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
public interface Adapter {

    /**
     * 适配
     */
    <T> T adapt(Call call);


    Adapter DEFAULT_FACTORY = new Adapter() {
        @SuppressWarnings("unchecked")
        @Override
        public <T> T adapt(Call call) {
            return (T) call;
        }
    };

}
