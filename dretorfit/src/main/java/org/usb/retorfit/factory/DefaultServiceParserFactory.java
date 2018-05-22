package org.usb.retorfit.factory;

import android.support.annotation.NonNull;

import org.usb.retorfit.proxy.ProxyServiceParser;
import org.usb.retorfit.template.ServiceParser;

/**
 * Description : 默认的解析器对象
 * <p/>
 * Created : TIAN FENG
 * Date : 2018/5/22
 * Email : 27674569@qq.com
 * Version : 1.0
 */
public class DefaultServiceParserFactory implements ServiceParser.Factory {

    public static final DefaultServiceParserFactory DEFAULT_SERVICE_PARSER_FACTORY = new DefaultServiceParserFactory();

    /**
     * 返回解析实体对象
     */
    @NonNull
    @Override
    public ServiceParser get() {
        return new ProxyServiceParser();
    }
}
