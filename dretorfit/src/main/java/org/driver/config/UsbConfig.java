package org.driver.config;

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
     * 长度所在第几个数据处（不是字节,是数据拼装）
     */
    public static final int LENGTH_INDEX = 3;

}
