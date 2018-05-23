package org.usb.retorfit.factory;

import android.content.Context;

import org.usb.driver.Instruct;
import org.usb.driver.OkUsbClient;
import org.usb.driver.template.Callback;
import org.usb.retorfit.info.Info;
import org.usb.retorfit.template.Call;
import org.usb.retorfit.template.UsbDriver;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/23
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class OkUsbDriver extends org.usb.driver.OkUsbClient implements UsbDriver{
    protected OkUsbDriver(Builder builder) {
        super(builder);
    }

    /**
     * 发送指令
     *
     * @param info     封装了发送指令，拦截指令，重试次数，间隔时间等 ，一定需要用callback回调,否则监听器收不到结果
     * @param callback 返回结果
     */
    @Override
    public void execute(Info info, final Call.Callback callback) {
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
        writeInstruct(instruct);
    }


    public static class Builder extends OkUsbClient.Builder{

        public Builder(Context context) {
            super(context);
        }

        @Override
        public OkUsbDriver build() {
            return new OkUsbDriver(this);
        }
    }
}
