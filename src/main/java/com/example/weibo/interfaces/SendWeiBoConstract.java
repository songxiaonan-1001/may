package com.example.weibo.interfaces;

import com.example.weibo.models.bean.BlogBean;

/**
 * 动态的契约类
 */
public interface SendWeiBoConstract {

    interface View extends IBaseView{
        //发布动态返回的接口
        void sendTrendsReturn(BlogBean result);
    }

    interface Persenter extends IBasePersenter<View>{
        //发布动态的接口
        void sendTrends(String content,String resources);
    }
}
