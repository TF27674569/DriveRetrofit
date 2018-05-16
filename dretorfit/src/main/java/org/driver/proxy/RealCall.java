package org.driver.proxy;

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

    // 执行驱动（okhttpcall）
    private UsbDrive drive;
    // 执行参数所在类（Request）
    private ServiceMethod serviceMethod;
    // 改变的参数（函数参数里面的值）
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
        // 驱动执行 真正回调给UsbDriver对象开始执行所有封装指令
        drive.execute(serviceMethod.info(args), callback);
    }

}
