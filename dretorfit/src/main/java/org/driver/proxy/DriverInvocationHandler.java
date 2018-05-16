package org.driver.proxy;

import org.driver.UsbRetorfit;
import org.driver.adapter.DriverAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description : 代理回调
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class DriverInvocationHandler<T> implements InvocationHandler {

    // 函数缓存避免重复反射创建
    private final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();

    private UsbRetorfit.Builder.Params mParams;

    public DriverInvocationHandler(UsbRetorfit.Builder.Params params) {
        mParams = params;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        // 解析函数的注解等信息
        ServiceMethod serviceMethod = loadServiceMethod(method, args);
        // 适配器
        DriverAdapter adapter = new DriverAdapter(mParams.adapter,serviceMethod.returnType(),method.getName());
        // 适配
        return adapter.adapt(new RealCall(mParams.drive,serviceMethod,args));
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
                result = new ServiceMethod.Builder(method).args(args).build();
                // 加入缓存
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }
}
