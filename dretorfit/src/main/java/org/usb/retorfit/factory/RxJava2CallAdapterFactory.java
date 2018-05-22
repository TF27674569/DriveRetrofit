package org.usb.retorfit.factory;

import android.support.annotation.NonNull;

import org.usb.retorfit.template.Call;
import org.usb.retorfit.template.CallAdapter;
import org.usb.retorfit.utils.Utils;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
@SuppressWarnings("unchecked")
public class RxJava2CallAdapterFactory extends CallAdapter.Factory {

    /**
     * 代理工厂
     */
    public static CallAdapter.Factory create() {
        return new RxJava2CallAdapterFactory();
    }

    /**
     * 返回适配器
     *
     * @param returnType 返回类型
     * @param methodName
     */
    @NonNull
    @Override
    public CallAdapter<?> get(Type returnType, String methodName) {
        Utils.checkNotNull(returnType, "responseType is null !!");
        CallAdapter<?> adapter = new Rxjava2CallAdapter();
        Utils.checkReturnType(adapter.responseType() == returnType, methodName, adapter.responseType().toString());
        return adapter;
    }


    class Rxjava2CallAdapter<T> implements CallAdapter<T> {

        /**
         * 返回值类型
         */
        @Override
        public Type responseType() {
            return Observable.class;
        }

        /**
         * 适配
         */
        @Override
        public T adapt(final Call call) {
            return (T) Observable.create(new ObservableOnSubscribe<byte[]>() {
                @Override
                public void subscribe(final ObservableEmitter<byte[]> emitter) throws Exception {
                    call.execute(new Call.Callback() {
                        @Override
                        public void onSuccess(byte[] result) {
                            emitter.onNext(result);
                            emitter.onComplete();
                        }

                        @Override
                        public void onError(Throwable e) {
                            emitter.onError(e);
                        }
                    });

                }
            });
        }
    }
}
