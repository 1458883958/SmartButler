package com.example.wudelin.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wudelin.smartbutler.R;
import com.example.wudelin.smartbutler.entity.ChatData;

import java.util.List;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.adapter
 * 创建者：   wdl
 * 创建时间： 2018/3/27 10:42
 * 描述：    聊天适配器
 */

public class ChatAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater inflater;
    private List<ChatData> mList;
    //type
    public static final int VALUE_LEFT_TYPE = 1;
    public static final int VALUE_RIGHT_TYPE = 2;

    public ChatAdapter(Context mContext, List<ChatData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolderLeft viewHolderLeft = null;
        ViewHolderRight viewHolderRight = null;
        //获取type  加载数据
        int type = getItemViewType(position);
        if(convertView == null){
            switch (type){
                case VALUE_LEFT_TYPE:
                    viewHolderLeft = new ViewHolderLeft();
                    convertView = inflater.inflate(R.layout.left_item,null);
                    viewHolderLeft.textView = convertView.findViewById(R.id.tv_left_text);
                    convertView.setTag(viewHolderLeft);
                    break;
                case VALUE_RIGHT_TYPE:
                    viewHolderRight = new ViewHolderRight();
                    convertView = inflater.inflate(R.layout.right_item,null);
                    viewHolderRight.textView = convertView.findViewById(R.id.tv_right_text);
                    convertView.setTag(viewHolderRight);
                    break;
            }
        }else{
            switch (type){
                case VALUE_LEFT_TYPE:
                    viewHolderLeft  = (ViewHolderLeft) convertView.getTag();
                    break;
                case VALUE_RIGHT_TYPE:
                    viewHolderRight  = (ViewHolderRight) convertView.getTag();
                    break;
            }
        }
        //赋值
        ChatData data = mList.get(position);
        switch (type){
            case VALUE_LEFT_TYPE:
                viewHolderLeft.textView.setText(data.getText());
                break;
            case VALUE_RIGHT_TYPE:
                viewHolderRight.textView.setText(data.getText());
                break;
        }
        return convertView;
    }

    //根据id获取显示的type
    @Override
    public int getItemViewType(int position) {
        ChatData chatData = mList.get(position);
        int type = chatData.getType();
        return type;
    }
    //默认   左边   右边
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    //缓存
    class ViewHolderLeft{
        private TextView textView;
    }
    class ViewHolderRight{
        private TextView textView;
    }
}
