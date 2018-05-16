package org.driver.adapter;

import org.driver.modle.Call;

import java.lang.reflect.Type;

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

    /**
     * 是否是返回的类型
     */
    boolean checkReturnType(Type type);


    Adapter DEFAULT_FACTORY = new Adapter() {
        @SuppressWarnings("unchecked")
        @Override
        public <T> T adapt(Call call) {
            return (T) call;
        }

        @Override
        public boolean checkReturnType(Type type) {
            return type==Call.class;
        }
    };

}
