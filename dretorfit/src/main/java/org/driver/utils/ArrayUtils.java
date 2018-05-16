package org.driver.utils;

import org.driver.config.UsbConfig;

import java.util.Arrays;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class ArrayUtils {


    /**
     * 合并数组补全length
     */
    public static byte[] merge(byte[]... values) {
        // 判断包是否合法
        if (values == null || values.length < 4||values[0]==null) {
            return null;
        }

        // 长度本身占一个字节
        int length = 1;
        for (byte[] value : values) {
            if (value != null) {
                length += value.length;
            }
        }

        // 先把第一个拷进去
        byte[] result = Arrays.copyOf(values[0], length);
        int offset = values[0].length;

        // 拼接指令
        for (int i = 1, index = 0; i < values.length; i++) {
            if (values[i] == null) continue;
            if (index == UsbConfig.LENGTH_INDEX) {
                System.arraycopy(Utils.intToByte(length, UsbConfig.LENGTH_SIZE), 0, result, offset, UsbConfig.LENGTH_SIZE);
                offset += UsbConfig.LENGTH_SIZE;
            }
            System.arraycopy(values[i], 0, result, offset, values[i].length);
            offset += values[i].length;
            index++;
        }
        return result;
    }

    /**
     * 位移运算获取int
     */
    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
    }
}
