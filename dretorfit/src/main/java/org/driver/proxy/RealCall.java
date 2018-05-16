package org.driver.proxy;

import org.driver.Instructions;
import org.driver.modle.UsbDrive;
import org.driver.modle.Call;

/**
 * Description : 真正开始执行的
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class RealCall implements Call {

    private UsbDrive drive;
    private ServiceMethod serviceMethod;
    private Object[] args;

    public RealCall(UsbDrive drive, ServiceMethod serviceMethod, Object[] args) {
        this.drive = drive;
        this.serviceMethod = serviceMethod;
        this.args = args;
    }

    /**
     * 返回结果
     *
     * @param callback
     */
    @Override
    public void execute(Call.Callback callback) {

        // 这里会重新处理args反射
        // thanks for okhttp
        byte[] command = serviceMethod.newBuilder()
                .args(args)// 为了args改变时重新赋予参数
                .build()
                .command();

        // 创建指令
        Instructions instructions = new Instructions();
        instructions.setSend(command);
        instructions.setIntercept(serviceMethod.intercepr());
        instructions.setInterval(serviceMethod.Interval());
        instructions.setRetryCount(serviceMethod.retryCount());

        // 驱动执行 真正回调给UsbDriver对象开始执行所有封装指令
        drive.execute(instructions, callback);
    }

}
