package org.driver.adapter;

import org.driver.modle.Call;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.subjects.PublishSubject;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class RxJava2CallAdapter<T> implements Adapter {

    private RxJava2CallAdapter() {

    }

    public static RxJava2CallAdapter<?> create() {
        return new RxJava2CallAdapter<>();
    }


    /**
     * 适配
     *
     * @param call
     */
    @SuppressWarnings("unchecked")
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
                    public void onError(String message) {
                        emitter.onError(new Throwable(message));
                    }
                });
            }
        });
    }


    /**
     * 是否是返回的类型
     *
     * @param type
     */
    @Override
    public boolean checkReturnType(Type type) {
        return type == Observable.class;
    }
}
