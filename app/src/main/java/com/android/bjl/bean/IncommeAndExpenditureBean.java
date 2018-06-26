package com.android.bjl.bean;

public class IncommeAndExpenditureBean extends IBean {

    /**
     * amount : 1000
     * iae_type : 其他收入
     * operator : 孙志茂
     * operator_time : 9.0
     * project : 旧房拆出售钢筋收入
     * sub_operator :
     * town_and_village : 苏溪镇高岭村
     * type : 收入
     */

    private String amount;
    private String iae_type;
    private String operator;
    private String operator_time;
    private String project;
    private String sub_operator;
    private String town_and_village;
    private String type;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIae_type() {
        return iae_type;
    }

    public void setIae_type(String iae_type) {
        this.iae_type = iae_type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator_time() {
        return operator_time;
    }

    public void setOperator_time(String operator_time) {
        this.operator_time = operator_time;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSub_operator() {
        return sub_operator;
    }

    public void setSub_operator(String sub_operator) {
        this.sub_operator = sub_operator;
    }

    public String getTown_and_village() {
        return town_and_village;
    }

    public void setTown_and_village(String town_and_village) {
        this.town_and_village = town_and_village;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
