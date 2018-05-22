package com.cabinet.jvm.driveretrofit.driver;



import org.usb.retorfit.info.Info;
import org.usb.retorfit.template.Call;
import org.usb.retorfit.template.UsbDriver;

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
public class TestDriver implements UsbDriver {

    /**
     * 发送指令
     *
     * @param info     封装了发送指令，拦截指令，重试次数，间隔时间等 ，一定需要用callback回调,否则监听器收不到结果
     * @param callback 返回结果
     */
    @Override
    public void execute(Info info, Call.Callback callback) {

    }
}
