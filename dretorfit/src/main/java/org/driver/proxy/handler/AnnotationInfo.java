package org.driver.proxy.handler;

import org.driver.base.Info;
import org.driver.annoation.Action;
import org.driver.annoation.Adress;
import org.driver.annoation.Data;
import org.driver.annoation.End;
import org.driver.annoation.Fun;
import org.driver.annoation.Head;
import org.driver.annoation.Intercept;
import org.driver.annoation.Log;
import org.driver.annoation.Retry;
import org.driver.utils.ArrayUtils;
import org.driver.utils.Utils;

import java.lang.annotation.Annotation;

/**
 * Description : 注解信息
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/16
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public final class AnnotationInfo implements Cloneable {

    private Annotation[] annotations;
    private Annotation[][] parameterAnnotations;
    /****************************************************************/

    private byte[] head;// 2 byte
    private byte[] adress;// 1 byte
    private byte[] fun;// 1 byte
    private byte[] data;// ? byte
    private byte[] retry;// 1 byte
    private byte[] log;// 1 byte
    private byte[] action;// 1 byte
    private byte[] end;// 2 byte
    private byte[] intercepr;

    // 重试次数
    private int retryCount;
    // 重试间隔
    private long timer;

    public AnnotationInfo(Annotation[] methodAnnotations) {
        this.annotations = methodAnnotations;
    }
/***********************************************************************************************************************************************/

    /**
     * 解析函数注解
     */
    public void parseMethod() {
        for (int i = 0; i < annotations.length; i++) {
            parseMethodAnnotation(annotations[i]);
        }
    }

    /**
     * 解析参数注解
     */
    public void setParameterAnnotations(Annotation[][] parameterAnnotations) {
        this.parameterAnnotations = parameterAnnotations;
    }

    /**
     * 对象值处理
     */
    public void parseArgs(Object[] args) {
        if (args == null) return;

        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                parseParamsAnnotation(annotation, args[i]);
            }
        }
    }


    /**
     * 转为需要的对象
     */
    public Info toInfo() {
        /**
         * 会自动补全长度，和长度所在位置
         */
        byte[] send = ArrayUtils.merge(head, adress, fun, data, retry, log, action, end);
        // 拦截
        intercepr = intercepr == null ? send : intercepr;
        // 创建信息
        return new Info(intercepr, send, retryCount, timer);
    }
/***********************************************************************************************************************************************/
    /**
     * 解析函数和注解
     *
     * @param annotation 注解
     * @param value      值
     */
    private void parseParamsAnnotation(Annotation annotation, Object value) {
        if (annotation instanceof Head) {
            head = toByte((int) value, ((Head) annotation).size());
        } else if (annotation instanceof Adress) {
            adress = toByte((int) value, ((Adress) annotation).size());
        } else if (annotation instanceof Fun) {
            fun = toByte((int) value, ((Fun) annotation).size());
        } else if (annotation instanceof Data) {
            data = (byte[]) value;
        } else if (annotation instanceof Retry) {
            retryCount = (int) value;
            retry = toByte(retryCount, ((Retry) annotation).size());
            timer = ((Retry) annotation).time();
        } else if (annotation instanceof Log) {
            log = toByte(((Log.Logger) value).getValue(), ((Log) annotation).size());
        } else if (annotation instanceof Action) {
            action = toByte((int) value, ((Action) annotation).size());
        } else if (annotation instanceof Intercept) {
            intercepr = (byte[]) value;
        } else if (annotation instanceof End) {
            end = toByte((int) value, ((End) annotation).size());
        }
    }

    /**
     * 解析函数上注解
     */
    private void parseMethodAnnotation(Annotation annotation) {
        if (annotation instanceof Head) {
            Head value = (Head) annotation;
            head = toByte(value.value(), value.size());
        } else if (annotation instanceof Adress) {
            Adress value = (Adress) annotation;
            adress = toByte(value.value(), value.size());
        } else if (annotation instanceof Fun) {
            Fun value = (Fun) annotation;
            fun = toByte(value.value(), value.size());
        } else if (annotation instanceof Data) {
            Data value = (Data) annotation;
            data = checkNoData(value.value());
        } else if (annotation instanceof Retry) {
            Retry value = (Retry) annotation;
            retryCount = value.value();
            retry = toByte(retryCount, value.size());
            timer = value.time();
        } else if (annotation instanceof Log) {
            Log value = (Log) annotation;
            log = toByte(value.value().getValue(), value.size());
        } else if (annotation instanceof Action) {
            Action value = (Action) annotation;
            action = toByte(value.value(), value.size());
        } else if (annotation instanceof Intercept) {
            Intercept value = (Intercept) annotation;
            intercepr = checkNoData(value.value());
        } else if (annotation instanceof End) {
            End value = (End) annotation;
            end = toByte(value.value(), value.size());
        }
    }

    /**
     * int 转  byte
     */
    private byte[] toByte(int value, int length) {
        if (value == -1) return null;
        return Utils.intToByte(value, length);
    }

    /**
     * 检测是否有值
     */
    private byte[] checkNoData(byte[] bytes) {
        return ArrayUtils.byteArrayToInt(bytes) == -1 ? null : bytes;
    }
}
