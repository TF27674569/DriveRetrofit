package org.usb.retorfit.proxy;

import org.usb.retorfit.UsbRetorfit;
import org.usb.retorfit.info.Info;
import org.usb.retorfit.template.Call;
import org.usb.retorfit.template.UsbDriver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description : 代理实现对象
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public final class ProxyInvocationHandler implements InvocationHandler {

    // 函数缓存避免重复反射创建
    private final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();

    private UsbRetorfit.Builder.Params mParams;

    public ProxyInvocationHandler(UsbRetorfit.Builder.Params params) {
        this.mParams = params;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // object 的函数直接实现
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }

        // 函数解析对象
        ServiceMethod serviceMethod = loadServiceMethod(method, args);

        // 驱动
        UsbDriver usbDriver = mParams.usbDriverFactory.get();

        // 请求信息
        Info info = serviceMethod.info(args);

        // 真实的请求call
        Call realCall = new RealCall(usbDriver,info);

        // 适配
        return serviceMethod.adapt(mParams.adapterFactory,realCall);
    }

    /**
     * 解析函数
     */
    private ServiceMethod loadServiceMethod(Method method, Object[] args) {
        // 先从缓存中拿
        ServiceMethod result = serviceMethodCache.get(method);
        if (result != null) return result;
        // 缓存中没有
        synchronized (serviceMethodCache) {
            // 多线程操作时多重判断有没有
            result = serviceMethodCache.get(method);
            if (result == null) {
                // 创建
                result = new ServiceMethod.Builder(method).args(args).service(mParams.serviceParserFactory.get()).build();
                // 加入缓存
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }
}
