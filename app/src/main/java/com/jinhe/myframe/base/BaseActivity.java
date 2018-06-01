package com.jinhe.myframe.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.jinhe.myframe.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by LC on 2018/5/26.
 */
public abstract class BaseActivity<V extends BaseView,Z extends BasePresenter<V>> extends Activity implements BaseView{

    public Z mPresenter;
    private View mContextView;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContextView = LayoutInflater.from(this).inflate(bindLayout(),null);
        setContentView(mContextView);
        initView();
        initListener();
    }


    public void initView() {
        if(mPresenter==null){
            mPresenter = initPresenter();
        }
        mPresenter.attachView((V) this);
    }

    public void initListener() {

    }

    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }

    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> targetActivityClass, String targetName, String targetMessage, String targetName1, String targetMessage1) {
        Intent intent = new Intent(this, targetActivityClass);
        if (targetMessage != null) {
            intent.putExtra(targetName, targetMessage);
            intent.putExtra(targetName1, targetMessage1);
        }
        startActivity(intent);
    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingDialog(String msg) {
        if(pDialog==null){
            pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
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
            pDialog = new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText(msg)
                    .setConfirmText("确定")
                    .show();
        }
        pDialog.setConfirmButton("确定", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dismissLoadingDialog();
            }
        });
    }

    @Override
    public void showWarningDialog(String title,String content) {
        if(pDialog!=null){
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
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
    protected void onDestroy() {
        if(mPresenter!=null){
            mPresenter.detachView();
            mPresenter=null;
        }
        super.onDestroy();
    }
}
