package com.jinhe.myframe.ui.mainfragment;

import com.jinhe.myframe.R;
import com.jinhe.myframe.base.BaseFragment;
import com.jinhe.myframe.base.BasePresenter;

/**
 * Created by LC on 2018/6/5.
 */
public class OneFragment extends BaseFragment{
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fagment_one;
    }

    @Override
    public void setSuccessData(Object o) {

    }

    @Override
    public void setFailedData(String msg) {

    }
}
