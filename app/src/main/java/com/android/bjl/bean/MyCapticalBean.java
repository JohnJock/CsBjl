package com.android.bjl.bean;

public class MyCapticalBean extends IBean {
    private String title;
    private String money;

    public MyCapticalBean(String title, String money) {
        this.title = title;
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
