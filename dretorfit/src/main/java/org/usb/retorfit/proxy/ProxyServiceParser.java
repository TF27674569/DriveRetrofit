package org.usb.retorfit.proxy;

import org.usb.retorfit.annotation.Action;
import org.usb.retorfit.annotation.Adress;
import org.usb.retorfit.annotation.Data;
import org.usb.retorfit.annotation.End;
import org.usb.retorfit.annotation.Fun;
import org.usb.retorfit.annotation.Head;
import org.usb.retorfit.annotation.Intercept;
import org.usb.retorfit.annotation.Log;
import org.usb.retorfit.annotation.Retry;
import org.usb.retorfit.info.Info;
import org.usb.retorfit.template.BaseServiceParser;
import org.usb.retorfit.utils.Utils;

import java.lang.annotation.Annotation;

/**
 * Description : 解析默认的注解器
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class ProxyServiceParser extends BaseServiceParser{

    protected byte[] head;// 2 byte
    protected byte[] adress;// 1 byte
    protected byte[] fun;// 1 byte
    protected byte[] data;// ? byte
    protected byte[] retry;// 1 byte
    protected byte[] log;// 1 byte
    protected byte[] action;// 1 byte
    protected byte[] end;// 2 byte
    protected byte[] intercepr;

    // 重试次数
    protected int retryCount;
    // 重试间隔
    protected long timer;


    /**
     * 将 注解信息转为需要的信息
     */
    @Override
    public Info toInfo() {
        /**
         * 会自动补全长度，和长度所在位置
         */
        byte[] send = Utils.merge(head, adress, fun, data, retry, log, action, end);
        // 拦截
        intercepr = intercepr == null ? send : intercepr;
        // 创建信息
        return new Info(intercepr, send, retryCount, timer);
    }

    /**
     * 解析函数
     *
     * @param annotation 函数注解
     */
    @Override
    protected void parseMethodAnnotation(Annotation annotation) {
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
     * 解析参数
     *
     * @param annotation 函数参数注解
     * @param value 函数参数
     */
    @Override
    protected void parseParamsAnnotation(Annotation annotation, Object value) {
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
}
