package com.android.bjl.bean;

public class Subsidy extends IBean{
    /**
     * amount : 111
     * criterion : 补贴标准
     * detail : 补贴面积
     * id : 3
     * status : 0
     * title : 这里是表述方式补贴记录
     * userId : 13
     * userName : 张恒
     */

    private String amount;
    private String criterion;
    private String detail;
    private String id;
    private String status;
    private String title;
    private String userId;
    private String userName;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

