package com.fastaoe.gankio.home;

import com.fastaoe.gankio.home.model.Content;
import com.fastaoe.gankio.home.model.History;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by jinjin on 17/11/2.
 * description:
 */

public interface BlogService {

    @GET("day/history")
    Observable<History> getBlogHistory();

    @GET("history/content/day/2016/05/11")
    Observable<Content> getContentForDay();
}
