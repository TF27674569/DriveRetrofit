package com.cabinet.jvm.driveretrofit;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cabinet.jvm.driveretrofit.driver.UsbClient;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements Runnable {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsbClient.init(this);

        handler.postDelayed(this,3000);
    }



    public static String printHex(byte[] command) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < command.length; i++) {
            byte b = command[i];
            int i1 = byteToInt(b);
            String s = Integer.toHexString(i1);
            if (s.length() == 1) {
                s = "0" + s;
            }
            buffer.append(s);
        }
        return buffer.toString();
    }

    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    public void click(View view) {
        UsbClient.get()
                .check2()
                .subscribe(new Observer<byte[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(byte[] bytes) {
                        android.util.Log.e("TAG", "check2: "+Thread.currentThread().getName()+" "+printHex(bytes) );
                    }

                    @Override
                    public void onError(Throwable e) {
                        android.util.Log.e("TAG", "check2: "+e.getMessage() );
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void run() {
        click(null);
        handler.postDelayed(this,3000);
    }
}
