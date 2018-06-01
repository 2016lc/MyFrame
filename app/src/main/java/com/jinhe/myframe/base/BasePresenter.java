package com.jinhe.myframe.base;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jinhe.myframe.base.BaseView;
import com.jinhe.myframe.http.RetrofitServiceManager;
import com.jinhe.myframe.service.InternetService;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LC on 2018/5/28.
 */
public abstract class BasePresenter<T extends BaseView>{

    private WeakReference<T> mViewRef = null;
    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;

    public InternetService internetService = RetrofitServiceManager.getInstance().create(InternetService.class);

    public Disposable mSubscription;

    public HashMap<String, String> mParams = new HashMap<>();



    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }


    public void detachView() {
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef=null;
        }
        unDisposable();
    }


    @SuppressLint("CheckResult")
    @SuppressWarnings("unchecked")
    public void requestData(HashMap<String, String> params) {
        mSubscription = getObservable(params)//接口
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDisposable(disposable);
                    }
                })
               /* .map(new Function<BaseBean, Object>() {
                    @Override
                    public Object apply(BaseBean baseBean) throws Exception {
                        //转化数据
                        return o;
                    }
                })*/
                //获得的数据返回主线程去更新界面
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //请求成功
                        onAllSuccess(o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //打印出错误信息
                        onFailed(throwable);
                    }
                });
    }

    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    public void clearHashmap(){
        if (mParams.size() > 0 && mParams != null) {
            mParams.clear();
        }
    }

    protected abstract void onAllSuccess(Object o);

    protected abstract void onFailed(Throwable throwable);

    protected abstract Observable getObservable(Map<String, String> params);

}
