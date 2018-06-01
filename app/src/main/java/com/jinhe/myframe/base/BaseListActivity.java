package com.jinhe.myframe.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jinhe.myframe.R;
import com.jinhe.myframe.http.RetrofitServiceManager;
import com.jinhe.myframe.service.InternetService;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by LC on 2018/5/31.
 */
public abstract class BaseListActivity<T,K extends BaseListPresenter<T>> extends BaseActivity<BaseListView<T>,K> implements BaseListView<T>{


    private RefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private BaseAdapter<T> mAdapter;
    public int PAGE = 1;
    public HashMap<String,String> map = new HashMap<>();


    @Override
    public int bindLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void initView() {
        super.initView();
        refreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        mRecyclerView.setLayoutManager(layoutmanager);
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                PAGE=1;
                mPresenter.getData(BaseListPresenter.RequestMode.REFRESH,getMap());
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshLayout) {
                PAGE++;
                mPresenter.getData(BaseListPresenter.RequestMode.LOAD_MORE,getMap());
            }
        });
        //触发自动刷新
        refreshLayout.autoRefresh();
    }
    public abstract HashMap<String,String> getMap();


    @Override
    public void showFinishDate(List<T> o) {
        refreshLayout.finishRefresh();
        refreshLayout.setNoMoreData(false);
        if (mAdapter == null) {
            mAdapter = new BaseAdapter<T>(getItemLayout(), o) {
                @Override
                protected void convert(BaseViewHolder helper, T item, int position, int size) {
                    fitDates(helper, item,position,size);
                }
            };
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNewData(o);
        }
        mAdapter.setOnRecyclerViewItemChildClickListener(new BaseAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseAdapter adapter, View view, int position) {
                onItemClick(view, position);
            }
        });
    }

    public abstract int getItemLayout();
    public abstract void fitDates(BaseViewHolder helper, T bean,int position,int size);
    public void onItemClick(View v, int position){}

    @Override
    public void showLoadMoreData(List<T> o) {
        if(o.size()>0 && o!=null){
            refreshLayout.finishLoadMore(true);
            mAdapter.addDataAndNotify(o);
        }else {
            //showToast("没有更多数据");
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void showEmptyData() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void setSuccessData(Object o) {

    }

    @Override
    public void setFailedData(String msg) {

    }
}
