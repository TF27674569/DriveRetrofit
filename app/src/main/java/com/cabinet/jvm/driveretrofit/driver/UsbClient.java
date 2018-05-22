package com.cabinet.jvm.driveretrofit.driver;


import org.usb.retorfit.UsbRetorfit;
import org.usb.retorfit.factory.RxJava2CallAdapterFactory;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class UsbClient {

    private final static UsbApi api;

    static {
        UsbRetorfit retorfit = new UsbRetorfit.Builder()
                // 兼容rxjava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retorfit.create(UsbApi.class);
    }


    public static UsbApi get() {
        return api;
    }
}
