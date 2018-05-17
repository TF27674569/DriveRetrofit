package org.driver.base;

import org.driver.modle.Call;
import org.driver.modle.UsbDrive;
import org.usb.Instruct;
import org.usb.OkDriveClient;
import org.usb.base.Callback;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/17
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class OkDriver implements UsbDrive {

    OkDriveClient client;

    public OkDriver(OkDriveClient client) {
        this.client = client;
    }

    /**
     * 发送指令
     *
     * @param info     封装了发送指令，拦截指令，重试次数，间隔时间等 ，一定需要用callback回调,否则监听器收不到结果
     * @param callback 返回结果
     */
    @Override
    public void execute(Info info, final Call.Callback callback) {
        client.writeInstruct(new Instruct(info.getSend(), info.getIntercept(), info.getRetryCount(), info.getInterval(), new Callback() {
            @Override
            public void onSuccess(byte[] result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable throwable) {
                callback.onError(throwable);
            }
        }));
    }
}
