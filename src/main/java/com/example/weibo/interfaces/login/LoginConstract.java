package com.example.weibo.interfaces.login;


import com.example.weibo.interfaces.IBasePersenter;
import com.example.weibo.interfaces.IBaseView;
import com.example.weibo.models.bean.UserInfoBean;

public interface LoginConstract {

    interface View extends IBaseView {
        void loginReturn(UserInfoBean result);
    }

    interface Persenter extends IBasePersenter<View> {
        void login(String username, String password);
    }

}
