package com.fastaoe.gankio.model.beans;

import java.util.List;

/**
 * Created by jinjin on 17/11/2.
 * description:
 */

public class HistoryForOneDay {

    public String error;

    public List<ContentItem> results;

    public class ContentItem {
        public String _id;
        public String content;
        public String publishedAt;
        public String title;
    }

}
