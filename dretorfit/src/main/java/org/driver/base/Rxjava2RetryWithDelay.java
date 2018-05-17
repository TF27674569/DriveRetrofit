package org.driver.base;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Description : 重试
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/16
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class Rxjava2RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {

    /**
     * 最大重试次数
     */
    private int maxRetries;

    /**
     * 实际重试次数
     */
    private int retryCount;

    /**
     * 重试间隔
     */
    private long retryDelayMillis;


    public Rxjava2RetryWithDelay(Info info) {
        this.maxRetries = info.getRetryCount();
        this.retryDelayMillis = info.getInterval();
    }

    public Rxjava2RetryWithDelay(int maxRetries, long retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(final Observable<? extends Throwable> observable) throws Exception {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                if (++retryCount <= maxRetries) {
                    Log.e("TAG", "retryCount: " + retryCount);
                    return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                return Observable.error(throwable);
            }
        });
    }
}
