package com.android.bjl.bean;

/**
 * Created by john on 2018/4/20.
 */

public class MyrentBean extends  IBean {

    public MyrentBean(String title, String rentAmount, String phone, String img) {
        this.title = title;
        this.rentAmount = rentAmount;
        this.phone = phone;
        this.img = img;
    }

    /**
     * title : qw
     * area : w
     * id : 1500714051
     * rentAmount : g3nwiZB6HSuybEhi
     * phone : wx20180331192902b98c713c300325816195
     * produce : Sign=WXPay
     * appid : wx6ea5846abbabe104
     * img :
     */

    private String title;
    private String area;
    private String id;
    private String rentAmount;
    private String phone;
    private String produce;
    private String appid;
    private String img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
