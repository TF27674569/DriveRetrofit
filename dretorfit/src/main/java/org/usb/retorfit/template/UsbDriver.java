package org.usb.retorfit.template;

import android.support.annotation.NonNull;

import org.usb.retorfit.info.Info;

/**
 * Description : 驱动抽象接口，真实请求
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface UsbDriver {

    /**
     * 发送指令
     *
     * @param info 封装了发送指令，拦截指令，重试次数，间隔时间等 ，一定需要用callback回调,否则监听器收不到结果
     * @param callback 返回结果
     */
    void execute(Info info, Call.Callback callback);


    interface Factory{

        @NonNull
        UsbDriver get();
    }

}
