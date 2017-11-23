package com.fastaoe.gankio.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jinjin on 17/11/23.
 * description:
 */

@Entity(nameInDb = "gank_item_profile")
public class GankItemProfile {

    @Id
    private String id;// gankio.com中的id用来查询

    private Date createdAt;
    private String desc;
    private Date publishedAt;
    private String type;
    private String url;
    private String who;
    private String images;//保存的图片集合使用"|"来分割
    private boolean collectioned;// 是否收藏
    private Date collectionedAt;// 收藏时间
    private boolean laterReadered;// 是否稍后阅读
    private Date laterReaderedAt;// 稍后阅读时间
    private boolean readed;// 是否已经阅读过
    private Date readedAt;// 上次阅读时间
    @Generated(hash = 765610103)
    public GankItemProfile(String id, Date createdAt, String desc, Date publishedAt,
            String type, String url, String who, String images,
            boolean collectioned, Date collectionedAt, boolean laterReadered,
            Date laterReaderedAt, boolean readed, Date readedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.publishedAt = publishedAt;
        this.type = type;
        this.url = url;
        this.who = who;
        this.images = images;
        this.collectioned = collectioned;
        this.collectionedAt = collectionedAt;
        this.laterReadered = laterReadered;
        this.laterReaderedAt = laterReaderedAt;
        this.readed = readed;
        this.readedAt = readedAt;
    }
    @Generated(hash = 1585706593)
    public GankItemProfile() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Date getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Date getPublishedAt() {
        return this.publishedAt;
    }
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getWho() {
        return this.who;
    }
    public void setWho(String who) {
        this.who = who;
    }
    public String getImages() {
        return this.images;
    }
    public void setImages(String images) {
        this.images = images;
    }
    public boolean getCollectioned() {
        return this.collectioned;
    }
    public void setCollectioned(boolean collectioned) {
        this.collectioned = collectioned;
    }
    public boolean getLaterReadered() {
        return this.laterReadered;
    }
    public void setLaterReadered(boolean laterReadered) {
        this.laterReadered = laterReadered;
    }
    public boolean getReaded() {
        return this.readed;
    }
    public void setReaded(boolean readed) {
        this.readed = readed;
    }
    public Date getReadedAt() {
        return this.readedAt;
    }
    public void setReadedAt(Date readedAt) {
        this.readedAt = readedAt;
    }
    public Date getCollectionedAt() {
        return this.collectionedAt;
    }
    public void setCollectionedAt(Date collectionedAt) {
        this.collectionedAt = collectionedAt;
    }
    public Date getLaterReaderedAt() {
        return this.laterReaderedAt;
    }
    public void setLaterReaderedAt(Date laterReaderedAt) {
        this.laterReaderedAt = laterReaderedAt;
    }
}
