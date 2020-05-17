package com.example.weibo.interfaces.own;

import com.example.weibo.interfaces.IBasePersenter;
import com.example.weibo.interfaces.IBaseView;
import com.example.weibo.models.bean.DetailsUpdateBean;
import com.example.weibo.models.bean.UserDetailsBean;

import java.util.Map;

public interface UserConstact {

    interface DetailsView extends IBaseView {
        void getDetailsReturn(UserDetailsBean result);

        //更新用户详情信息的返回
        void updateDetailsReturn(DetailsUpdateBean result);
    }

    interface DetailsPersenter extends IBasePersenter<DetailsView> {
        void getDetails();

        //更新用户信息
        void updateDetails(Map<String, String> map);
    }

}
