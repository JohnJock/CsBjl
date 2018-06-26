package com.android.bjl.http;

/**
 * Created by john on 2018/4/11.
 */

public class Constant {
    //    public static String SERVER_URL = "http://api.juheapi.com/japi/";//生产
//    public static String SERVER_URL = "http://192.168.0.110:8088/glc_mobile/";//杨义军
    public static String SERVER_URL = "http://47.98.226.156/glc_mobile/";//公网


    public static final class ResultCode {
        public static final int RECALLSDK = 0;//重新调取收银台
        public static final int REFERESH = 1;//刷新界面
    }

    public static final class RequestCode {
        public static final int GOVERNMENTAFFAIRS_CODE = 100;
        public static final int GET_BANK_LIST_CODE = 101;
        public static final int CREATE_PAY_ORDER_CODE = 102;
        public static final int BANK_CARD_MEAS_CODE = 103;
        public static final int SENDMOBILECODE_CODE = 104;
        public static final int BANKCARDBIND_CODE = 105;
        public static final int OPENMOBILEPAYPWD_CODE = 106;
        public static final int BANK_VERIFY_CODE = 107;
        public static final int VALIDATE_MOBILECODE_CODE = 108;
        public static final int SDK_PAY_MONEY_CODE = 109;
        public static final int CREATE_PAY_ORDER_NO_ACCOUNT_CODE = 110;
        public static final int SDK_PAY_CARD_AUTH_CODE = 111;
        public static final int SDK_PAY_MONEY_NO_ACCOUNT_CODE = 112;
        public static final int SDK_PAY_CARD_AUTH_CONF_CODE = 113;
        public static final int SDK_REIMBURSEMENT_NO_ACCOUNT_COCNFIRM_CODE = 114;
        public static final int BANK_CARD_CHECK_CODE = 115;
        public static final int SDK_REIMBURSEMENT_COCNFIRM_CODE = 116;
        public static final int CLOSE_ORDER_OUT_CHECKSTAND_CODE = 117;
        public static final int SDK_CJ_SHORT_MESSAGE_RESEND_CODE = 118;
        public static final int CHANNEL_ROUTE_CODE = 119;

        public static final int DK_CONFIRM = 120;
        public static final int SENDMOBILECODE_CODE_DK = 121;
    }

    /**
     * 党务公开
     */
    public static String GOVERNMENTAFFAIRS = "AppVillageAffairs/governmentAffairs";

    /**
     * 房屋出租上传图片
     */
    public static String RENTOUTHOUSE = "AppConvenience/rentOutHouse";
    /**
     * 登录
     */
    public static String LOGIN = "AppLogin/login";
    /**
     * 找回登录密码/注册
     */
    public static String REGISTERUSER = "AppUser/RegisterUser";
    /**
     * 找回登录密码/注册
     */
    public static String FINDPASSWPRD = "AppUser/findPassword";

    /**
     * 发送手机验证码
     */
    public static String REGISTERSENGSMS = "AppUser/RegisterSendSms";
    /**
     * 获取房屋出租信息列表
     */
    public static String HOUSEINFO = "AppConvenience/houseInfo";

    /**
     * 删除房屋出租信息
     */
    public static String DELETEHOUSEINFO = "AppConvenience/deleteHouseInfo";
    /**
     * 获取求职信息列表
     */
    public static String JOBWANTEDINFO = "AppConvenience/jobWantedInfo";
    /**
     * 发布个人求职信息
     */
    public static String PUSHJOBWANTEDED = "AppConvenience/pushJobWanted";
    /**
     * 删除个人求职信息
     */
    public static String DELETEJOBWANTEDINFO = "AppConvenience/deleteJobWantedInfo";
    /**
     * 获取招聘信息列表
     */
    public static String ADVERTISEFORJOB = "AppConvenience/advertiseForInfo";
    /**
     * 发布招聘信息
     */
    public static String PUSHADVERTISEFOR = "AppConvenience/pushAdvertiseFor";
    /**
     * 删除招聘信息
     */
    public static String DELETEJADVERTISEFORJOB = "AppConvenience/deleteAdvertiseForInfo";
    /**
     * 修改信息
     */
    public static String SUBMITUSERINFO = "AppUser/submitUserInfo";

    /**
     * 补贴公示
     */
    public static String SUBSIDYINFO = "AppSubsidy/subsidyInfo";
    /**
     * 补贴公示根据名字查想
     */
    public static String SUBSIDYINFOBYUSERNAME = "/AppSubsidy/subsidyInfoByUserName";

    /**
     * 收入支出
     */
    public static String INCOMEANDEXPENDITURE = "AppIncomeAndExpenditure/incomeAndExpenditure";


    /**
     * 评论详情
     */
    public static String FINDCOMMENT = "/AppVillageAffairs/findComment";

    /**
     * 发布评论
     */
    public static String COMMENT = "/AppVillageAffairs/comment";


}
