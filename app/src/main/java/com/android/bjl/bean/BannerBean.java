package com.android.bjl.bean;

import java.io.Serializable;

/**
 * Created by john on 2018/3/5.
 */

public class BannerBean implements Serializable {
    private Integer url;
    private String description;

    public BannerBean(Integer url, String description) {
        this.url = url;
        this.description = description;
    }

    public Integer getUrl() {
        return url;
    }

    public void setUrl(Integer url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
