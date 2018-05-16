package org.driver.modle;

/**
 * Description : 回调
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface Call {

    /**
     * 返回结果
     */
    void execute(Callback callback);


    interface Callback {

        /**
         * 返回结果回调
         */
        void onSuccess(byte[] result);

        /**
         * 失败，返回原因
         */
        void onError(String message);
    }
}
