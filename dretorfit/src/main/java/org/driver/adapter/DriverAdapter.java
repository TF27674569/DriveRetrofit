package org.driver.adapter;

import org.driver.modle.Call;
import org.driver.utils.Utils;

import java.lang.reflect.Type;

/**
 * Description : 代理适配器
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/15
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class DriverAdapter implements Adapter {

    private Adapter adapter;
    private Type type;

    public DriverAdapter(Adapter adapter, Type type) {
        this.adapter = adapter;
        this.type = type;
    }


    /**
     * 适配
     *
     * @param call
     */
    @Override
    public <T> T adapt(Call call) {
        // 检测是返回值是否合法
        Utils.checkReturnType(checkReturnType(type),legitimateReturnTypeName());

        // 调用适配器
        return adapter.adapt(call);
    }

    /**
     * 是否是返回的类型
     *
     * @param type
     */
    @Override
    public boolean checkReturnType(Type type) {
        return adapter.checkReturnType(type);
    }

    /**
     * @return
     */
    @Override
    public String legitimateReturnTypeName() {
        return adapter.legitimateReturnTypeName();
    }
}
