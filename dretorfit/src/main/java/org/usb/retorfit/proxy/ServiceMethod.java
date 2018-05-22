package org.usb.retorfit.proxy;

import org.usb.retorfit.info.Info;
import org.usb.retorfit.template.AbsServiceMethod;
import org.usb.retorfit.template.Call;
import org.usb.retorfit.template.CallAdapter;
import org.usb.retorfit.utils.Utils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Description : 代理函数解析对象
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
@SuppressWarnings("unchecked")
public final class ServiceMethod extends AbsServiceMethod {
    private ServiceMethod.Builder P;

    private ServiceMethod(ServiceMethod.Builder builder) {
        this.P = builder;
    }

    /**
     * 获取指令封装信息
     */
    @Override
    public Info info() {
        return P.serviceParser.toInfo();
    }

    /**
     * 参数解析
     */
    @Override
    public Info info(Object[] args) {
        if (args != null) {
            P.serviceParser.parseArgs(args);
        }
        return info();
    }

    /**
     * 适配
     */
    @Override
    protected <T> T adapt(CallAdapter.Factory adapterFactory, Call call) {
        CallAdapter<?> callAdapter = adapterFactory.get(P.returnType,P.method.getName());
        return (T) callAdapter.adapt(call);
    }

    /**
     * 返回类型
     */
    @Override
    public Type returnType() {
        return P.returnType;
    }


    public static class Builder extends AbsServiceMethod.AbsBuilder {

        public Builder(Method method) {
            super(method);
        }

        @Override
        public ServiceMethod build() {

            Utils.checkNotNull(serviceParser,"ServiceParser is null !!");

            // 添加函数注解
            serviceParser.addMethodAnnotation(methodAnnotations);

            // 添加参数注解
            serviceParser.addParameterAnnotation(parameterAnnotationsArray);

            // 解析函数
            serviceParser.parseMethod();

            // 解析参数
            serviceParser.parseArgs(args);

            return new ServiceMethod(this);
        }
    }
}
