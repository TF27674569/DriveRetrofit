package org.usb.retorfit.factory;

import android.support.annotation.NonNull;

import org.usb.Instruct;
import org.usb.OkDriveClient;
import org.usb.base.Callback;
import org.usb.retorfit.info.Info;
import org.usb.retorfit.template.Call;
import org.usb.retorfit.template.UsbDriver;

/**
 * Description : okDriver 驱动
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class OkDriverClientFactory implements UsbDriver.Factory {

    private OkDriveClient client;

    private OkDriverClientFactory(OkDriveClient client) {
        this.client = client;
    }


    public static OkDriverClientFactory create(OkDriveClient client) {
        return new OkDriverClientFactory(client);
    }


    @NonNull
    @Override
    public UsbDriver get() {
        return new UsbDriver() {
            @Override
            public void execute(Info info, final Call.Callback callback) {
                /**
                 * byte[] send, byte[] intercept, int retyrCount, long retryTimer, Callback callback
                 */
                Instruct instruct = new Instruct(info.getSend(), info.getIntercept(), info.getRetryCount(), info.getInterval(), new Callback() {
                    @Override
                    public void onSuccess(byte[] result) {
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onError(throwable);
                    }
                });

                client.writeInstruct(instruct);
            }
        };
    }
}
