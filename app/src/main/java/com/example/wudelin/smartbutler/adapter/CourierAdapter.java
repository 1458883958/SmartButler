package com.example.wudelin.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.entity.CourierData;

import java.util.List;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.adapter
 * 创建者：   wdl
 * 创建时间： 2018/3/26 16:43
 * 描述：    快递信息适配器
 */

public class CourierAdapter extends BaseAdapter{
    private Context mContext;
    private List<CourierData> mList;
    private LayoutInflater inflater;
    private CourierData courierData;
    public CourierAdapter(Context mContext,List<CourierData> mList){
        this.mContext = mContext;
        this.mList = mList;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //判断是否是第一次（是否缓存）
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item,null);
            viewHolder.tv_datetime = convertView.findViewById(R.id.tv_datetime);
            viewHolder.tv_remask = convertView.findViewById(R.id.tv_remask);
            //设置缓存
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        courierData = mList.get(position);
        viewHolder.tv_remask.setText(courierData.getRemark());
        viewHolder.tv_datetime.setText(courierData.getDatetime());
        return convertView;
    }
    class ViewHolder{
        private TextView tv_datetime;
        private TextView tv_remask;

    }
}
