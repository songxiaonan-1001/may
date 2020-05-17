package com.example.weibo.presenters.trends;

import com.example.weibo.base.BasePersenter;
import com.example.weibo.common.CommonSubscriber;
import com.example.weibo.interfaces.trends.TrendsStract;
import com.example.weibo.models.HttpManager;
import com.example.weibo.models.bean.PublishTrendsBean;
import com.example.weibo.utils.RxUtils;

public class TrendsPersenter extends BasePersenter<TrendsStract.View> implements TrendsStract.Persenter {
    @Override
    public void sendTrends(String content, String resources) {
        addSubscribe(HttpManager.getInstance().getChatApi().sendTrends(content, resources)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<PublishTrendsBean>(mView) {
                    @Override
                    public void onNext(PublishTrendsBean result) {
                        mView.sendTrendsReturn(result);
                    }
                }));
    }
}
