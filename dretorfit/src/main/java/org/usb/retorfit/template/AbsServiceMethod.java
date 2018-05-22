package org.usb.retorfit.template;

import org.usb.retorfit.info.Info;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public abstract class AbsServiceMethod {

    /**
     * 获取指令封装信息
     */
    public abstract Info info();

    /**
     * 参数解析
     */
    public abstract Info info(Object[] args);

    /**
     * 适配
     */
    protected abstract <T> T adapt(CallAdapter.Factory adapterFactory,  Call call);

    /**
     * 返回类型
     */
    public abstract Type returnType();


    public static abstract class AbsBuilder {
        // 函数上所有注解
        public Annotation[] methodAnnotations;
        // 函数
        public Method method;
        // 函数参数所有注解
        public Annotation[][] parameterAnnotationsArray;
        // 返回值的class
        public Type returnType;
        // 函数参数
        public Object[] args;
        // 解析器
        public ServiceParser serviceParser;

        public AbsBuilder(Method method) {
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
            this.returnType = method.getReturnType();
        }

        /**
         * 函数参数
         */
        public AbsBuilder args(Object[] args) {
            this.args = args;
            return this;
        }

        /**
         * 解析器
         */
        public AbsBuilder service(ServiceParser serviceParser) {
            this.serviceParser = serviceParser;
            return this;
        }

        public abstract <T extends AbsServiceMethod> T build();


    }
}
