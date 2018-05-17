package com.cabinet.jvm.driveretrofit.driver;

import org.driver.annoation.Adress;
import org.driver.annoation.End;
import org.driver.annoation.Fun;
import org.driver.annoation.Head;
import org.driver.annoation.Intercept;
import org.driver.annoation.Log;
import org.driver.annoation.Retry;


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
    @Retry(0x05)
    @End(0xfeff)
    @Intercept({-1,-2,10,10,9,10,10,-2,-1})
    Observable<byte[]> check();

    /**
     * 只拦截某指令
     */
    @Retry(0x05)
    @Intercept({-1,-2,1,1,9,1,0,-2,-1})
    Observable<byte[]> check2();


    /**
     * 发送和拦截此指令
     */
    @Head(0xfffe)
    @Adress(0x05)
    @Fun(0x06)
    @Log(Log.Logger.ON)
    @Retry(0x05)
    @End(0xfeff)
    Observable<byte[]> check1(@Intercept byte[] in);
}
