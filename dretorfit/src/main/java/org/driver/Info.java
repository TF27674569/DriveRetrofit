package org.driver;

/**
 * Description : 指令封装对象
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class Info {
    /**
     * 拦截命令
     */
    private byte[] intercept;

    /**
     * 发送命令
     */
    private byte[] send;

    /**
     * 重试次数
     */
    private int retryCount = 0;

    /**
     * 重试间隔
     */
    private long interval = 0;

    public Info(byte[] intercept, byte[] send, int retryCount, long interval) {
        this.intercept = intercept;
        this.send = send;
        this.retryCount = retryCount;
        this.interval = interval;
    }

    public byte[] getIntercept() {
        return intercept;
    }

    public void setIntercept(byte[] intercept) {
        this.intercept = intercept;
    }

    public byte[] getSend() {
        return send;
    }

    public void setSend(byte[] send) {
        this.send = send;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
