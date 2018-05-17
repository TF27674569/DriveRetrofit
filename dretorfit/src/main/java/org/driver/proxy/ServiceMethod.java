package org.driver.proxy;

import org.driver.base.Info;
import org.driver.proxy.handler.AbsServiceMethod;
import org.driver.proxy.handler.AnnotationInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Description : 函数解析对象
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/16
 * Email : 27674569@qq.com
 * Version : 1.0
 */
final class ServiceMethod extends AbsServiceMethod {

    private Builder P;

    private ServiceMethod(Builder builder) {
        this.P = builder;
    }

    /**
     * 获取指令封装信息
     */
    @Override
    public Info info() {
        return P.annotationInfo.toInfo();
    }

    @Override
    public Info info(Object[] args) {
        if (args != null) {
            P.annotationInfo.parseArgs(args);
        }
        return info();
    }

    /**
     * 返回类型
     */
    @Override
    public Type returnType() {
        return P.returnType;
    }


    /**
     * 建造器
     */
    public static class Builder extends AbsBuilder {

        private AnnotationInfo annotationInfo;

        Builder(Method method) {
            super(method);
        }

        @SuppressWarnings("unchecked")
        @Override
        public AbsServiceMethod build() {
            // 解析函数注解
            parseMethodAnnotation();
            // 解析参数注解
            parseParameterAnnotation();

            return new ServiceMethod(this);
        }

        /**
         * 解析函数注解
         */
        private void parseMethodAnnotation() {
            annotationInfo = new AnnotationInfo(methodAnnotations);
            annotationInfo.parseMethod();
        }

        /**
         * 解析参数注解
         */
        private void parseParameterAnnotation() {
            annotationInfo.setParameterAnnotations(parameterAnnotationsArray);
            annotationInfo.parseArgs(args);
        }

    }
}
