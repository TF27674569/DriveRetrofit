# UsbRetorfit
### **使用配置**

##### **依赖 compile 'com.tianfeng:usbretorfit:2.0.2'**

1.与retorfit一样建一个Client类
```java
public class UsbClient {

    private final static UsbApi api;

    static {
        UsbRetorfit retorfit = new UsbRetorfit.Builder()
                // 实现部分
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
```

```java


public class TestDriver implements UsbDrive {
    /**
     * 发送指令
     *
     * @param info 封装了发送指令，拦截指令，重试次数，间隔时间等 ，一定需要用callback回调
     * @param callback     返回结果
     */
    @Override
    public void execute(final Instructions info, final Call.Callback callback) {
        // 发送指令可以：https://github.com/TF27674569/Command

        // 这里模拟串口通信
        Executors.newSingleThreadExecutor()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // 这里如果是进行串口通信
                        // 发送指令
                        // send(info.getSend(),info.getIntercept());
                        // 如果拿到返回结果
                        byte[] intercept = info.getIntercept();
                        try {
                            callback.onSuccess(intercept);
                        } catch (Exception e) {
                            callback.onError(e.getMessage());
                        }

                    }
                });
    }
}
```
配合[UsbDriver](https://github.com/TF27674569/UsbDrive)使用
```java
public class UsbClient {

    private static UsbApi api;


    public static void init(Context context) {
        OkDriveClient client = new OkDriveClient.Builder(context)
                .intercept(new CrcInterceptor())
                .timeOut(50000)
                .build();

        UsbRetorfit retorfit = new UsbRetorfit.Builder()
                .client(client)
                // 兼容rxjava2
                .addCallAdapter(RxJava2CallAdapter.create())
                .build();

        api = retorfit.create(UsbApi.class);
    }



    public static UsbApi get() {
        return api;
    }
}
```
2.使用建一个接口类，通过注解拼接指令，真是指令以函数参数所带值为准，check1的Intercept以参数的intercept为准
```java
public interface UsbApi {

    @Head(0xfffe)
    @Adress(0x05)
    @Fun(0x06)
    @Log(Log.Logger.ON)
    @Retry(0x05)
    @End(0xfeff)
    @Intercept({-1,-2,10,10,9,10,10,-2,-1})
    Observable<byte[]> check();

    @Head(0xfffe)
    @Adress(0x05)
    @Fun(0x06)
    @Log(Log.Logger.ON)
    @Retry(0x05)
    @End(0xfeff)
    @Intercept({-1,-2,10,10,9,10,10,-2,-1})
    Call check1(@Intercept byte[] intercept);
}
```
3.调用
````java
 UsbClient.get()
                .check()
                .subscribe(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] bytes) throws Exception {
                        // 因为我那里模拟的串口返回的值是拦截指令 这里返回的是check()函数的@Intercept({127,126,10,10,9,10,10,126,127}) 注解的值
                        android.util.Log.e("TAG", "onNext: "+printHex(bytes) );
                    }
                });
````

4. 由于指令的拼接方式是与单片机协商好的协议，根据需求修改ServiceMethod和注解以及拼接方式