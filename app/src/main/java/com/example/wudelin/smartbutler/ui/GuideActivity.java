package com.example.wudelin.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wudelin.smartbutler.MainActivity;
import com.example.wudelin.smartbutler.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * 项目名：  SmartButler
 * 包名：    com.example.wudelin.smartbutler.ui
 * 创建者：   wdl
 * 创建时间： 2018/3/22 19:06
 * 描述：    引导页
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    //容器
    private List<View> mList = new ArrayList<>();
    //三个界面view
    private View view1,view2,view3;
    //三个小圆点
    private ImageView point1,point2,point3;
    //跳过
    private ImageView ivBack;
    //进入主页
    private Button btnStart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.mViewPager);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);
        //设置默认圆点
        setDefPoint(true,false,false);

        view1 = View.inflate(this,R.layout.viewpage_item_one,null);
        view2 = View.inflate(this,R.layout.viewpage_item_two,null);
        view3 = View.inflate(this,R.layout.viewpage_item_three,null);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        btnStart = view3.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);

        mViewPager.setAdapter(new GuideAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //滑动监听
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:setDefPoint(true,false,false);ivBack.setVisibility(View.VISIBLE);break;
                    case 1:setDefPoint(false,true,false);ivBack.setVisibility(View.VISIBLE);break;
                    case 2:setDefPoint(false,false,true);ivBack.setVisibility(View.GONE);break;
                    default:break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setDefPoint(boolean b, boolean b1, boolean b2) {
        if(b){
            point1.setBackgroundResource(R.drawable.point_on);
        }else{
            point1.setBackgroundResource(R.drawable.point_off);
        }
        if(b1){
            point2.setBackgroundResource(R.drawable.point_on);
        }else{
            point2.setBackgroundResource(R.drawable.point_off);
        }
        if(b2){
            point3.setBackgroundResource(R.drawable.point_on);
        }else{
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
            case R.id.btn_start:
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
                break;
            default:break;
        }
    }

    class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //添加
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }
        //删除
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
            //super.destroyItem(container, position, object);
        }
    }
}
