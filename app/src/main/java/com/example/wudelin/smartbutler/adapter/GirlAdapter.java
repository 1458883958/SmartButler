package com.example.wudelin.smartbutler.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.entity.GirlData;
import com.example.wudelin.smartbutler.utils.PicassoUtil;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.List;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.adapter
 * 创建者：   wdl
 * 创建时间： 2018/3/28 16:52
 * 描述：    美女图片适配器
 */

public class GirlAdapter extends BaseAdapter{

    private Context mContext;
    private List<GirlData> mList;
    private LayoutInflater inflater;
    private WindowManager wm;
    private int width;
    public GirlAdapter(Context mContext, List<GirlData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.grid_item,null);
            viewHolder.imageView = convertView.findViewById(R.id.iv_grid);
            //viewHolder.photoView = convertView.findViewById(R.id.photo_view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //赋值
        GirlData data = mList.get(position);
        String url = data.getImageUrl();
        PicassoUtil.loadDIY(mContext,url,R.mipmap.ic_launcher,R.mipmap.error
                ,width/2,400,viewHolder.imageView);
        return convertView;
    }

    class ViewHolder{
        private ImageView imageView;
        //private ImageView photoView;
    }
}
