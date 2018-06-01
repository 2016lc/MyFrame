package com.jinhe.myframe.ui;

import com.jinhe.myframe.Beans.MysteryBean;
import com.jinhe.myframe.R;
import com.jinhe.myframe.base.BaseListActivity;
import com.jinhe.myframe.base.BaseListPresenter;
import com.jinhe.myframe.base.BasePresenter;
import com.jinhe.myframe.base.BaseViewHolder;
import com.jinhe.myframe.presenter.MySteryPresenter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by LC on 2018/5/30.
 */
public class ListActivity extends BaseListActivity<MysteryBean.DataBean,MySteryPresenter>{


    @Override
    public HashMap<String, String> getMap() {
        map.put("page",String.valueOf(PAGE));
        return map;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_list;
    }

    @Override
    public void fitDates(BaseViewHolder helper, MysteryBean.DataBean bean, int position, int size) {
        helper.setText(R.id.tv_title,bean.getTitle());
    }


    @Override
    public MySteryPresenter initPresenter() {
        return new MySteryPresenter(this);
    }
}
