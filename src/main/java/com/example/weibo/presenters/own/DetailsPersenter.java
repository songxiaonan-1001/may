package com.example.weibo.presenters.own;


import com.example.weibo.base.BasePersenter;
import com.example.weibo.common.CommonSubscriber;
import com.example.weibo.interfaces.own.UserConstact;
import com.example.weibo.models.HttpManager;
import com.example.weibo.models.bean.DetailsUpdateBean;
import com.example.weibo.models.bean.UserDetailsBean;
import com.example.weibo.utils.RxUtils;

import java.util.Map;

public class DetailsPersenter extends BasePersenter<UserConstact.DetailsView> implements UserConstact.DetailsPersenter {
    @Override
    public void getDetails() {
        addSubscribe(HttpManager.getInstance().getChatApi().getUserDetails()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<UserDetailsBean>(mView) {
                    @Override
                    public void onNext(UserDetailsBean result) {
                        mView.getDetailsReturn(result);
                    }
                }));
    }

    /**
     * 更新用户信息
     * @param map
     */
    @Override
    public void updateDetails(Map<String, String> map) {
        addSubscribe(HttpManager.getInstance().getChatApi().updateUserDetails(map)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<DetailsUpdateBean>(mView) {
                    @Override
                    public void onNext(DetailsUpdateBean result) {
                        mView.updateDetailsReturn(result);
                    }
                }));
    }
}
