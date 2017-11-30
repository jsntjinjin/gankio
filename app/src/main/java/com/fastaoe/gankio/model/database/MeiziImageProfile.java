package com.fastaoe.gankio.model.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * Created by jinjin on 17/11/23.
 * description:
 */

@Entity(nameInDb = "meizi_image_profile")
public class MeiziImageProfile {

    @Id
    private String id;
    // 保存的image url地址
    private String imageUrl;
    // 保存的image文件名
    private String imagePath;
    private Long create_date;
    @Generated(hash = 367556667)
    public MeiziImageProfile(String id, String imageUrl, String imagePath,
            Long create_date) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.imagePath = imagePath;
        this.create_date = create_date;
    }
    @Generated(hash = 1489447718)
    public MeiziImageProfile() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public Long getCreate_date() {
        return this.create_date;
    }
    public void setCreate_date(Long create_date) {
        this.create_date = create_date;
    }
}
