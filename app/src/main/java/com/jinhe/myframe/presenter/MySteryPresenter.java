package com.jinhe.myframe.presenter;

import com.jinhe.myframe.Beans.MysteryBean;
import com.jinhe.myframe.base.BaseListPresenter;
import com.jinhe.myframe.base.BaseListView;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by LC on 2018/5/31.
 */
public class MySteryPresenter extends BaseListPresenter<MysteryBean.DataBean>{

    public MySteryPresenter(BaseListView listView) {
        super(listView);
    }

    @Override
    protected void onAllSuccess(Object o) {
        MysteryBean mysteryBean = (MysteryBean) o;
        if(mysteryBean.rel){
            if(mode == RequestMode.REFRESH){
                if(mysteryBean.getData()==null||mysteryBean.getData().size()==0){
                    listView.showEmptyData();
                    return;
                }
                listView.showFinishDate(mysteryBean.getData());
            }else {
                listView.showLoadMoreData(mysteryBean.getData());
            }
        }else {
            listView.showError();
        }
    }

    @Override
    protected Observable getObservable(Map<String, String> params) {
        return internetService.getMySteryList(params);
    }
}
