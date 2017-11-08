package com.fastaoe.gankio.model.beans;

/**
 * Created by jinjin on 17/11/7.
 * description:
 */

public class AddToGankBean {

    public String url;    //	想要提交的网页地址
    public String desc;   //	对干货内容的描述	单独的文字描述
    public String who;    //	提交者 ID	干货提交者的网络 ID
    public String type;   //	干货类型
                          //    可选参数: Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
    public String debug;  //	当前提交为测试数据	如果想要测试数据是否合法, 请设置 debug 为 true! 可选参数: true | false

}
