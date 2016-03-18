package com.umall.ydz.calendartest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import interfaces.JavaScriptObject;

public class MainActivity extends Activity{
    /**
     * js 桥
     */
    TextView mTextview;
    Button mBtn1;
    WebView mWebView;
    Context mContext;

    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge);
        //初始化
        initViews();

        //设置编码
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置背景颜色 透明
        mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        //设置本地调用对象及其接口
        mWebView.addJavascriptInterface(new JavaScriptObject(mContext), "myObj");
        //载入js
        mWebView.loadUrl("file:///android_asset/test.html");

        //点击调用js中方法
        mBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:funFromjs()");
                Toast.makeText(mContext, "调用javascript:funFromjs()", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void initViews() {

        mBtn1 = (Button) findViewById(R.id.btn_1);

        mTextview = (TextView) findViewById(R.id.tv_view);
        mWebView = (WebView) findViewById(R.id.wv_view);

        mContext = getApplicationContext();
    }


    /**
     * 轮播 2
     */
    /*private AdvertisingWheelView advertisingWheelView;
    private int pictures[]={R.mipmap.img_1,R.mipmap.img_2,R.mipmap.img_3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_wheel);
        advertisingWheelView= (AdvertisingWheelView) findViewById(R.id.advertisingWheelView);
        advertisingWheelView.setData(pictures);
    }*/
    /**
     * 轮播 1
     */
    /*private final int NEXT=0;
    private final int STOP=1;

    private ViewPager viewPager;
    private LinearLayout indicates;
    private String urls[]={"http://img5.imgtn.bdimg.com/it/u=4055211445,2432360678&fm=21&gp=0.jpg","http://imgsrc.baidu.com/forum/pic/item/b497eeefce1b9d16b36f99ecf3deb48f8d546460.jpg"
            ,"http://p4.gexing.com/shaitu/20120922/1520/505d66aac9e7b.jpg"};
    private int pictures[]={R.mipmap.img_1,R.mipmap.img_2,R.mipmap.img_3};
    private int currentPosition=0;
    private int positionReal=Integer.MAX_VALUE/2;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case NEXT:
                    viewPager.setCurrentItem(++positionReal);
                    //handler.sendEmptyMessageDelayed(NEXT, 3000);
                    break;
                case STOP:
                    handler.removeCallbacksAndMessages(null);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_wheel);
        init();
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        indicates= (LinearLayout) findViewById(R.id.indicates);
        viewPager.setAdapter(new PictureWhell());
        indicates.removeAllViews();
        for(int i=0;i<pictures.length;i++){
            ImageView view=new ImageView(this);
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(60,20);
            view.setLayoutParams(params);
            view.setImageResource(R.drawable.selector_indicates);
            view.setPadding(20,0,20,0);
            if(i==0)
                view.setSelected(true);
            indicates.addView(view);
        }

        viewPager.setCurrentItem(positionReal);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                positionReal = position;
            }

            @Override
            public void onPageSelected(int position) {
                int  selectedPosition=0;
                if(position-Integer.MAX_VALUE/2>=0){
                    selectedPosition=(position-Integer.MAX_VALUE/2)%pictures.length;
                }else if((Integer.MAX_VALUE/2-position)%pictures.length==0){
                    selectedPosition=0;
                }else {
                    selectedPosition=pictures.length-(Integer.MAX_VALUE/2-position)%pictures.length;
                }
                if(indicates.getChildCount()>0){
                    for (int i=0;i<indicates.getChildCount();i++){
                        if(i==selectedPosition){
                            indicates.getChildAt(i).setSelected(true);
                        }else {
                            indicates.getChildAt(i).setSelected(false);
                        }

                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_DRAGGING:

                        handler.sendEmptyMessage(STOP);
                        break;
                    *//*case ViewPager.SCROLL_STATE_SETTLING:
                        handler.sendEmptyMessageDelayed(NEXT, 3000);
                        break;*//*
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(NEXT, 3000);
                        break;
                }
            }
        });

        handler.sendEmptyMessageDelayed(NEXT,3000);
    }
    private void init(){

    }
    class  PictureWhell extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=new ImageView(MainActivity.this);
            if(position-Integer.MAX_VALUE/2>=0){
                currentPosition=(position-Integer.MAX_VALUE/2)%pictures.length;
            }else if((Integer.MAX_VALUE/2-position)%pictures.length==0){
                currentPosition=0;
            }else {
                currentPosition=pictures.length-(Integer.MAX_VALUE/2-position)%pictures.length;
            }
            imageView.setBackgroundResource(pictures[currentPosition]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }*/
    /**
     *  V4 包得下拉刷新
     */
    /*private SwipeRefreshLayout mSwipeLayout;
    private ListView mListView;
    private ArrayList<String> list = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swif_refreshlayout_demo);

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                getData()));

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private ArrayList<String> getData() {
        list.add("Hello");
        list.add("This is stormzhang");
        list.add("An Android Developer");
        list.add("Love Open Source");
        list.add("My GitHub: stormzhang");
        list.add("weibo: googdev");
        return list;
    }

    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        }, 5000);
    }*/
    /**
     * 动画滚动展示中奖纪录
     */
    /*private TextWheelView2 textWheelView;
    private List<GiftRecord> mGiftRecords=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_wheel);
        textWheelView= (TextWheelView2) findViewById(R.id.text_wheel_view);
        for(int i=0;i<5;i++){
            GiftRecord giftRecord=new GiftRecord();
            giftRecord.setTelNumber("185****"+(i*100+i*10+i));
            giftRecord.setGiftName("摇到10元便捷卡*"+i);
            giftRecord.setTime(i+"分钟前");
            mGiftRecords.add(giftRecord);
        }
        //textWheelView.setData(mGiftRecords,4);
        textWheelView.setAdapter(new WheelViewAdapter(this,mGiftRecords),4);
    }*/
    /**
     * 开关按钮
     */
    /*private ToggleButton mTogBtn;
    private CheckSwitchButton mCheckSwithcButton;
    private CheckSwitchButton mEnableCheckSwithcButton;
    private SlideSwitchView mSlideSwitchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn); // »ñÈ¡µ½¿Ø¼þ
        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //Ñ¡ÖÐ
                }else{
                    //Î´Ñ¡ÖÐ
                }
            }
        });// Ìí¼Ó¼àÌýÊÂ¼þ
        mCheckSwithcButton = (CheckSwitchButton)findViewById(R.id.mCheckSwithcButton);
        mEnableCheckSwithcButton = (CheckSwitchButton)findViewById(R.id.mEnableCheckSwithcButton);
        mCheckSwithcButton.setChecked(false);
        mCheckSwithcButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    mEnableCheckSwithcButton.setEnabled(false);
                    mSlideSwitchView.setEnabled(false);
                }else{
                    mEnableCheckSwithcButton.setEnabled(true);
                    mSlideSwitchView.setEnabled(true);
                }
            }
        });
        mSlideSwitchView = (SlideSwitchView) findViewById(R.id.mSlideSwitchView);
        mSlideSwitchView.setOnChangeListener(new SlideSwitchView.OnSwitchChangedListener() {

            @Override
            public void onSwitchChange(SlideSwitchView switchView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {

                }
            }
        });
    }*/



}
