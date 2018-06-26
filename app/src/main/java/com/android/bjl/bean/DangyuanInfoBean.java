package com.android.bjl.bean;

public class DangyuanInfoBean extends IBean {
    /**
     * author : è§„å®š
     * content : è§„èŒƒçš„éƒ½æ˜¯
     * createTime : 2018-04-22 21:56:37
     * title : é˜¿å‘
     */

    private String author;
    private String content;
    private String createTime;
    private String title;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
