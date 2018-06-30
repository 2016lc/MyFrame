package com.jinhe.myframe.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jinhe.myframe.view.NormalView;
import com.jinhe.myframe.R;
import com.jinhe.myframe.base.BaseActivity;
import com.jinhe.myframe.presenter.NormalPresenter;


/**
 * Created by LC on 2018/5/28.
 */
public class NormalActivity extends BaseActivity<NormalView,NormalPresenter> implements NormalView{


    private EditText mEtUsername,mEtPassword;
    private Button mBtnLogin;

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_normal;
    }

    @Override
    public void initListener() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog("登录中...");
                mPresenter.getData();
                //showWarningDialog("","");
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        mEtUsername = findViewById(R.id.et_username);
        mEtPassword = findViewById(R.id.et_password);
        mBtnLogin = findViewById(R.id.btn_login);
    }

    @Override
    public String mUsername() {
        return "15084438964";
    }

    @Override
    public String mPassword() {
        return "123456";
    }

    @Override
    public void setSuccessData(Object o) {
        dismissLoadingDialog();
       openActivity(ListActivity.class);
       // openActivity(MainActivity.class);
    }

    @Override
    public void setFailedData(String msg) {
       // dismissLoadingDialog();
        showErrorDialog(msg);
    }
}
