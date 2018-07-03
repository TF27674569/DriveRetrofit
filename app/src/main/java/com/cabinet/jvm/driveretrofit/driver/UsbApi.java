package com.cabinet.jvm.driveretrofit.driver;



import org.usb.retorfit.annotation.Adress;
import org.usb.retorfit.annotation.End;
import org.usb.retorfit.annotation.Fun;
import org.usb.retorfit.annotation.Head;
import org.usb.retorfit.annotation.Intercept;
import org.usb.retorfit.annotation.Log;
import org.usb.retorfit.annotation.Count;

import io.reactivex.Observable;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public interface UsbApi {

    /**
     * 发送指令后 拦截新的指令
     */
    @Head(0xfffe)
    @Adress(0x05)
    @Fun(0x06)
    @Log(Log.Logger.ON)
    @Count(0x05)
    @End(0xfeff)
    @Intercept({-1,-2,10,10,9,10,10,-2,-1})
    Observable<byte[]> check();

    /**
     * 只拦截某指令
     */
    @Head(0xfffe)
    @Adress(0x03)
    @Fun(0x05)
    @Log(Log.Logger.ON)
    @Count(0x05)
    @End(0xfeff)
    Observable<byte[]> check2();


    /**
     * 发送和拦截此指令
     */
    @Head(0xfffe)
    @Adress(0x05)
    @Fun(0x06)
    @Log(Log.Logger.ON)
    @Count(0x05)
    @End(0xfeff)
    Observable<byte[]> check1(@Intercept byte[] in);
}
