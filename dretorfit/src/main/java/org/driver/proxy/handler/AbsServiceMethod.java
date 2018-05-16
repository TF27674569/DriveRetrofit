package org.driver.proxy.handler;

import org.driver.Info;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/16
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public abstract class AbsServiceMethod {

    /**
     * 获取指令封装信息
     */
    public abstract Info info();

    public abstract Info info(Object[] args);

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

        public Object[] args;

        public AbsBuilder(Method method) {
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
            this.returnType = method.getReturnType();
        }

        public AbsBuilder args(Object[] args) {
            this.args = args;
            return this;
        }

        public abstract <T extends AbsServiceMethod> T build();


    }

}
