package com.jinhe.myframe.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View mContentView;

    private List<T> mData;

    private int mLayoutResId;

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private OnRecyclerViewItemChildClickListener onRecyclerViewItemClickListener;

    public BaseAdapter(int layoutResId, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public BaseAdapter(View contentView, List<T> data) {
        this(0, data);
        mContentView = contentView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = null;
        int headerCount = 0;
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        baseViewHolder = onCreateDefViewHolder(parent, viewType);

        /*if (parent instanceof RecyclerView) {
            headerCount = ((RecyclerView) parent).getHeaderViewCount();
        }*/
        initItemClickListener(baseViewHolder);
        return baseViewHolder;
    }


    private BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mLayoutResId);
    }


    private void initItemClickListener(final BaseViewHolder baseViewHolder) {
        if (onRecyclerViewItemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onItemChildClick(BaseAdapter.this, v,
                            baseViewHolder.getLayoutPosition());
                }
            });
        }
    }

    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        if (mContentView == null) {
            return new BaseViewHolder(getItemView(layoutResId, parent));
        }
        return new BaseViewHolder(mContentView);
    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        convert((BaseViewHolder) holder,
                mData.get(position), position, mData.size());
    }

    protected abstract void convert(BaseViewHolder holder, T t, int position, int size);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setNewData(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addDataAndNotify(List<T> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public List getData() {
        return mData;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void setOnRecyclerViewItemChildClickListener(OnRecyclerViewItemChildClickListener childClickListener) {
        this.onRecyclerViewItemClickListener = childClickListener;
    }

    public interface OnRecyclerViewItemChildClickListener {

        void onItemChildClick(BaseAdapter adapter, View view, int position);

    }
}
