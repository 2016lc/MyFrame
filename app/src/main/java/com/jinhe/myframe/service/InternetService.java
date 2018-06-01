package com.jinhe.myframe.service;

import com.jinhe.myframe.Beans.MyBean;
import com.jinhe.myframe.Beans.MysteryBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by LC on 2018/5/26.
 */
public interface InternetService {

    @FormUrlEncoded
    @POST("/api/v1.Login")
    Observable<MyBean> getLoginList(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("/api/v1.Theory")
    Observable<MysteryBean> getMySteryList(@FieldMap Map<String, String> params);
}
