package com.fastaoe.gankio.model.services;


import com.fastaoe.gankio.model.beans.AllData;
import com.fastaoe.gankio.model.beans.Content;
import com.fastaoe.gankio.model.beans.HistoryForOneDay;
import com.fastaoe.gankio.model.beans.HistoryList;
import com.fastaoe.gankio.model.beans.HistoryForSomeDay;
import com.fastaoe.gankio.model.beans.RandomData;
import com.fastaoe.gankio.model.beans.Search;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jinjin on 17/11/2.
 * description:
 */

public interface BlogService {

    // 获取发过干货日期接口
    @GET("day/history")
    Observable<HistoryList> getHistoryDay();

    // 获取某几日干货网站数据
    @GET("history/content/{count}/{page}")
    Observable<HistoryForSomeDay> getHistoryForSomeDay(@Path("count") String count,
                                                       @Path("page") String page);

    // 获取特定日期网站数据
    @GET("history/content/day/{year}/{month}/{day}")
    Observable<HistoryForOneDay> getHistoryForOneDay(@Path("year") String year,
                                                     @Path("month") String month,
                                                     @Path("day") String day);

    // 所有干货，支持配图数据返回 （除搜索 Api）
    // category -> 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
    @GET("data/{category}/{count}/{page}")
    Observable<AllData> getAllData(@Path("category") String category,
                                   @Path("count") String count,
                                   @Path("page") String page);

    // 搜索
    // category -> all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
    @GET("search/query/listview/category/{category}/count/{count}/page/{page}")
    Observable<Search> getSearch(@Path("category") String category,
                                 @Path("count") String count,
                                 @Path("page") String page);

    // 每日数据： http://gank.io/api/day/年/月/日
    @GET("day/{year}/{month}/{day}")
    Observable<Content> getContent(@Path("year") String year,
                                   @Path("month") String month,
                                   @Path("day") String day);

    // 随机数据：http://gank.io/api/random/data/分类/个数
    // category -> 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
    @GET("random/data/{category}/{count}")
    Observable<RandomData> getRandomData(@Path("category") String category,
                                         @Path("count") String count);
}
