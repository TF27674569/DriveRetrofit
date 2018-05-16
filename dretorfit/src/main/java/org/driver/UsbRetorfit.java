package org.driver;

import org.driver.modle.UsbDrive;
import org.driver.adapter.Adapter;
import org.driver.proxy.DriverInvocationHandler;
import org.driver.utils.Utils;

import java.lang.reflect.Proxy;

/**
 * Description : UsbRetorfit
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public final class UsbRetorfit {
    private Builder.Params P;

    private UsbRetorfit(Builder.Params params) {
        P = params;
    }


    @SuppressWarnings("unchecked")
    public final <T> T create(Class<T> service) {
        Utils.validateServiceInterface(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new DriverInvocationHandler<T>(P));
    }

    /***************************************************************************************************/
    // 基本配置类
    public static class Builder {

        private Params P;

        public Builder() {
            P = new Params();
        }

        // 执行驱动
        public Builder driver(UsbDrive drive) {
            P.drive = drive;
            return this;
        }

        // 适配兼容rxjava2 默认回调call
        public Builder addCallAdapter(Adapter adapter) {
            P.adapter = adapter;
            return this;
        }


        public UsbRetorfit build() {
            return new UsbRetorfit(P);
        }

        public static class Params {
            public UsbDrive drive;
            // 默认使用直接的call
            public Adapter adapter = Adapter.DEFAULT_FACTORY;
        }
    }
}
