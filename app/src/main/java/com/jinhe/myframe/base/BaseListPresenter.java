package com.jinhe.myframe.base;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by LC on 2018/5/31.
 */
public abstract class BaseListPresenter<T> extends BasePresenter<BaseListView<T>>{

    public BaseListView listView;
    public RequestMode mode = RequestMode.REFRESH;

    public enum RequestMode {
        LOAD_MORE, REFRESH
    }

    public BaseListPresenter(BaseListView listView) {
        this.listView = listView;
    }

    public void getData(RequestMode mode, HashMap<String,String> map){
        this.mode=mode;
        requestData(map);
    }

    @Override
    protected abstract void onAllSuccess(Object o);

    @Override
    protected void onFailed(Throwable throwable) {

    }

    @Override
    protected abstract Observable getObservable(Map<String, String> params);
}
