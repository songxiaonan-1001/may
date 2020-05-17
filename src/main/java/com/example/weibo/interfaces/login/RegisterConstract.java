package com.example.weibo.interfaces.login;


import com.example.weibo.interfaces.IBasePersenter;
import com.example.weibo.interfaces.IBaseView;

public interface RegisterConstract {

    interface View extends IBaseView {
        //void getVerifyReturn(VerifyBean result);
    }

    interface Persenter extends IBasePersenter<View> {
        void getVerify();
    }

}
