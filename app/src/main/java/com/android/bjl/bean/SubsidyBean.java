package com.android.bjl.bean;

import java.util.ArrayList;
import com.android.bjl.bean.Subsidy;

/**
 * 补贴bean
 */
public class SubsidyBean extends IBean {
    //    {
//        "subsidyData": [
//        {
//            "amount": 111,
//                "criterion": "补贴标准",
//                "detail": "补贴面积",
//                "id": 3,
//                "status": "0",
//                "title": "这里是表述方式补贴记录",
//                "userId": 13,
//                "userName": "张恒"
//        },
//        {
//            "amount": 13,
//                "criterion": "补贴标准",
//                "detail": "补贴面积",
//                "id": 2,
//                "status": "0",
//                "title": "这里是啊啊啊补贴记录",
//                "userId": 13,
//                "userName": "张恒"
//        },
//        {
//            "amount": 12,
//                "criterion": "补贴标准",
//                "detail": "补贴面积",
//                "id": 1,
//                "status": "0",
//                "title": "这里是补贴记录",
//                "userId": 13,
//                "userName": "张恒"
//        }
//    ],
//           "subsidyTotal": "20",
//            "subsidyTypeName": "耕地补贴",
//            "total": "136.00",
//            "userTotal": "200"
//          "subsidyType": "2",
//    }
//
    String subsidyTypeName;
    String subsidyTotal;
    String userTotal;
    String total;
    String subsidyType;
    ArrayList<Subsidy> subsidyData;

    public String getSubsidyType() {
        return subsidyType;
    }

    public void setSubsidyType(String subsidyType) {
        this.subsidyType = subsidyType;
    }

    public String getSubsidyTotal() {
        return subsidyTotal;
    }

    public void setSubsidyTotal(String subsidyTotal) {
        this.subsidyTotal = subsidyTotal;
    }

    public String getUserTotal() {
        return userTotal;
    }

    public void setUserTotal(String userTotal) {
        this.userTotal = userTotal;
    }

    public String getSubsidyTypeName() {
        return subsidyTypeName;
    }

    public void setSubsidyTypeName(String subsidyTypeName) {
        this.subsidyTypeName = subsidyTypeName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Subsidy> getSubsidyData() {
        return subsidyData;
    }

    public void setSubsidyData(ArrayList<Subsidy> subsidyData) {
        this.subsidyData = subsidyData;
    }

}