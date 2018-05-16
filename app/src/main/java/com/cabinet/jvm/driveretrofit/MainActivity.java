package com.cabinet.jvm.driveretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cabinet.jvm.driveretrofit.driver.UsbClient;

import org.driver.annoation.Log;
import org.driver.modle.Call;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                .subscribe(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] bytes) throws Exception {
                        // 因为我那里模拟的串口返回的值是拦截指令 这里返回的是check()函数的@Intercept({127,126,10,10,9,10,10,126,127}) 注解的值
                        android.util.Log.e("TAG", "onNext: "+Thread.currentThread().getName()+" "+printHex(bytes) );
                    }
                });


    }
}
