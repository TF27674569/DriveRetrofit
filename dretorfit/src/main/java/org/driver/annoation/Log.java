package org.driver.annoation;

import org.driver.config.UsbConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description : 是否开启日期
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
// 运行时注解
@Retention(RetentionPolicy.RUNTIME)
// 可以用在函数和参数中
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Log {

    enum Logger {
        OFF(0x00),
        ON(0x01);
        int value;

        Logger(int value) {
            this.value = value;
        }

        public int getValue(){
            return value;
        }
    }

    Logger value() default Logger.OFF;

    /**
     * 占的字节大小
     */
    int size() default UsbConfig.OTHER_LENGTH;
}
