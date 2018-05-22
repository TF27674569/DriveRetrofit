package org.usb.retorfit.template;

/**
 * Description : 真正实现请求串口接口
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface Call {

    /**
     * 执行
     */
    void execute(Callback callback);


    interface Callback {

        /**
         * 成功执行
         */
        void onSuccess(byte[] result);

        /**
         * 失败
         */
        void onError(Throwable e);
    }
}
