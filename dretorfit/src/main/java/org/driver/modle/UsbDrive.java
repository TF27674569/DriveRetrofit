package org.driver.modle;

import org.driver.Instructions;

/**
 * Description : 驱动
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface UsbDrive {

    /**
     * 发送指令
     *
     * @param instructions 封装了发送指令，拦截指令，重试次数，间隔时间等 ，一定需要用callback回调
     * @param callback 返回结果
     */
    void execute(Instructions instructions, Call.Callback callback);

}
