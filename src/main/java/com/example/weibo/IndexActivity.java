package com.example.weibo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.weibo.activitys.web.WebActivity;
import com.example.weibo.fragments.home.HomeFragment;
import com.example.weibo.fragments.own.OwnFragment;
import com.example.weibo.fragments.trends.TrendsFragment;
import com.example.weibo.utils.SpUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndexActivity extends AppCompatActivity {

    @BindView(R.id.fragment_box)
    FrameLayout fragmentBox;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    HomeFragment homeFragment;
    TrendsFragment trendsFragment;
    OwnFragment ownFragment;

    FragmentTransaction fragmentTransaction;
    //用户隐私协议的提示框
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        ButterKnife.bind(this);
        initView();

        //是否是用户第一次进入
        boolean isfirst = SpUtils.getInstance().getBoolean("first");
        if (!isfirst) {
            showUserTips();
        }

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        fragmentTransaction.replace(R.id.fragment_box, homeFragment).commit();
                        break;
                    case R.id.menu_trends:
                        fragmentTransaction.replace(R.id.fragment_box, trendsFragment).commit();
                        break;
                    case R.id.menu_own:
                        fragmentTransaction.replace(R.id.fragment_box, ownFragment).commit();
                        break;
                }
                return false;
            }
        });

        //默认初始化显示第一个页面
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_box, homeFragment).commit();
    }

    private void initView() {
        //主页
        homeFragment = new HomeFragment();
        //动态
        trendsFragment = new TrendsFragment();
        //我的
        ownFragment = new OwnFragment();
    }

    //第一次进入软件的时候，弹出用户协议的提示框
    private void showUserTips() {
        if (alertDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_pop, null);
            TextView txtWord = view.findViewById(R.id.txt_word);
            TextView txtCancel = view.findViewById(R.id.txt_cancel);
            TextView txtOk = view.findViewById(R.id.txt_ok);
            txtCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    alertDialog = null;
                }
            });
            txtOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    alertDialog = null;
                    SpUtils.getInstance().setValue("first", true);
                }
            });
            String str1 = "欢迎您使用虎牙直播！\n我们将通过";
            String str2 = "《虎牙直播App隐私政策》";
            String str3 = "帮助您了解我们收集、使用、存储和共享个人信息的情况，以及您所享有的相关去权利。";
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(str1);
            SpannableString spannableString = new SpannableString(str2);
            //设置str2的点击事件
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    //Toast.makeText(IndexActivity.this, "智汇平台使用协议", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(IndexActivity.this, WebActivity.class);
                    intent.putExtra("url", "https://www.baidu.com");
                    startActivity(intent);
                }
            }, 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置str2的文字颜色
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(spannableString);
            SpannableString spannableString3 = new SpannableString(str3);
            builder.append(spannableString3);
            txtWord.setText(builder);
            txtWord.setMovementMethod(LinkMovementMethod.getInstance());
            alertDialog = new AlertDialog.Builder(this)
                    .setView(view)
                    .setCancelable(false)
                    .create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertDialog.show();
        }
    }
}
