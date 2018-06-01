package com.jinhe.myframe.presenter;

import com.jinhe.myframe.Beans.MyBean;
import com.jinhe.myframe.view.NormalView;
import com.jinhe.myframe.base.BasePresenter;
import com.jinhe.myframe.constant.ErrorMsg;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by LC on 2018/5/28.
 */
public class NormalPresenter extends BasePresenter<NormalView> {


    private NormalView normalView;

    public NormalPresenter(NormalView normalView)
    {
        this.normalView = normalView;
    }

    public void getData(){
       // baseModel.
        clearHashmap();
        mParams.put("username",normalView.mUsername());
        mParams.put("password",normalView.mPassword());
        mParams.put("registration_id","deviceToken");
        mParams.put("char","a82133e068c9c6a8a54fe8a2e971057a");
        mParams.put("registration_type","1");
        requestData(mParams);
    }


    @Override
    protected void onAllSuccess(Object o) {
        MyBean myBean = (MyBean) o;
        if(myBean.rel){
            normalView.setSuccessData(o);
        }else {
            normalView.setFailedData(myBean.getMsg());
        }
    }

    @Override
    protected void onFailed(Throwable throwable) {
        normalView.setFailedData(throwable + ErrorMsg.msg);
    }

    @Override
    protected Observable getObservable(Map<String, String> params) {
        return internetService.getLoginList(params);
    }
}
