package view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Scroller;

import utils.DensityUtil;


/**
 * Created by GuiYang on 15/12/22.
 */
public class TextWheelView2 extends LinearLayout {
    private final int itemHeight=60;
    private Context mContext;
    private BaseAdapter mAdapter;
    private int mVisibleCount=-1;
    private Scroller mScroller;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            View view=null;
            if(mVisibleCount<=0){
                throw new IllegalArgumentException("visibleCount must more than the 0 at TextWheelView.setAdapter(BaseAdapter adapter ,int visibleCount)");
            }
            //先删掉从第二个开始删  在开始滑动
            if(getChildCount()>(mVisibleCount+2)){
                view=getChildAt(0);
                removeViewAt(0);
                smoothScrollByRemoveAfter(0, DensityUtil.dip2px(mContext, itemHeight));
            }else{
                smoothScrollBy(0, DensityUtil.dip2px(mContext, itemHeight));
            }

            addView(mAdapter.getView((++postion%mAdapter.getCount()),view,TextWheelView2.this));
            handler.sendEmptyMessageDelayed(1,1000);
        }
    };
    public TextWheelView2(Context context) {
        super(context);
        initView(context);
    }

    public TextWheelView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        mContext=context;
        mScroller=new Scroller(mContext);
    }
    private int postion=-1;
    public void setAdapter(BaseAdapter adapter ,int visibleCount){
        mAdapter=adapter;
        mVisibleCount=visibleCount;
        if(mAdapter.getCount()<=mVisibleCount){
            for(int i=0;i<mAdapter.getCount();i++){
                postion=i;
                addView(mAdapter.getView(postion,null,this));
            }
        }else{
            for(int i=0;i<visibleCount;i++){
                postion=i;
                addView(mAdapter.getView(postion,null,this));
            }
            //添加一条用来上移
            addView(mAdapter.getView(++postion,null,this));
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //handler.getLooper().quit();
        handler.removeCallbacksAndMessages(null);
        handler=null;
    }

    //调用此方法滚动到目标位置
    public   void  smoothScrollTo( int  fx,  int  fy) {
        int  dx = fx - mScroller.getFinalX();
        int  dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public   void  smoothScrollBy( int  dx,  int  dy) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, 500);
    }
    //调用此方法设置滚动的相对偏移
    public   void  smoothScrollByRemoveAfter( int  dx,  int  dy) {
        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY() - DensityUtil.dip2px(mContext,itemHeight), dx, dy,500);
    }
    @Override
    public void computeScroll() {
        if  (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
    }

}
