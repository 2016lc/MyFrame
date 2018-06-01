package com.jinhe.myframe.base;

import java.util.HashMap;
import java.util.List;


/**
 * Created by LC on 2018/5/31.
 */
public interface BaseListView<K> extends BaseView{

    void showFinishDate(List<K> o);

    void showLoadMoreData(List<K> o);

    void showEmptyData();

    void showError();

}
