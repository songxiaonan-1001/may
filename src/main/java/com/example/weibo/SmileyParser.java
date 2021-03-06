package com.example.weibo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.example.weibo.anim.gif.AnimatedGifDrawable;
import com.example.weibo.anim.gif.AnimatedImageSpan;
import com.example.weibo.app.MyApp;
import com.example.weibo.models.vo.FaceListItemVo;
import com.example.weibo.models.vo.FaceTabVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmileyParser {
    public static int FACE_TYPE_1 = 1; //图文混排的表情
    public static int FACE_TYPE_2 = 2; //常规的表情


    private Context mContext;
    private String[] faceTabArr;
    private String[] mSmileyTexts;
    private String[] mSmileyIcons;
    private Pattern mPattern;
    private HashMap<String, Integer> mSmileyToRes;

    private List<FaceTabVo> faceTabList;
    private HashMap<Integer,List<FaceListItemVo>> allFaceMap;

    //表情的tab图片id
    public static final int[] FACE_TABS = {
            R.mipmap.aini,
            R.mipmap.icon_002_cover,
    };

    //小表情资源
    public static final int[] DEFAULT_SMILEY_RES_IDS = {
        R.raw.aini,
        R.raw.aoteman,
        R.raw.baibai,
        R.raw.baobao,
        R.raw.beiju,
        R.raw.beishang,
        R.raw.bianbian,
        R.raw.bishi,
        R.raw.bizui,
        R.raw.buyao,
        R.raw.chanzui,
    };

    //大表情资源
    public static final int[] DEFAULT_SMILEY_RES_ICONS = {
            R.raw.icon_002,
            R.raw.icon_007,
            R.raw.icon_010,
            R.raw.icon_012,
            R.raw.icon_013,
            R.raw.icon_018,
            R.raw.icon_019,
            R.raw.icon_020,
            R.raw.icon_021,
            R.raw.icon_022,
            R.raw.icon_024,
            R.raw.icon_027,
            R.raw.icon_029,
            R.raw.icon_030,
            R.raw.icon_035,
            R.raw.icon_040,

    };

    private static SmileyParser smileyParser;
    public static SmileyParser getInstance(Context context){
        synchronized (SmileyParser.class){
            if(smileyParser == null){
                synchronized (SmileyParser.class){
                    smileyParser = new SmileyParser(context);
                }
            }
        }
        return smileyParser;
    }


    public SmileyParser(Context context) {
        mContext = context;
        faceTabArr = mContext.getResources().getStringArray(R.array.smile_tab);
        mSmileyTexts = mContext.getResources().getStringArray(DEFAULT_SMILEY_TEXTS);
        mSmileyToRes = buildSmileyToRes();
        mSmileyIcons = mContext.getResources().getStringArray(R.array.default_smiley_icon);
        mPattern = buildPattern();
        buildFace();
        //tab数据初始化
        initFaceTab();

    }

    public static final int DEFAULT_SMILEY_TEXTS = R.array.default_smiley_texts;

    private HashMap<String, Integer> buildSmileyToRes() {
        if (DEFAULT_SMILEY_RES_IDS.length != mSmileyTexts.length) {
//        	Log.w("SmileyParser", "Smiley resource ID/text mismatch");
            throw new IllegalStateException("Smiley resource ID/text mismatch");
        }

        HashMap<String, Integer> smileyToRes = new HashMap<String, Integer>(mSmileyTexts.length);
        for (int i = 0; i < mSmileyTexts.length; i++) {
            smileyToRes.put(mSmileyTexts[i], DEFAULT_SMILEY_RES_IDS[i]);
        }

        return smileyToRes;
    }

    /**
     * 初始化face资源
     */
    private void buildFace(){
        allFaceMap = new HashMap<>();
        List<FaceListItemVo> list_small = getFaceItemList(mSmileyTexts,DEFAULT_SMILEY_RES_IDS,FACE_TYPE_1);  //图文混排表情的整合
        allFaceMap.put(0,list_small);
        List<FaceListItemVo> list_big = getFaceItemList(mSmileyIcons,DEFAULT_SMILEY_RES_ICONS,FACE_TYPE_2);  //大表情的整合
        allFaceMap.put(1,list_big);
    }

    /**
     * 把表情整合到list集合
     * @param arr
     * @param ids
     * @param faceType
     * @return
     */
    private List<FaceListItemVo> getFaceItemList(String[] arr,int[] ids,int faceType){
        List<FaceListItemVo> list = new ArrayList<>();
        for(int i=0; i<arr.length; i++){
            String temp = arr[i];
            int end = temp.lastIndexOf("]");
            String name = temp.substring(1,end-1);
            FaceListItemVo itemVo = new FaceListItemVo();
            itemVo.setFaceId(ids[i]);
            itemVo.setName(name);
            itemVo.setTag(temp);
            itemVo.setPosition(i);
            itemVo.setFaceType(faceType);
            list.add(itemVo);
        }
        return list;
    }

    /**
     * 初始化表情分类导航的数据
     */
    private void initFaceTab(){
        faceTabList = new ArrayList<>();
        for(int i=0; i<faceTabArr.length; i++){
            FaceTabVo tabVo = new FaceTabVo();
            tabVo.setFaceId(FACE_TABS[i]);
            tabVo.setPostion(i);
            faceTabList.add(tabVo);
        }
    }

    /**
     * 表情正则表达式的初始化
     * @return
     */
    private Pattern buildPattern() {
        StringBuilder patternString = new StringBuilder((mSmileyTexts.length+mSmileyIcons.length) * 3);
        patternString.append('(');
        for (String s : mSmileyTexts) {
            patternString.append(Pattern.quote(s));
            patternString.append('|');
        }
        for (String s : mSmileyIcons){
            patternString.append(Pattern.quote(s));
            patternString.append('|');
        }
        patternString.replace(patternString.length() - 1, patternString.length(), ")");
        return Pattern.compile(patternString.toString());
    }

    public List<FaceTabVo> getFaceTabList(){
        return faceTabList;
    }

    /**
     * 获取对应tab分类下的表情
     * @param pos
     * @return
     */
    public List<FaceListItemVo> getFaceItemListByPos(int pos){
        return allFaceMap.get(pos);
    }

    public int getFaceListSize(){
        return allFaceMap.size();
    }

    /**
     * 解析图文混排非动画表情内容
     * @param text
     * @return
     */
    public CharSequence replace(CharSequence text) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        Matcher matcher = mPattern.matcher(text);
        while (matcher.find()) {
            int resId = mSmileyToRes.get(matcher.group());
            builder.setSpan(new ImageSpan(mContext, resId),matcher.start(), matcher.end(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);     //没有动画处理
        }
        return builder;
    }

    /**
     * 解析含有动画的表情包含(图文混排，大表情)
     * @param txtView
     * @param text
     * @return
     */
    public CharSequence replace(TextView txtView,CharSequence text,int faceType){
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        Matcher matcher = mPattern.matcher(text);
        while (matcher.find()) {
            int resId = getSmailyIdByTag(matcher.group(),faceType);
            if(resId == 0) continue;
            int width=0,height=0;
            //如果是图文混排的表情 修改显示表情的大小
            if(faceType == FACE_TYPE_1){
                width = height = 50;
            }
            AnimatedGifDrawable animatedGifDrawable = new AnimatedGifDrawable();
            animatedGifDrawable.onCreate(MyApp.myApp.getResources().openRawResource(resId), new AnimatedGifDrawable.UpdateListener() {
                @Override
                public void update() {
                    txtView.postInvalidate();
                }
            },width,height);
            builder.setSpan(new AnimatedImageSpan(animatedGifDrawable),matcher.start(),matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    /**
     * 获取当前动画表情ID
     * @param tag
     * @param faceType
     * @return
     */
    private int getSmailyIdByTag(String tag,int faceType){
        Integer key = faceType == FACE_TYPE_1 ? 0 : 1;
        List<FaceListItemVo> temp = allFaceMap.get(key);
        for(FaceListItemVo item:temp){
            if(item.getTag().equals(tag)){
                return item.getFaceId();
            }
        }
        return 0;
    }


    public CharSequence addSmaile(int resId){
        String str = "[0]";
        SpannableString span = new SpannableString(str);
        Drawable drawable = mContext.getResources().getDrawable(resId);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        ImageSpan img = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
        int start = str.indexOf("[");
        int end = str.indexOf("]")+1;
        span.setSpan(img,start,end,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return span;
    }


}
