package com.fastaoe.gankio.model.services;


import com.fastaoe.gankio.model.beans.Content;
import com.fastaoe.gankio.model.beans.History;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by jinjin on 17/11/2.
 * description:
 */

public interface BlogService extends IService {

    @GET("day/history")
    Observable<History> getBlogHistory();

    @GET("history/content/day/2016/05/11")
    Observable<Content> getContentForDay();
}
