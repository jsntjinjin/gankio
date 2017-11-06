package com.fastaoe.gankio.model.models;

import com.fastaoe.gankio.model.beans.Content;
import com.fastaoe.gankio.model.beans.History;
import com.fastaoe.gankio.model.callback.Callback;
import com.fastaoe.gankio.model.services.BlogService;
import com.fastaoe.gankio.model.services.HttpEngine;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/6.
 * description:
 */

public class Blog extends BaseModel<Content> {

    @Override
    public void execute(final Callback<Content> callback) {

        HttpEngine.getBlogService().getBlogHistory()
                .flatMap(new Func1<History, Observable<Content>>() {
                    @Override
                    public Observable<Content> call(History history) {
                        return HttpEngine.getBlogService().getContentForDay();
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
                        callback.onSuccess(content);
                    }
                });
    }
}
