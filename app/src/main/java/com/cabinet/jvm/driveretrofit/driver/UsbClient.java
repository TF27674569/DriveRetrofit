package com.cabinet.jvm.driveretrofit.driver;

import org.driver.UsbRetorfit;
import org.driver.Instructions;
import org.driver.adapter.RxJava2CallAdapter;
import org.driver.modle.UsbDrive;
import org.driver.modle.Call;

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
                .driver(new TestDriver())
                // 兼容rxjava2
                .addCallAdapter(RxJava2CallAdapter.create())
                .build();

        api = retorfit.create(UsbApi.class);
    }


    public static UsbApi get() {
        return api;
    }
}
