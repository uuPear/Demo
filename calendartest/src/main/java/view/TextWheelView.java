package view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.umall.ydz.calendartest.R;

import java.util.List;

import been.GiftRecord;
import utils.DensityUtil;


/**
 * Created by GuiYang on 15/12/22.
 */
public class TextWheelView extends LinearLayout {
    private Context mContext;
    private LayoutInflater mInflater;
    private int mVisibleCount=-1;
    private Scroller mScroller;
    private List<GiftRecord> mGiftRecoreds;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            View view=null;
            if(mVisibleCount<=0){
                throw new IllegalArgumentException("visibleCount must more than the 0");
            }
            //先删掉从第二个开始删  在开始滑动
            if(getChildCount()>(mVisibleCount+2)){
                view=getChildAt(0);
                removeViewAt(0);
                smoothScrollByRemoveAfter(0, DensityUtil.dip2px(mContext, 60));
            }else{
                smoothScrollBy(0, DensityUtil.dip2px(mContext, 60));
            }

            addView(getItem(view,mGiftRecoreds.get((++postion) % mGiftRecoreds.size())));
            handler.sendEmptyMessageDelayed(1,1000);
        }
    };
    public TextWheelView(Context context) {
        super(context);
        initView(context);
    }

    public TextWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        mContext=context;
        mInflater=LayoutInflater.from(context);
        mScroller=new Scroller(mContext);
    }
    private int postion=-1;
    public void setData(List<GiftRecord> giftRecords ,int visibleCount){
        mGiftRecoreds=giftRecords;
        mVisibleCount=visibleCount;
        for(int i=0;i<visibleCount;i++){
            postion=i;
            addView(getItem(null,giftRecords.get(i)));
        }
        //添加一条用来上移
        addView(getItem(null,giftRecords.get(++postion)));
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    private View getItem(View convertview,GiftRecord giftRecord){
        ViewHolder holder;
        if(convertview!=null){
            holder= (ViewHolder) convertview.getTag();
        }else{
            holder=new ViewHolder();
            convertview=mInflater.inflate(R.layout.item_gift_record,null);
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(mContext, 60));
            convertview.setLayoutParams(params);
            holder.tv_tel_number= (TextView) convertview.findViewById(R.id.tv_tel_number);
            holder.tv_gift_name= (TextView) convertview.findViewById(R.id.tv_gift_name);
            holder.tv_time= (TextView) convertview.findViewById(R.id.tv_time);
            convertview.setTag(holder);
        }
        holder.tv_tel_number.setText(giftRecord.getTelNumber());
        holder.tv_gift_name.setText(giftRecord.getGiftName());
        holder.tv_time.setText(giftRecord.getTime());
        return convertview;
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
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY() - DensityUtil.dip2px(mContext,60), dx, dy,500);
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

    static class ViewHolder{
        TextView tv_tel_number;
        TextView tv_gift_name;
        TextView tv_time;
    }
}
