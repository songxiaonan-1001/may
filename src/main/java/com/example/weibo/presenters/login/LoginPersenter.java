package com.example.weibo.presenters.login;

import com.example.weibo.base.BasePersenter;
import com.example.weibo.common.CommonSubscriber;
import com.example.weibo.interfaces.login.LoginConstract;
import com.example.weibo.models.HttpManager;
import com.example.weibo.models.bean.UserInfoBean;
import com.example.weibo.utils.RxUtils;

public class LoginPersenter extends BasePersenter<LoginConstract.View> implements LoginConstract.Persenter {
    @Override
    public void login(String username, String password) {
        addSubscribe(HttpManager.getInstance().getChatApi().login(username,password)
        .compose(RxUtils.rxScheduler())
        .subscribeWith(new CommonSubscriber<UserInfoBean>(mView) {
            @Override
            public void onNext(UserInfoBean userInfoBean) {
                mView.loginReturn(userInfoBean);
            }
        }));
    }
}
