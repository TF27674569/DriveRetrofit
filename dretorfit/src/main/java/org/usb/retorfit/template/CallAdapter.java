package org.usb.retorfit.template;

import android.support.annotation.NonNull;

import org.usb.retorfit.utils.Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description : 适配器
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface CallAdapter<T> {

    /**
     * 返回值类型
     */
    Type responseType();

    /**
     * 适配
     */
    T adapt(Call call);


    /**
     * 工厂类
     */
    abstract class Factory {

        /**
         * 返回适配器
         *
         * @param returnType 返回类型
         */
        public abstract @NonNull
        CallAdapter<?> get(Type returnType, String methodName);


        /**
         * 获取位置对应的type
         */
        protected static Type getParameterUpperBound(int index, ParameterizedType type) {
            return Utils.getParameterUpperBound(index, type);
        }

        /**
         * 从type中获取class
         */
        protected static Class<?> getRawType(Type type) {
            return Utils.getRawType(type);
        }
    }
}
