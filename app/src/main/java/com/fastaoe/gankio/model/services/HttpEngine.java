package com.fastaoe.gankio.model.services;

import com.fastaoe.gankio.base.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jinjin on 17/11/6.
 * description:
 */

public class HttpEngine {

    public static GankService getBlogService() {
        return RestServiceHolder.BLOG_SERVICE;
    }

    private static final class RetrofitHolder {
        private static final String BASE_URL = Constants.getEndPoint();

        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .addInterceptor(
                        new HttpLoggingInterceptor()
                                .setLevel(Constants.DEBUG
                                        ? HttpLoggingInterceptor.Level.BODY
                                        : HttpLoggingInterceptor.Level.NONE))
                .build();

        private static final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OK_HTTP_CLIENT)
                .build();
    }

    private static final class RestServiceHolder {
        private static final GankService BLOG_SERVICE = RetrofitHolder.RETROFIT.create(GankService.class);
    }
}
