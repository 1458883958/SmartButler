package com.example.wudelin.smartbutler.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.entity.SelectData;
import com.example.wudelin.smartbutler.ui.WebViewActivity;
import com.example.wudelin.smartbutler.utils.Logger;
import com.example.wudelin.smartbutler.utils.PicassoUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.adapter
 * 创建者：   wdl
 * 创建时间： 2018/3/27 19:39
 * 描述：    微信精选适配器
 */

public class SelectAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater inflater;
    private List<SelectData> mList;
    private  WindowManager wm;
    private int width;
    public SelectAdapter(Context mContext, List<SelectData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();

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
        //缓存
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.select_item,null);
            viewHolder.imageView = convertView.findViewById(R.id.iv_image);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.tvSource = convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //赋值
        SelectData data = mList.get(position);
        final String title = data.getTitle();
        final String url = data.getNewsUrl();
        viewHolder.tvTitle.setText(title);
        viewHolder.tvSource.setText(data.getSource());
        //Logger.d("width:",""+screenWidth);
        PicassoUtil.loadDIY(mContext,data.getImageUrl(),
                R.mipmap.ic_launcher,R.mipmap.error,
                width/3,300,
                viewHolder.imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.TITLE,title);
                bundle.putString(WebViewActivity.URL,url);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    class ViewHolder{
        private ImageView imageView;
        private TextView tvTitle;
        private TextView tvSource;
    }
}
