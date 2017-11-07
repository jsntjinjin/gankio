package com.fastaoe.gankio.model.beans;

import java.util.List;

/**
 * Created by jinjin on 17/11/7.
 * description:
 */

public class AllData {

    /**
     * error : false
     * results : [{"_id":"59fa764b421aa90fe72535ea","createdAt":"2017-11-02T09:35:07.929Z","desc":"带你玩转Android Studio 3.0的性能分析工具","publishedAt":"2017-11-06T12:40:39.976Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247487907&idx=1&sn=202a662ccf28a9b00d7daf2067eb22d6","used":true,"who":"陈宇明"},{"_id":"59fec807421aa90fe50c01d9","createdAt":"2017-11-05T16:12:55.594Z","desc":"自定义ViewPager实现3D画廊效果","publishedAt":"2017-11-06T12:40:39.976Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/7590431176c1","used":true,"who":"阿韦"},{"_id":"59ffaffe421aa90fef203501","createdAt":"2017-11-06T08:42:38.889Z","desc":"Dagger2的轻松愉悦解析 。依赖注入框架，一个刚接触时感觉麻烦，用久了就会\u201c嘴上说不要，身体却很诚实\u201d的开发润滑剂。","images":["http://img.gank.io/89aeaed4-00b6-41a5-a6f2-89ca03e8a536"],"publishedAt":"2017-11-06T12:40:39.976Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/9e5d2dbc4ad6","used":true,"who":"Shuyu Guo"},{"_id":"59ffbbe4421aa90fe2f02c28","createdAt":"2017-11-06T09:33:24.183Z","desc":"老程序员总结的16条经验教训","publishedAt":"2017-11-06T12:40:39.976Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247488018&idx=1&sn=0eebb7eb8d783320c2e3f9513b736be8","used":true,"who":"陈宇明"},{"_id":"59ffc9a2421aa90fe2f02c29","createdAt":"2017-11-06T10:32:02.209Z","desc":"这可能是至今为止最详细实用的 Kotlin 协程库详解了。 ","images":["http://img.gank.io/aa40ee86-6934-4e3e-95ec-536422e362be"],"publishedAt":"2017-11-06T12:40:39.976Z","source":"web","type":"Android","url":"https://kymjs.com/code/2017/11/06/01/","used":true,"who":"张涛"},{"_id":"59f8553f421aa90fef2034e9","createdAt":"2017-10-31T18:49:35.980Z","desc":"深度学习js与安卓的交互以及WebView的那些坑","images":["http://img.gank.io/d1d4f7b4-9291-499a-8b20-c3c485c46119"],"publishedAt":"2017-11-01T14:20:59.209Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/b9164500d3fb","used":true,"who":"阿韦"},{"_id":"59f92869421aa90fe50c01c1","createdAt":"2017-11-01T09:50:33.515Z","desc":"Android启动页黑屏及最优解决方案","publishedAt":"2017-11-01T14:20:59.209Z","source":"web","type":"Android","url":"https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247487886&idx=1&sn=6fbe4d971e873ee351aef213eedba0ae","used":true,"who":"陈宇明"},{"_id":"59f92b44421aa90fe50c01c3","createdAt":"2017-11-01T10:02:44.598Z","desc":"可设定阴影颜色的shadow-layout","images":["http://img.gank.io/d3acd780-a1a6-4529-a026-b8bd7967626a"],"publishedAt":"2017-11-01T14:20:59.209Z","source":"chrome","type":"Android","url":"https://github.com/dmytrodanylyk/shadow-layout","used":true,"who":"galois"},{"_id":"59f937f1421aa90fe50c01c4","createdAt":"2017-11-01T10:56:49.711Z","desc":"LeetCode的Java题解(updating)","publishedAt":"2017-11-01T14:20:59.209Z","source":"web","type":"Android","url":"https://github.com/Blankj/awesome-java-leetcode","used":true,"who":"Mengjie Cai"},{"_id":"59f95971421aa90fef2034ec","createdAt":"2017-11-01T13:19:45.187Z","desc":"Facebook面经记","images":["http://img.gank.io/7aa86243-57c4-43a4-80ba-de2809dd106e"],"publishedAt":"2017-11-01T14:20:59.209Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/fd8d3478f6ee","used":true,"who":"Mengjie Cai"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 59fa764b421aa90fe72535ea
         * createdAt : 2017-11-02T09:35:07.929Z
         * desc : 带你玩转Android Studio 3.0的性能分析工具
         * publishedAt : 2017-11-06T12:40:39.976Z
         * source : web
         * type : Android
         * url : https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247487907&idx=1&sn=202a662ccf28a9b00d7daf2067eb22d6
         * used : true
         * who : 陈宇明
         * images : ["http://img.gank.io/89aeaed4-00b6-41a5-a6f2-89ca03e8a536"]
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
