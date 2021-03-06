package com.cabinet.jvm.driveretrofit.driver;


import android.content.Context;

import com.cabinet.jvm.driveretrofit.MainActivity;

import org.usb.driver.Instruct;
import org.usb.driver.OkUsbClient;
import org.usb.driver.template.Interceptor;
import org.usb.retorfit.UsbRetorfit;
import org.usb.retorfit.factory.OkUsbDriver;
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

    private  static UsbApi api;

    public static void init(Context context){
        OkUsbDriver build = new OkUsbDriver.Builder(context)
                .timeOut(30000)
                .intercept(new Interceptor() {
                    @Override
                    public void intercept(Chain chain) {
                        Instruct instruct = chain.instruct();
                        byte[] send = instruct.getSend();
                        // 打印指令
                        MainActivity.printHex(send);
                        chain.proceed(instruct);
                    }
                })
                .build();
        UsbRetorfit retorfit = new UsbRetorfit.Builder()
                // 兼容rxjava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addUsbDriver(build)
                .build();

        api = retorfit.create(UsbApi.class);
    }


    public static UsbApi get() {
        return api;
    }
}
