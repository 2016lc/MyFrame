package com.jinhe.myframe.view;

import com.jinhe.myframe.base.BaseView;

import java.util.List;

/**
 * Created by LC on 2018/5/30.
 */
public interface ListView<K> extends BaseView{

    void showFinishDate(List<K> o);

    void showLoadMoreData(List<K> o);

    void showEmptyData();

    void showError();

}
