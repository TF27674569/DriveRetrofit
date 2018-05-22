package org.usb.retorfit;

import org.usb.retorfit.factory.DefauleCallAdapterFactory;
import org.usb.retorfit.factory.DefaultServiceParserFactory;
import org.usb.retorfit.proxy.ProxyInvocationHandler;
import org.usb.retorfit.template.CallAdapter;
import org.usb.retorfit.template.ServiceParser;
import org.usb.retorfit.template.UsbDriver;
import org.usb.retorfit.utils.Utils;

import java.lang.reflect.Proxy;

/**
 * Description : 代理发生器
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
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

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new ProxyInvocationHandler(P));
    }

    /**
     * 建造器
     */
    public static class Builder {

        private Params params;

        public Builder() {
            params = new Params();
        }

        /**
         * 驱动
         */
        public Builder addUsbDriverFactory(UsbDriver.Factory factory) {
            params.usbDriverFactory = factory;
            return this;
        }

        /**
         * 抽象适配工厂
         */
        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            params.adapterFactory = factory;
            return this;
        }

        /**
         * 抽象适配工厂
         */
        public Builder addServiceParserFactory(ServiceParser.Factory factory) {
            params.serviceParserFactory = factory;
            return this;
        }

        /**
         * 构建
         */
        public UsbRetorfit build() {
            Utils.checkNotNull(params.usbDriverFactory, "UsbDriver is null !!!");
            return new UsbRetorfit(params);
        }


        /**
         * 参数封装类
         */
        public static class Params {
            /**
             * 抽象适配工厂
             */
            public CallAdapter.Factory adapterFactory = DefauleCallAdapterFactory.DEFAULT_FACTORY;

            /**
             * 解析器工厂 默认只解析现有注解
             */
            public ServiceParser.Factory serviceParserFactory = DefaultServiceParserFactory.DEFAULT_SERVICE_PARSER_FACTORY;

            // 驱动
            public UsbDriver.Factory usbDriverFactory;

            private Params() {
            }
        }
    }
}
