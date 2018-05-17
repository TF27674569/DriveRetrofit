package com.cabinet.jvm.driveretrofit.driver;

import org.driver.base.Info;
import org.driver.modle.Call;
import org.driver.modle.UsbDrive;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/16
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class TestDriver implements UsbDrive {
    int count = 0;

    /**
     * 发送指令
     *
     * @param info     封装了发送指令，拦截指令，重试次数，间隔时间等 ，一定需要用callback回调
     * @param callback 返回结果
     */
    @Override
    public void execute(final Info info, final Call.Callback callback) {
        // 发送指令可以：https://github.com/TF27674569/Command

        // 这里模拟串口通信 + rxjava重试
        Observable.create(
                new ObservableOnSubscribe<byte[]>() {
                    @Override
                    public void subscribe(ObservableEmitter<byte[]> e) throws Exception {
                        count++;
                        if (count > 3) {
                            e.onNext(info.getIntercept());
                        } else {
                            e.onError(new Throwable("测试"));
                        }
                    }
                })
                .retryWhen(new RetryFunc(info.getRetryCount(), info.getInterval()))
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<byte[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(byte[] value) {
                        count = 0;
                        try {
                            callback.onSuccess(value);
                        } catch (Exception e) {
                            callback.onError(e);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                /*.subscribe(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] value) throws Exception {
                        // 这里如果是进行串口通信
                        // 发送指令
                        // send(info.getSend(),info.getIntercept());
                        // 如果拿到返回结果
                        try {
                            callback.onSuccess(value);
                        } catch (Exception e) {
                            callback.onError(e.getMessage());
                        }
                    }
                })*/
        ;
    }
}
