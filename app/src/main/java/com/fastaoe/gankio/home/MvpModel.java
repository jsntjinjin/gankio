package com.fastaoe.gankio.home;

import com.fastaoe.gankio.home.model.Content;
import com.fastaoe.gankio.home.model.History;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/1.
 * description: 模拟网络请求
 */

public class MvpModel {

    public static void getNetData(final MvpCallback<String> callback) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        final BlogService blogService = retrofit.create(BlogService.class);

        blogService.getBlogHistory()
                .flatMap(new Func1<History, Observable<Content>>() {
                    @Override
                    public Observable<Content> call(History history) {
                        return blogService.getContentForDay();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Content>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Content content) {
                        callback.onSuccess(content.results.get(0)._id);
                    }
                });
    }

}
