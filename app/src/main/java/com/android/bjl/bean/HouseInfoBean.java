package com.android.bjl.bean;

import java.util.ArrayList;
import java.util.List;

public class HouseInfoBean extends IBean {
    /**
     * area : 20
     * description : 靠近大门的车位，好停车，好出去，现在出租，有意向的尽快联系我。
     * filePath : [http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/066d5a86-2413-43e6-8c2b-ce017a13f5b4.png, http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/7e3003dc-c06c-4b4f-b1ae-b43b50e32a5e.png, http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/1efdfa52-655d-4e0b-add7-a1b9eff73091.png, http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/44caab04-0eac-46d9-a5b3-47bbd4189f9b.png, http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/523f4f07-6a65-4863-ad26-b5afdaa1f34b.png, http://192.168.0.107:8088/glc_mobile/static/convenience/file_11/20180501/6d9d95d0-46b5-429d-b7c6-61ba01a79a35.png]
     * id : 6
     * price : 2000
     * telephone : 18701557456
     * title : 车位出租
     * userName : 张恒
     */

    private String area;
    private String description;
    private List<String> filePath;
    private String id;
    private String price;
    private String telephone;
    private String title;
    private String userName;
    private String createTime;
    private String updateTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
