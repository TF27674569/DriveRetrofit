package org.driver.utils;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * Description : thanks for retorfit
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class Utils {

    public static <T> void validateServiceInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        // Prevent API interfaces from extending other interfaces. This not only avoids a bug in
        // Android (http://b.android.com/58753) but it forces composition of API declarations which is
        // the recommended pattern.
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

    public static void checkReturnType(boolean legitimate) {
        if (!legitimate) {
            throw new IllegalStateException("返回参数类型不合法");
        }
    }

    /***
     * int 转 byte
     * @param value 值
     * @param length 长度
     */
    public static byte[] intToByte(int value, @IntRange(from = 1, to = 4) int length) {
        // 先位运算取值
        byte[] bytes = new byte[]{
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };

        // 初始化当前字节长度的byte
        byte[] values = null;

        switch (length) {
            case 1:
                values = new byte[]{bytes[3]};
                break;
            case 2:
                values = new byte[]{bytes[2], bytes[3]};
                break;
            case 3:
                values = new byte[]{bytes[1], bytes[2], bytes[3]};
                break;
            case 4:
                values = bytes;
                break;
        }

        return values;
    }

}
