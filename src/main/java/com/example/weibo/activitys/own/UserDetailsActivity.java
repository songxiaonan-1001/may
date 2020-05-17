package com.example.weibo.activitys.own;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.weibo.R;
import com.example.weibo.app.GlideEngine;
import com.example.weibo.base.BaseActivity;
import com.example.weibo.interfaces.own.UserConstact;
import com.example.weibo.models.bean.DetailsUpdateBean;
import com.example.weibo.models.bean.UserDetailsBean;
import com.example.weibo.presenters.own.DetailsPersenter;
import com.example.weibo.utils.UserUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 个人信息activity
 */
public class UserDetailsActivity extends BaseActivity<UserConstact.DetailsPersenter> implements UserConstact.DetailsView {

    public static final int CODE_HEADCROPACTIVITY = 200;

    /*@BindView(R.id.layout_nickname)
    ConstraintLayout nicknameLayout;
    @BindView(R.id.layout_sex)
    ConstraintLayout sexLayout;
    @BindView(R.id.layout_age)
    ConstraintLayout ageLayout;
    @BindView(R.id.layout_signature)
    ConstraintLayout signLayout;
    @BindView(R.id.layout_grade)
    ConstraintLayout levelLayout;
    @BindView(R.id.layout_head)
    ConstraintLayout layoutHead;
    @BindView(R.id.iv_header)
    ImageView ivHeader;*/

    UserDetailsBean userDetailsBean;

