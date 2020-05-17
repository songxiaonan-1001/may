package com.example.weibo.activitys.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.weibo.R;
import com.example.weibo.activitys.web.WebActivity;
import com.example.weibo.base.BaseActivity;
import com.example.weibo.interfaces.login.LoginConstract;
import com.example.weibo.models.bean.UserInfoBean;
import com.example.weibo.presenters.login.LoginPersenter;
import com.example.weibo.utils.SecretUtils;
import com.example.weibo.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginConstract.Persenter> implements LoginConstract.View {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_login_tips)
    TextView tvLoginTips;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_vcode_login)
    TextView tvVcodeLogin;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_third_login)
    TextView tvThirdLogin;
    @BindView(R.id.iv_yy)
    ImageView ivYy;
    @BindView(R.id.iv_wechat)
    ImageView ivWechat;
    @BindView(R.id.iv_qq)
    ImageView ivQq;
    @BindView(R.id.iv_sina)
    ImageView ivSina;
    @BindView(R.id.radiobtn)
    RadioButton radiobtn;
    @BindView(R.id.txt_tips)
    TextView txtTips;
   /* @BindView(R.id.et_account)
    EditText editUsername;
    @BindView(R.id.et_password)
    EditText editPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.radiobtn)
    RadioButton radiobtn;
    @BindView(R.id.txt_tips)
    TextView txtTips;*/

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        radiobtn.setChecked(true);
        String str1 = "已阅读并同意";
        String str2 = "《用户服务协议》";
        String str3 = "和";
        String str4 = "《隐私政策》";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(str1);
        SpannableString spannableString = new SpannableString(str2);
        //设置str2的点击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginActivity.this, WebActivity.class);
                intent.putExtra("url", "https://www.baidu.com");
                startActivity(intent);
            }
        }, 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置str2的文字颜色
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(spannableString);
        builder.append(str3);
        SpannableString spannableString4 = new SpannableString(str4);
        //设置str2的点击事件
        spannableString4.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginActivity.this, WebActivity.class);
                intent.putExtra("url", "https://www.baidu.com");
                startActivity(intent);
            }
        }, 0, str4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置str2的文字颜色
        spannableString4.setSpan(new ForegroundColorSpan(Color.BLUE), 0, str4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(spannableString4);
        txtTips.setText(builder);
        txtTips.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected LoginConstract.Persenter createPersenter() {
        return new LoginPersenter();
    }

    /**
     * 登录结果返回
     *
     * @param result
     */
    @Override
    public void loginReturn(UserInfoBean result) {
        if (result.getErr() == 200) {
            //把接收到的登录成功返回的值保存到sp
            SpUtils.getInstance().setValue("token", result.getData().getToken());
            SpUtils.getInstance().setValue("username", result.getData().getUsername());
            SpUtils.getInstance().setValue("avater", result.getData().getAvater());
            SpUtils.getInstance().setValue("phone", result.getData().getPhone());
            finish();
        } else {
            Toast.makeText(context, result.getErrmsg(), Toast.LENGTH_SHORT).show();
        }
    }

    //登录
    private void login() {
        String username = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(context, "请输入正确的用户名或密码", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断协议是否有同意
        if (!radiobtn.isChecked()) {
            Toast.makeText(context, "未同意协议内容", Toast.LENGTH_SHORT).show();
            return;
        }

        //把输入的密码进行sha1加密
        password = SecretUtils.getSha1(password);
        //正常的业务逻辑下 password
        persenter.login(username, password);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_close, R.id.tv_login, R.id.radiobtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.radiobtn:
                break;
            default:
        }
    }
}
