package org.usb.retorfit.utils;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import org.usb.retorfit.config.UsbConfig;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

/**
 * Description : thanks for retorfit
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public final class Utils {


    /**
     * 检测代理对象是否是接口
     */
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

    public static Type getParameterUpperBound(int index, ParameterizedType type) {
        Type[] types = type.getActualTypeArguments();
        if (index < 0 || index >= types.length) {
            throw new IllegalArgumentException(
                    "Index " + index + " not in range [0," + types.length + ") for " + type);
        }
        Type paramType = types[index];
        if (paramType instanceof WildcardType) {
            return ((WildcardType) paramType).getUpperBounds()[0];
        }
        return paramType;
    }

    public static Type getCallResponseType(Type returnType) {
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalArgumentException(
                    "Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
        }
        return getParameterUpperBound(0, (ParameterizedType) returnType);
    }

    public static Class<?> getRawType(Type type) {
        checkNotNull(type, "type == null");

        if (type instanceof Class<?>) {
            // Type is a normal class.
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
            // suspects some pathological case related to nested classes exists.
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) throw new IllegalArgumentException();
            return (Class<?>) rawType;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
            // type that's more general than necessary is okay.
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }

        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
    }

    public static <T> T checkNotNull(@Nullable T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    public static void checkReturnType(boolean legitimate, String methodName, String message) {
        if (!legitimate) {
            throw new IllegalStateException("函数 " + methodName + " 的返回类型必须是: " + message);
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

    /**
     * 位移运算获取int
     */
    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
    }

    /**
     * 合并数组补全length
     */
    public static byte[] merge(byte[]... values) {
        // 判断包是否合法  没到长度位
        if (values == null || values.length < UsbConfig.LENGTH_INDEX + 1 || values[0] == null) {
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
}