    String editContent; //当前修改的内容
    TextView curTxt; //当前操作的信息文本
    String newAvater; //当前最新的头像地址
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.iv_header_arrow_right)
    ImageView ivHeaderArrowRight;
    @BindView(R.id.layout_head)
    ConstraintLayout layoutHead;
    @BindView(R.id.layout_nickname)
    ConstraintLayout layoutNickname;
    @BindView(R.id.layout_sex)
    ConstraintLayout layoutSex;
    @BindView(R.id.layout_age)
    ConstraintLayout layoutAge;
    @BindView(R.id.layout_address)
    ConstraintLayout layoutAddress;
    @BindView(R.id.layout_signature)
    ConstraintLayout layoutSignature;
    @BindView(R.id.layout_grade)
    ConstraintLayout layoutGrade;
    @BindView(R.id.layout_title)
    ConstraintLayout layoutTitle;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_nickname_arrow_right)
    ImageView ivNicknameArrowRight;
    @BindView(R.id.tv_huyanumber)
    TextView tvHuyanumber;
    @BindView(R.id.iv_huyanumber_arrow_right)
    ImageView ivHuyanumberArrowRight;
    @BindView(R.id.layout_huya_number)
    ConstraintLayout layoutHuyaNumber;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.iv_sex_arrow_right)
    ImageView ivSexArrowRight;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.iv_age_arrow_right)
    ImageView ivAgeArrowRight;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_address_arrow_right)
    ImageView ivAddressArrowRight;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.iv_signature_arrow_right)
    ImageView ivSignatureArrowRight;
    @BindView(R.id.iv_grade)
    ImageView ivGrade;
    @BindView(R.id.iv_grade_arrow_right)
    ImageView ivGradeArrowRight;
    @BindView(R.id.iv_video_arrow_right)
    ImageView ivVideoArrowRight;
    @BindView(R.id.layout_video_management)
    ConstraintLayout layoutVideoManagement;
    @BindView(R.id.iv_drafts_arrow_right)
    ImageView ivDraftsArrowRight;
    @BindView(R.id.layout_drafts)
    ConstraintLayout layoutDrafts;
    @BindView(R.id.iv_binding_arrow_right)
    ImageView ivBindingArrowRight;
    @BindView(R.id.layout_binding_phone)
    ConstraintLayout layoutBindingPhone;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_details;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        //获取用户的详情信息
        persenter.getDetails();
    }

    @Override
    protected UserConstact.DetailsPersenter createPersenter() {
        return new DetailsPersenter();
    }

    @Override
    public void getDetailsReturn(UserDetailsBean result) {
        userDetailsBean = result;
        //头像刷新
        if (result.getData().getAvater() != null && !TextUtils.isEmpty((CharSequence) result.getData().getAvater())) {
            Glide.with(context).load(result.getData().getAvater()).into(ivHeader);
        }
        //显示昵称
        if (result.getData().getNickname() != null && !TextUtils.isEmpty((CharSequence) result.getData().getNickname())) {
            ((TextView) tvNickname.findViewById(R.id.tv_nickname)).setText((String) result.getData().getNickname());
        } else {
            ((TextView) tvNickname.findViewById(R.id.tv_nickname)).setText("请输入");
        }
        //显示性别
        if (result.getData().getSex() == 0) {
            ((TextView) tvSex.findViewById(R.id.tv_sex)).setText("无");
        } else if (result.getData().getSex() == 1) {
            ((TextView) tvSex.findViewById(R.id.tv_sex)).setText("男");
        } else {
            ((TextView) tvSex.findViewById(R.id.tv_sex)).setText("女");
        }
        //签名
        if (result.getData().getSign() != null && !TextUtils.isEmpty((CharSequence) result.getData().getSign())) {
            ((TextView) tvSignature.findViewById(R.id.tv_signature)).setText((String) result.getData().getSign());
        } else {
            ((TextView) tvSignature.findViewById(R.id.tv_signature)).setText("请输入");
        }
    }

    /**
     * 处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // onResult Callback  本地相机相册图片选取的结果
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() == 0) {
                    return;
                }
                //打开图片剪切界面 图片上传
                Intent intent = new Intent(UserDetailsActivity.this, HeadCropActivity.class);
                intent.putExtra("img_path", selectList.get(0).getPath());
                startActivityForResult(intent, CODE_HEADCROPACTIVITY);
                break;
            case CODE_HEADCROPACTIVITY:
                //处理图片剪切页面图片上传结果的地址
                String imgUrl = data.getStringExtra("imgUrl");
                Map<String, String> map = new HashMap<>();
                map.put("avater", imgUrl);
                persenter.updateDetails(map);
                //更新当前页面的头像
                break;
            default:
                break;
        }
    }

    /**
     * 更新用户信息返回
     *
     * @param result
     */
    @Override
    public void updateDetailsReturn(DetailsUpdateBean result) {
        if (result.getErr() == 200) {
            if (curTxt != null && !TextUtils.isEmpty(editContent)) {
                curTxt.setText(editContent);
                editContent = null;
                curTxt = null;
            }
            if (!TextUtils.isEmpty(newAvater)) {
                Glide.with(context).load(newAvater).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        newAvater = null;
                        return false;
                    }
                }).into(ivHeader);
            }

        }
    }

    @OnClick({R.id.iv_back, R.id.iv_header, R.id.iv_header_arrow_right, R.id.layout_head, R.id.layout_nickname, R.id.layout_sex, R.id.layout_age, R.id.layout_address, R.id.layout_signature, R.id.layout_grade})
    public void onViewClicked(View view) {
        if (userDetailsBean == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_back:
                //关闭本页面
                finish();
                break;
            case R.id.iv_header:
                openLocalPhoto();
                break;
            case R.id.iv_header_arrow_right:
                break;
            case R.id.layout_head:
                break;
            case R.id.layout_nickname:
                curTxt = ((TextView) tvNickname.findViewById(R.id.tv_nickname));
                openDialog("修改昵称", (String) userDetailsBean.getData().getNickname(), "nickname");
                Log.i("tag", "onViewClicked: 修改昵称");
                break;
            case R.id.layout_sex:
                curTxt = ((TextView) tvSex.findViewById(R.id.tv_sex));
                openDialog("修改性别", UserUtils.parseSex(userDetailsBean.getData().getSex()), "sex");
                Log.i("tag", "onViewClicked: 修改性别");
                break;
            case R.id.layout_age:
                curTxt = ((TextView) tvAge.findViewById(R.id.tv_age));
                openDialog("修改年龄", (String) userDetailsBean.getData().getAge(), "age");
                Log.i("tag", "onViewClicked: 修改年龄");
                break;
            case R.id.layout_address:
                break;
            case R.id.layout_signature:
                curTxt = ((TextView) tvSignature.findViewById(R.id.tv_signature));
                openDialog("修改签名", (String) userDetailsBean.getData().getSign(), "sign");
                Log.i("tag", "onViewClicked: 修改签名");
                break;
            case R.id.layout_grade:
                break;
            default:
        }
    }

    /**
     * 打开修改信息的弹框
     * title当前修改的内容
     * value当前要修改的数据值
     */
    private void openDialog(String title, String value, String key) {
        View view = LayoutInflater.from(UserDetailsActivity.this).inflate(R.layout.layout_edit_detailsinfo, null);
        //自定义弹窗
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .show();

        //alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView txtTitle = view.findViewById(R.id.txt_edit_title);
        EditText editWord = view.findViewById(R.id.edit_word);
        LinearLayout layout_sex = view.findViewById(R.id.layout_sex);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        if (key.equals("sex")) {
            layout_sex.setVisibility(View.VISIBLE);
            editWord.setVisibility(View.GONE);
        } else {
            layout_sex.setVisibility(View.GONE);
            editWord.setVisibility(View.VISIBLE);
        }
        txtTitle.setText(title);
        if (!TextUtils.isEmpty(value)) {
            editWord.setHint(value);
        } else {
            editWord.setHint("请输入内容");
        }
        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key.equals("sex")) {
                    int curId = radioGroup.getCheckedRadioButtonId();
                    if (curId < 0) {
                        Toast.makeText(context, "未输入任何内容", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, String> map = new HashMap<>();
                        if (curId == R.id.radioBtn_man) {
                            map.put(key, "1");
                            editContent = "男";
                        } else {
                            map.put(key, "2");
                            editContent = "女";
                        }
                        //调用后台接口，更新用户信息
                        persenter.updateDetails(map);
                        alertDialog.dismiss();
                    }
                } else {
                    editContent = editWord.getText().toString();
                    if (!TextUtils.isEmpty(editContent)) {
                        Map<String, String> map = new HashMap<>();
                        map.put(key, editContent);
                        //调用后台接口，更新用户信息
                        persenter.updateDetails(map);
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "未输入任何内容", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /**
         * 关闭编辑框的监听
         */
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //editContent = null;
                //curTxt = null;
            }
        });

    }

    /**
     * 打开本地相机相册选取图片的功能
     */
    private void openLocalPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .selectionMode(PictureConfig.SINGLE)
                .imageSpanCount(4)
                .previewImage(true)
                .isCamera(true)
                .enableCrop(true)
                .compress(true)
                .rotateEnabled(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

}
