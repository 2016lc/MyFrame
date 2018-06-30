package com.jinhe.myframe.base;

import android.app.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by LC on 2018/5/26.
 */
public abstract class BaseFragment<V extends BaseView,Z extends BasePresenter<V>> extends Fragment implements BaseView{
    protected Activity mContext;
    protected View mRootView;
    public Z mPresenter;
    private View mContextView;
    private SweetAlertDialog pDialog;
    protected boolean isInit = false;
    protected boolean isLoad = false;
    private Unbinder unbinder;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = inflater.inflate(bindLayout(), container, false);
            unbinder = ButterKnife.bind(this, mRootView);
            initView();
            initListener();
            isInit = true;
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    public void initView() {
        if(mPresenter==null){
            mPresenter = initPresenter();
        }
        //mPresenter.attachView((V) mContext);
    }

    public void initListener() {

    }

    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                // stopLoad();
            }
        }
    }

    public void lazyLoad(){}

    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }

    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(mContext, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> targetActivityClass, String targetName, String targetMessage, String targetName1, String targetMessage1) {
        Intent intent = new Intent(mContext, targetActivityClass);
        if (targetMessage != null) {
            intent.putExtra(targetName, targetMessage);
            intent.putExtra(targetName1, targetMessage1);
        }
        startActivity(intent);
    }

    public void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog(String msg) {
        if(pDialog==null){
            pDialog = new SweetAlertDialog(mContext,SweetAlertDialog.PROGRESS_TYPE);
            pDialog.showContentText(false);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText(msg);
            pDialog.setCancelable(true);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
            pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if(pDialog!=null){
                        pDialog.dismiss();
                        pDialog=null;
                    }
                }
            });
        }
    }

    @Override
    public void showSuccessDialog(String msg) {

    }

    @Override
    public void showErrorDialog(String msg) {
        if(pDialog!=null){
            pDialog.setTitleText(msg)
                    .setConfirmText("确定")
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }else {
            pDialog = new SweetAlertDialog(mContext,SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText(msg)
                    .setConfirmText("确定")
                    .show();
        }
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dismissLoadingDialog();
            }
        });
    }

    @Override
    public void showWarningDialog(String title,String content) {
        if(pDialog!=null){
            pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);
            pDialog.setTitleText("Are you sure?")
                    .setContentText("Won't be able to recover this file!")
                    .setCancelText("No,cancel plx!")
                    .setConfirmText("Yes,delete it!")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // reuse previous dialog instance, keep widget user state, reset them if you need
                            sDialog.setTitleText("Cancelled!")
                                    .setContentText("Your imaginary file is safe :)")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.setTitleText("Deleted!")
                                    .setContentText("Your imaginary file has been deleted!")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
        }

    }

    @Override
    public void dismissLoadingDialog() {
        if(pDialog!=null){
            pDialog.dismiss();
            pDialog=null;
        }
    }

    public abstract Z initPresenter();
    public abstract int bindLayout();


    @Override
    public void onDestroyView() {
        if(mPresenter!=null){
            mPresenter.detachView();
            mPresenter=null;
        }
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if(mPresenter!=null){
            mPresenter.detachView();
            mPresenter=null;
        }
        unbinder.unbind();
        super.onDestroy();
    }
}
