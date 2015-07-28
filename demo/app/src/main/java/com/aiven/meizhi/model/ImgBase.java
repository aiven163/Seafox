package com.aiven.meizhi.model;

import java.io.Serializable;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/7/22  17:29
 * Desc :
 */
public class ImgBase implements Serializable{
    public String thumbImgUrl;
    public String nomalImgUrl;


    public String getThumbImgUrl() {
        return thumbImgUrl;
    }

    public void setThumbImgUrl(String thumbImgUrl) {
        this.thumbImgUrl = thumbImgUrl;
    }

    public String getNomalImgUrl() {
        return nomalImgUrl;
    }

    public void setNomalImgUrl(String nomalImgUrl) {
        this.nomalImgUrl = nomalImgUrl;
    }
}
