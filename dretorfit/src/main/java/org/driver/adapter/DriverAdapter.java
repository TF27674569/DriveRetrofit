package org.driver.adapter;

import org.driver.modle.Call;

/**
 * Description :
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class DriverAdapter implements Adapter {

    private Adapter adapter;

    public DriverAdapter(Adapter adapter) {
        this.adapter = adapter;
    }


    /**
     * 适配
     *
     * @param call
     */
    @Override
    public <T> T adapt(Call call) {
        return adapter.adapt(call);
    }
}
