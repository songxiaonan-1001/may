package com.example.weibo.presenters.trends;

import com.example.weibo.base.BasePersenter;
import com.example.weibo.common.CommonSubscriber;
import com.example.weibo.interfaces.trends.TrendsStract;
import com.example.weibo.models.HttpManager;
import com.example.weibo.models.bean.TrendsBean;
import com.example.weibo.utils.RxUtils;

/**
 * 获取动态列表的操作
 */
public class TrendsPagerPersenter extends BasePersenter<TrendsStract.TrendsListView> implements TrendsStract.TrendsListPersenter {

    @Override
    public void queryTrends(int page, int size, int trendsid) {
        addSubscribe(HttpManager.getInstance().getChatApi().queryTrends(page,size,trendsid)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<TrendsBean>(mView) {
                    @Override
                    public void onNext(TrendsBean result) {
                        mView.queryTrendsReturn(result);
                    }
                }));
    }

}
