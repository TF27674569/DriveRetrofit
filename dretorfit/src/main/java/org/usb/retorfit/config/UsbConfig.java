package org.usb.retorfit.config;

/**
 * Description : usb 参数
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/16
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class UsbConfig {

    /**
     * 默认没有值用于注解和判断注解
     */
    public static final int DEFAULT_NO_VALUE = -1;

    /**
     * 默认count值
     */
    public static final int DEFAULT_COUNT = 1;

    /**
     * 包头，包尾 长度
     */
    public static final int HEAD_AND_END_LENGTH = 2;

    /**
     * 其他的长度
     */
    public static final int OTHER_LENGTH = 1;

    /**
     * 长度本身占用大小
     */
    public static final int LENGTH_SIZE = 1;

    /**
     * 长度所在第几个数据处（不是字节,是数据拼装位置）
     * 包头 0xfffe 2字节 0
     * 地址 1字节 1
     * 功能码 1字节 2
     * 日志 1 字节 3
     * 长度 1 字节 在三的后面
     */
    public static int LENGTH_INDEX = 3;

}
