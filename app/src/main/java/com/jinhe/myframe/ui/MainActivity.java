package com.jinhe.myframe.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jinhe.myframe.R;
import com.jinhe.myframe.base.FragmentPagerAdapter;
import com.jinhe.myframe.ui.mainfragment.FourFragment;
import com.jinhe.myframe.ui.mainfragment.OneFragment;
import com.jinhe.myframe.ui.mainfragment.ThreeFragment;
import com.jinhe.myframe.ui.mainfragment.TwoFragment;
import com.jinhe.myframe.utils.BottomBarUtil;
import com.jinhe.myframe.widget.NoScrollView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationBar bottomNavigationBar;
    private NoScrollView mMainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initAdapter();
    }

    private void initAdapter() {
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter.Holder(getSupportFragmentManager()).add(new OneFragment()).add(new TwoFragment()).add(new ThreeFragment()).add(new FourFragment()).build(null);
        mMainViewPager.setAdapter(mAdapter);
    }

    private void initView() {
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        mMainViewPager = findViewById(R.id.main_viewPager);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC );
       // bottomNavigationBar.setPadding(0,10,0,0);
        //bottomNavigationBar.setScrollBarSize();
        //bottomNavigationBar.setBarBackgroundColor(R.color.black); //换成你的背景色即可
        /*
        *  .setActiveColor("#FF107FFD") //选中颜色
                .setInActiveColor("#e9e6e6") //未选中颜色
                .setBarBackgroundColor("#1ccbae");//导航栏背景色
        *
        * */
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_favorites,"喜欢")).setActiveColor(R.color.colorAccent)
                .addItem(new BottomNavigationItem(R.mipmap.ic_friends,"朋友")).setActiveColor(R.color.colorAccent)
                .addItem(new BottomNavigationItem(R.mipmap.ic_nearby,"附近")).setActiveColor(R.color.colorAccent)
                .addItem(new BottomNavigationItem(R.mipmap.ic_recents,"时间")).setActiveColor(R.color.colorAccent)
                .setFirstSelectedPosition(0)
                .initialise();
        BottomBarUtil.BottomBarSet(this,bottomNavigationBar, 6, 26, 10);
    }

    private void initListener() {
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
               // Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
                mMainViewPager.setCurrentItem(position,false);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }
}
