package com.android.bjl.bean;

/**
 * Created by john on 2018/4/13.
 */

public class HistoryIndex extends IBean {

    /**
     * _id : 18141001
     * title : 反法联盟各参加国在奥地利首都维也纳召开会议
     * pic : http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/201110/2/1F81726127.jpg
     * year : 1814
     * month : 10
     * day : 1
     * des : 在204年前的今天，1814年10月1日 (农历八月十八)，反法联盟各参加国在奥地利首都维也纳召开会议。
     * lunar : 甲戌年八月十八
     */

    private String _id;
    private String title;
    private String pic;
    private int year;
    private int month;
    private int day;
    private String des;
    private String lunar;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }
}
