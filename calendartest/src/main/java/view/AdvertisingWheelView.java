package view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.umall.ydz.calendartest.R;

/**
 * Created by GuiYang on 16/1/7.
 */
public class AdvertisingWheelView extends RelativeLayout {
    /**
     * 轮播
     */
    private final int NEXT=0;
    private final int STOP=1;

    private Context mContext;
    private ViewPager viewPager;
    private LinearLayout indicates;

    private int positionReal=Integer.MAX_VALUE/2;
    private int mData[];

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
    public AdvertisingWheelView(Context context) {
        super(context);
        initView(context);
    }

    public AdvertisingWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AdvertisingWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext=context;
        View view=LayoutInflater.from(context).inflate(R.layout.view_adverting_wheel,this);
        viewPager= (ViewPager) view.findViewById(R.id.viewpager);
        indicates= (LinearLayout) view.findViewById(R.id.indicates);
    }

    public  void setData(int data[]){
        if(data==null||data.length==0){
            return;
        }
        mData=data;
        viewPager.setAdapter(new PictureWhell());
        initIndicates();
        viewPager.setCurrentItem(positionReal);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                positionReal = position;
            }

            @Override
            public void onPageSelected(int position) {
                int selectedPosition = 0;
                if (position - Integer.MAX_VALUE / 2 >= 0) {
                    selectedPosition = (position - Integer.MAX_VALUE / 2) % mData.length;
                } else if ((Integer.MAX_VALUE / 2 - position) % mData.length == 0) {
                    selectedPosition = 0;
                } else {
                    selectedPosition = mData.length - (Integer.MAX_VALUE / 2 - position) % mData.length;
                }
                if (indicates.getChildCount() > 0) {
                    for (int i = 0; i < indicates.getChildCount(); i++) {
                        if (i == selectedPosition) {
                            indicates.getChildAt(i).setSelected(true);
                        } else {
                            indicates.getChildAt(i).setSelected(false);
                        }

                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:

                        handler.sendEmptyMessage(STOP);
                        break;
                    /*case ViewPager.SCROLL_STATE_SETTLING:
                        handler.sendEmptyMessageDelayed(NEXT, 3000);
                        break;*/
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(NEXT, 3000);
                        break;
                }
            }
        });

        handler.sendEmptyMessageDelayed(NEXT, 3000);
    }

    private void initIndicates(){
        indicates.removeAllViews();
        for(int i=0;i<mData.length;i++){
            ImageView view=new ImageView(mContext);
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(60,20);
            view.setLayoutParams(params);
            view.setImageResource(R.drawable.selector_indicates);
            view.setPadding(20,0,20,0);
            if(i==0)
                view.setSelected(true);
            indicates.addView(view);
        }
    }
    @Override
    protected void onDetachedFromWindow() {
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        super.onDetachedFromWindow();
    }


    class  PictureWhell extends PagerAdapter {

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
            int currentPosition=0;
            ImageView imageView=new ImageView(mContext);
            if(position-Integer.MAX_VALUE/2>=0){
                currentPosition=(position-Integer.MAX_VALUE/2)%mData.length;
            }else if((Integer.MAX_VALUE/2-position)%mData.length==0){
                currentPosition=0;
            }else {
                currentPosition=mData.length-(Integer.MAX_VALUE/2-position)%mData.length;
            }
            imageView.setBackgroundResource(mData[currentPosition]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
