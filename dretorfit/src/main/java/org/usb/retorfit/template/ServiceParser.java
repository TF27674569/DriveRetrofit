package org.usb.retorfit.template;

import android.support.annotation.NonNull;

import org.usb.retorfit.info.Info;

import java.lang.annotation.Annotation;

/**
 * Description : 解析器
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface ServiceParser {

    /**
     * 将 注解信息转为需要的信息
     */
    Info toInfo();

    /**
     * 解析函数
     */
    void parseMethod();

    /**
     * 解析
     */
    void parseArgs(Object[] args);

    /**
     * 添加函数注解
     */
    void addMethodAnnotation(Annotation[] annotations);

    /**
     * 添加参数注解
     */
    void addParameterAnnotation(Annotation[][] parameterAnnotations);


    /**
     * 解析工厂类 支持自定义解析器
     */
    interface Factory {

        /**
         * 返回解析实体对象
         */
        @NonNull
        ServiceParser get();
    }
}
