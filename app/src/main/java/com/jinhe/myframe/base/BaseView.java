package com.jinhe.myframe.base;

/**
 * Created by LC on 2018/5/28.
 */
public interface BaseView {

    void showLoadingDialog(String msg);

    void showErrorDialog(String msg);

    void showWarningDialog(String title,String content);

    void showSuccessDialog(String msg);

    void dismissLoadingDialog();

    void setSuccessData(Object o);

    void setFailedData(String msg);

}
