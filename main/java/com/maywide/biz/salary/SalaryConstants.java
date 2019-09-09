package com.maywide.biz.salary;

public class SalaryConstants {

    public static class API_URL{
//        public static  final String URL="http://10.205.28.84:8008/";  //测试地址
//        public static  final String URL="http://10.205.8.22:8001/";  //正式地址
        public static  final String PRE_CENT_SEARCH = "PRE_CENT_SEARCH";  //预积分查询接口
        public static  final String SHARE_CENT = "SHARE_CENT";  //预积分分享接口
        public static  final String REAL_CENT_SEARCH = "REAL_CENT_SEARCH";  //实积分查询接口
        public static  final String SHARE_CENT_SEARCH = "SHARE_CENT_SEARCH";  //分享积分查询
        public static  final String CENCEL_SHARE_CENT = "CENCEL_SHARE_CENT";  //取消分享
        public static  final String ADJUST_CENT_SEARCH = "ADJUST_CENT_SEARCH";  //积分调整查询
        public static  final String ADJUST_CENT = "ADJUST_CENT";  //积分调整
        public static  final String RANK_CENT_SEARCH = "RANK_CENT_SEARCH";  //积分排名
        public static  final String AUDIT_CENT = "AUDIT_CENT";  //积分审核
        public static  final String REAL_CENT_QRY_DETAIL = "REAL_CENT_QRY_DETAIL";  //积分明细
        public static  final String REAL_CENT_QRY_TOTAL = "REAL_CENT_QRY_TOTAL";  //积分汇总
        public static  final String SCENE_PRE_CENT_QRY_TOTAL = "SCENE_PRE_CENT_QRY_TOTAL";  //预积分分场景汇总
        public static  final String SCENE_REAL_CENT_QRY_TOTAL = "SCENE_REAL_CENT_QRY_TOTAL";  //实积分分场景汇总
    }
    public static class OthersKpi{
        public static  final String INIT_STAUTS="0";  //草稿
        public static  final String AUDIT_STAUTS="1";  //审核中
        public static  final String SUCCESS_STAUTS="2";  //审核通过
        public static  final String REJECT_STAUTS="3";  //审核失败
        public static  final String ZSBUSI="ZSBUSI";  //销售积分
        public static  final String ZSSETUP="ZSSETUP";  //安装积分

    }

    public static class OthersKpiAudit{
        public static  final String AUDIT_STAUTS="1";  //待审核
        public static  final String SUCCESS_STAUTS="2";  //审核通过
        public static  final String REJECT_STAUTS="3";  //审核失败
    }

    public static class BaseWage{
        public static  final String BASE_WAGE_TYPE="1";  //基本薪酬
        public static  final String ACHIEVEMENT_TYPE="2";  //运维薪酬
    }
    public static class OthersKpiTextConfig{
        public static  final String YWXC_TYPE="YWXC_TYPE";  //运维薪酬
        public static  final String TCJL_TYPE="TCJL_TYPE";  //提成奖励
    }

    public static class OthersExchangeConfig{
        public static  final String CONTROL_STATUS_OPEN="0";  //开
        public static  final String CONTROL_STATUS_CLOSE="1";  //闭
        public static  final String CONTROL_STATUS_NO="2";  //不判断
    }

    //获取积分类型对应的场景gcode
    public static String getSceneGcode(String groupcode){
       if(OthersKpi.ZSBUSI.equals(groupcode)){
          return "GOODS_RULE_T1";
       }
       if(OthersKpi.ZSSETUP.equals(groupcode)){
           return "ZSGRID_WFLTYPE";
       }
       return null;
    }
}
