package org.usb.retorfit.template;

import org.usb.retorfit.config.UsbConfig;
import org.usb.retorfit.utils.Utils;

import java.lang.annotation.Annotation;

/**
 * Description : 基本的解析器
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public abstract class BaseServiceParser implements ServiceParser {

    /**
     * 函数注解
     */
    protected Annotation[] methodAnnotations;

    /**
     * 参数注解
     */
    protected Annotation[][] parameterAnnotations;


    /**
     * 添加函数注解
     */
    @Override
    public void addMethodAnnotation(Annotation[] annotations) {
        this.methodAnnotations = annotations;
    }

    /**
     * 添加参数注解
     */
    @Override
    public void addParameterAnnotation(Annotation[][] parameterAnnotations) {
        this.parameterAnnotations = parameterAnnotations;
    }

    /**
     * 解析函数
     */
    @Override
    public void parseMethod() {
        for (int i = 0; i < methodAnnotations.length; i++) {
            parseMethodAnnotation(methodAnnotations[i]);
        }
    }

    /**
     * 解析
     */
    @Override
    public void parseArgs(Object[] args) {
        if (args == null) return;

        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                parseParamsAnnotation(annotation, args[i]);
            }
        }
    }

    /**
     * int 转  byte
     */
    protected byte[] toByte(int value, int length) {
        if (value == UsbConfig.DEFAULT_NO_VALUE) return null;
        return Utils.intToByte(value, length);
    }

    /**
     * 检测是否有值
     */
    protected byte[] checkNoData(byte[] bytes) {
        return Utils.byteArrayToInt(bytes) == UsbConfig.DEFAULT_NO_VALUE ? null : bytes;
    }

    /**
     * 解析函数
     */
    protected abstract void parseMethodAnnotation(Annotation annotation);


    /**
     * 解析参数
     */
    protected abstract void parseParamsAnnotation(Annotation annotation, Object value);
}
