package org.usb.retorfit.factory;

import android.support.annotation.NonNull;


import org.usb.retorfit.template.Call;
import org.usb.retorfit.template.CallAdapter;
import org.usb.retorfit.utils.Utils;

import java.lang.reflect.Type;

/**
 * Description : 默认call
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
@SuppressWarnings("unchecked")
public class DefauleCallAdapterFactory extends CallAdapter.Factory {

    /**
     * 代理工厂
     */
    public static final CallAdapter.Factory DEFAULT_FACTORY = new DefauleCallAdapterFactory();

    /**
     * 返回适配器
     *
     * @param returnType 返回类型
     */
    @NonNull
    @Override
    public CallAdapter<?> get(Type returnType, String methodName) {
        Utils.checkNotNull(returnType, "responseType is null !!");

        CallAdapter<?> adapter = new DefauleCallAdapter<>();

        Utils.checkReturnType(adapter.responseType() == returnType, methodName, adapter.responseType().toString());

        return adapter;
    }

    class DefauleCallAdapter<T> implements CallAdapter<T> {

        /**
         * 返回值类型
         */
        @Override
        public Type responseType() {
            return Call.class;
        }

        /**
         * 适配
         */
        @Override
        public T adapt(Call call) {
            return (T) call;
        }
    }
}
