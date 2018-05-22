package org.usb.retorfit.proxy;

import org.usb.retorfit.info.Info;
import org.usb.retorfit.template.Call;
import org.usb.retorfit.template.UsbDriver;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class RealCall implements Call {

    private Info info;
    private UsbDriver usbDriver;

    public RealCall(UsbDriver usbDriver, Info info) {
        this.info = info;
        this.usbDriver = usbDriver;
    }

    /**
     * 执行
     *
     * @param callback
     */
    @Override
    public void execute(Callback callback) {
        usbDriver.execute(info, callback);
    }
}
