package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.umall.ydz.calendartest.R;

import java.util.List;

import been.GiftRecord;
import utils.DensityUtil;

/**
 * Created by GuiYang on 15/12/23.
 */
public class WheelViewAdapter extends BaseAdapter {
    private final int itemHeight=60;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<GiftRecord> mGiftRecords;
    public WheelViewAdapter(Context context,List<GiftRecord> giftRecords){
        mContext=context;
        mInflater=LayoutInflater.from(mContext);
        mGiftRecords=giftRecords;
    }
    @Override
    public int getCount() {
        return mGiftRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return mGiftRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView!=null){
            holder= (ViewHolder) convertView.getTag();
        }else{
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_gift_record,null);
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, itemHeight));
            convertView.setLayoutParams(params);
            holder.tv_tel_number= (TextView) convertView.findViewById(R.id.tv_tel_number);
            holder.tv_gift_name= (TextView) convertView.findViewById(R.id.tv_gift_name);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }
        GiftRecord giftRecord=mGiftRecords.get(position);
        holder.tv_tel_number.setText(giftRecord.getTelNumber());
        holder.tv_gift_name.setText(giftRecord.getGiftName());
        holder.tv_time.setText(giftRecord.getTime());
        return convertView;
    }
    static class ViewHolder{
        TextView tv_tel_number;
        TextView tv_gift_name;
        TextView tv_time;
    }
}
