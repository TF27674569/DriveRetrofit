package com.cabinet.jvm.driveretrofit.driver;

import org.driver.Instructions;
import org.driver.modle.Call;
import org.driver.modle.UsbDrive;

import java.util.concurrent.Executors;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/16
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class TestDriver implements UsbDrive {
    /**
     * 发送指令
     *
     * @param instructions 封装了发送指令，拦截指令，重试次数，间隔时间等 ，一定需要用callback回调
     * @param callback     返回结果
     */
    @Override
    public void execute(final Instructions instructions, final Call.Callback callback) {
        // 发送指令可以：https://github.com/TF27674569/Command

        // 这里模拟串口通信
        Executors.newSingleThreadExecutor()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 这里如果是进行串口通信
                        // 发送指令
                        // send(instructions.getSend(),instructions.getIntercept());
                        // 如果拿到返回结果
                        byte[] intercept = instructions.getIntercept();
                        try {
                            callback.onSuccess(intercept);
                        } catch (Exception e) {
                            callback.onError(e.getMessage());
                        }

                    }
                });
    }
}
