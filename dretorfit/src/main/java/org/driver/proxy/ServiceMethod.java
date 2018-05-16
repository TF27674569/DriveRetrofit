package org.driver.proxy;

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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;


/**
 * Description : 函数
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
final class ServiceMethod {

    private Builder builder;

    private ServiceMethod(Builder builder) {
        this.builder = builder;
    }

    /**
     * 拼接指令
     * TODO 后续指令调整所在类
     */
    byte[] command() {
        byte[] len = length();

        return ArrayUtils.concatAll(
                builder.head,
                builder.adress,
                builder.fun,
                len,
                builder.data,
                builder.retry,
                builder.log,
                builder.action,
                builder.end);
    }

    // 计算长度
    private byte[] length() {
        // 长度自己占一个字节
        int length = 1;

        // 头尾两个字节 其他一个字节
        if (builder.head != null) {
            length += 2;
        }

        if (builder.adress != null) {
            length += 1;
        }

        if (builder.fun != null) {
            length += 1;
        }

        if (builder.data != null) {
            length += builder.data.length;
        }

        if (builder.retry != null) {
            length += 1;
        }

        if (builder.log != null) {
            length += 1;
        }

        if (builder.action != null) {
            length += 1;
        }

        if (builder.end != null) {
            length += 2;
        }

        return builder.intToByte(length, 1);
    }

    /**
     * 拦截指令
     */
    byte[] intercepr() {
        // 如果没传拦截指令 则默认拦截的是发送指令
        if (builder.intercepr == null) {
            return command();
        }
        return builder.intercepr;
    }

    /**
     * 重试间隔
     */
    long interval() {
        return builder.timer;
    }

    /**
     * 重试次数
     */
    int retryCount() {
        return builder.retryCount;
    }

    /**
     * 创建新builder用来处理参数
     */
    Builder newBuilder() {
        return new Builder(builder);
    }

    /**
     * 建造器
     */
    static class Builder {
        // 函数上所有注解
        Annotation[] methodAnnotations;
        // 函数
        Method method;
        // 函数参数所有注解
        Annotation[][] parameterAnnotationsArray;

        // 返回值的class
        Type returnType;

        // 函数参数
        Object[] args;


        /***************** 所有参数 **********************************************/
        byte[] head;// 2 byte
        byte[] adress;// 1 byte
        byte[] fun;// 1 byte
        byte[] data;// ? byte
        byte[] retry;// 1 byte
        byte[] log;// 1 byte
        byte[] action;// 1 byte
        byte[] end;// 2 byte
        byte[] intercepr;
        int retryCount;
        long timer;


        Builder(Method method) {
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
            this.returnType = method.getReturnType();
            // 解析函数上的注解
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }
        }

        Builder(Builder builder) {
            this.method = builder.method;
            this.methodAnnotations = builder.methodAnnotations;
            this.parameterAnnotationsArray = builder.parameterAnnotationsArray;
            this.returnType = builder.returnType;

            this.args = builder.args;

            this.head = builder.head;// 2 byte
            this.adress = builder.adress;// 1 byte
            this.fun = builder.fun;// 1 byte
            this.data = builder.data;// ? byte
            this.retry = builder.retry;// 1 byte
            this.log = builder.log;// 1 byte
            this.action = builder.action;// 1 byte
            this.end = builder.end;// 2 byte
            this.intercepr = builder.intercepr;
            this.retryCount = builder.retryCount;
            this.timer = builder.timer;
        }

        Builder args(Object[] args) {
            this.args = args;
            // 解析参数注解
            for (int i = 0; i < parameterAnnotationsArray.length; i++) {
                for (Annotation annotation : parameterAnnotationsArray[i]) {
                    parseParamsAnnotation(annotation, args[i]);
                }
            }
            return this;
        }

        ServiceMethod build() {
            return new ServiceMethod(this);
        }


        /**
         * 解析函数和注解
         */
        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof Head) {
                int value = ((Head) annotation).value();
                if (value == -1) return;
                head = intToByte(value, 2);
            } else if (annotation instanceof Adress) {
                int value = ((Adress) annotation).value();
                if (value == -1) return;
                adress = intToByte(value, 1);
            } else if (annotation instanceof Fun) {
                int value = ((Fun) annotation).value();
                if (value == -1) return;
                fun = intToByte(value, 1);
            } else if (annotation instanceof Data) {
                data = ((Data) annotation).value();
                if (ArrayUtils.byteArrayToInt(data) == -1) {
                    data = null;
                }
            } else if (annotation instanceof Retry) {
                retryCount = ((Retry) annotation).value();
                if (retryCount != -1) {
                    retry = intToByte(retryCount, 1);
                    timer = ((Retry) annotation).time();
                }
            } else if (annotation instanceof Log) {
                log = intToByte(((Log) annotation).value().getValue(), 1);
            } else if (annotation instanceof Action) {
                int value = ((Action) annotation).value();
                if (value == -1) return;
                action = intToByte(value, 1);
            } else if (annotation instanceof Intercept) {
                intercepr = ((Intercept) annotation).value();
                if (ArrayUtils.byteArrayToInt(intercepr) == -1) {
                    intercepr = null;
                }
            } else if (annotation instanceof End) {
                int value = ((End) annotation).value();
                if (value == -1) return;
                end = intToByte(value, 2);
            }
        }

        /**
         * 解析函数和注解
         *
         * @param annotation 注解
         * @param value      值
         */
        private void parseParamsAnnotation(Annotation annotation, Object value) {
            if (annotation instanceof Head) {
                head = intToByte((int) value, 2);
            } else if (annotation instanceof Adress) {
                adress = intToByte((int) value, 1);
            } else if (annotation instanceof Fun) {
                fun = intToByte((int) value, 1);
            } else if (annotation instanceof Data) {
                data = (byte[]) value;
            } else if (annotation instanceof Retry) {
                retryCount = (int) value;
                retry = intToByte(retryCount, 1);
                timer = ((Retry) annotation).time();
            } else if (annotation instanceof Log) {
                log = intToByte(((Log.Logger) value).getValue(), 1);
            } else if (annotation instanceof Action) {
                action = intToByte((int) value, 1);
            } else if (annotation instanceof Intercept) {
                intercepr = (byte[]) value;
            } else if (annotation instanceof End) {
                end = intToByte((int) value, 2);
            }
        }

        private byte[] intToByte(int value, int length) {
            // 先位运算取值
            byte[] bytes = new byte[]{
                    (byte) ((value >> 24) & 0xFF),
                    (byte) ((value >> 16) & 0xFF),
                    (byte) ((value >> 8) & 0xFF),
                    (byte) (value & 0xFF)
            };

            // 初始化当前字节长度的byte
            byte[] values = null;

            switch (length) {
                case 1:
                    values = new byte[]{bytes[3]};
                    break;
                case 2:
                    values = new byte[]{bytes[2], bytes[3]};
                    break;
                case 3:
                    values = new byte[]{bytes[1], bytes[2], bytes[3]};
                    break;
                case 4:
                    values = bytes;
                    break;
            }

            return values;
        }

    }
}
