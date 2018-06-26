package com.android.bjl.bean;

/**
 * 个人信息
 */

public class UserBean extends IBean {

    /**
     * certNo : 410422199009257000
     * mobilePhone : 13000000000
     * status : 0
     * userId : 10
     * userName : 王艳
     * userType : 0
     */

    private String certNo;
    private String mobilePhone;
    private String status;//0 正常状态  -1  注销
    private String userId;
    private String userName;
    private String userType;//0代表本村普通用户  2游客

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
