package com.maywide.biz.inter.pojo.queWorkOrderListByCustid;

import java.util.List;

import com.maywide.biz.inter.pojo.cmptask.CmpBaseResp;

public class CustomerWorkTagBean extends CmpBaseResp{

    /**
     * code : 0
     * data : [{"ACC_NBR_TYPE":"1","AREA_ID":"66013","AREA_NAME":"外海站","BANDWIDTH":"","BF_ORDER_ID":"","BLANK0":"","BLANK1":"","BLANK2":"","BLANK3":"","BLANK4":"","BLANK5":"","BLANK6":"","BLANK7":"","BLANK8":"","BLANK9":"","BRANCH":"","BUSIKIND":"","CALLIN_CODE":"15766232562","CALL_ID":"180824888417","CARDTYPE":"","CARDTYPENAME":"","CATEGORY":"businessWorkflow","CERT_CODE":"","CERT_TYPE":"","CITY":"15","CLIENTTYPE":"","CLIENTTYPENAME":"","CMACCTNO":"","CMPWD":"","COMPLETE_DATE":"","CONTENT":"未超时，催修次数：1次，运维情况（1有联系上过门未修复，2有联系无上门未修复，3无联系无上门未修复），广东省江门市江海区外海镇麻一龙桥里一路20号，模拟信号，无信号，用户表示打雷后整栋楼都无信号，联系号码：15766232562,（来电），15976439318，用户表示师傅最好星期日上门","CON_ADDR":"","CON_MOBILE":"0","CON_NAME":"","CON_TEL":"0","CTERNODENAME":"","CUST_ADDR":"广东省江门市江海区外海镇麻一龙桥里一路20号","CUST_NAME":"林长林","CUST_NO":"3690001450","CUST_PRI":"","CUST_STATE":"","CUST_TYPE":"","EMAIL":"","EQCARDTYPE":"","EQUIPKIND":"","EQUIPNO":"","FEEDBACK_TIME":"","GROUP_REASON":"","HOUSETYPENAME":"","INPUTOR":"","INPUTORNAME":"","INPUTSUBWAY":"","INPUTSUBWAYNAME":"","INPUTWAY":"","INPUTWAYNAME":"","INTEND_DATE":"2018-08-25 18:19:09.0","LAST_FAULT_REASON":"","LINEID":"","LINEKINDNAME":"","LINENAME":"","LINETYPE":"","MAINNUM":"0","MEMO":"3690001450","MTYPE":"I","NETWORK_TYPE":"","NEWADDRESS":"","NEWEQUIPNO":"","NEWHOUSETYPE":"","NEWINPUTSUBWAYNAME":"","NEWINPUTWAYNAME":"","NEWNAMES":"","NEWPHONE":"","NEWUSERID":"","NODEADDR":"","NODENAME":"","NODETYPE":"","OLDADDRESS":"","OLDEQUIPNO":"","OLDHOUSETYPE":"","OLDINPUTSUBWAYNAME":"","OLDINPUTWAYNAME":"","OLDNAMES":"","OLDPHONE":"","OLDUSERID":"wh962189","OPCODE":"","OPCODENAME":"","OPERATOR":"","OPERATORNAME":"","OPTIME":"2017-04-10","ORDER_DATE":"2018-08-24 18:20:12.0","ORDER_ID":"S1808246834968","PACKAGE_TYPE":"","PERMARK":"","PLACE":"","PLACENAME":"","PRI":"1","PROMISE_TIME":"","QC_STATE":"","REF_ORDER_ID":"","REGION_ID":"15","SERIALNO":"","SMS_ID":"","SM_NO":"","SM_STATE":"","STAFF_ID":"34983","STANDBY1":"","STANDBY2":"","STANDBY3":"","STANDBY4":"","STANDBY5":"","STBATTRS":"","STB_COMPANY":"","STB_NO":"","STYPE":"356710","SUBNUM":"0","SUBSETTYPE":"","SUBSETTYPENAME":"","SUPERVISE_REASON":"","TALKTIME":"225","TERMINATOR_TYPE":"","UNIT_CODE":"","UNIT_NAME":"","URGE_COUNT":"0","USERKIND":"","USERKINDNAME":"","WFRECID":"0","WORKTYPE":"","ZIP":""}]
     * msg : 根据custid获取工单信息列表!
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ACC_NBR_TYPE : 1
         * AREA_ID : 66013
         * AREA_NAME : 外海站
         * BANDWIDTH :
         * BF_ORDER_ID :
         * BLANK0 :
         * BLANK1 :
         * BLANK2 :
         * BLANK3 :
         * BLANK4 :
         * BLANK5 :
         * BLANK6 :
         * BLANK7 :
         * BLANK8 :
         * BLANK9 :
         * BRANCH :
         * BUSIKIND :
         * CALLIN_CODE : 15766232562
         * CALL_ID : 180824888417
         * CARDTYPE :
         * CARDTYPENAME :
         * CATEGORY : businessWorkflow
         * CERT_CODE :
         * CERT_TYPE :
         * CITY : 15
         * CLIENTTYPE :
         * CLIENTTYPENAME :
         * CMACCTNO :
         * CMPWD :
         * COMPLETE_DATE :
         * CONTENT : 未超时，催修次数：1次，运维情况（1有联系上过门未修复，2有联系无上门未修复，3无联系无上门未修复），广东省江门市江海区外海镇麻一龙桥里一路20号，模拟信号，无信号，用户表示打雷后整栋楼都无信号，联系号码：15766232562,（来电），15976439318，用户表示师傅最好星期日上门
         * CON_ADDR :
         * CON_MOBILE : 0
         * CON_NAME :
         * CON_TEL : 0
         * CTERNODENAME :
         * CUST_ADDR : 广东省江门市江海区外海镇麻一龙桥里一路20号
         * CUST_NAME : 林长林
         * CUST_NO : 3690001450
         * CUST_PRI :
         * CUST_STATE :
         * CUST_TYPE :
         * EMAIL :
         * EQCARDTYPE :
         * EQUIPKIND :
         * EQUIPNO :
         * FEEDBACK_TIME :
         * GROUP_REASON :
         * HOUSETYPENAME :
         * INPUTOR :
         * INPUTORNAME :
         * INPUTSUBWAY :
         * INPUTSUBWAYNAME :
         * INPUTWAY :
         * INPUTWAYNAME :
         * INTEND_DATE : 2018-08-25 18:19:09.0
         * LAST_FAULT_REASON :
         * LINEID :
         * LINEKINDNAME :
         * LINENAME :
         * LINETYPE :
         * MAINNUM : 0
         * MEMO : 3690001450
         * MTYPE : I
         * NETWORK_TYPE :
         * NEWADDRESS :
         * NEWEQUIPNO :
         * NEWHOUSETYPE :
         * NEWINPUTSUBWAYNAME :
         * NEWINPUTWAYNAME :
         * NEWNAMES :
         * NEWPHONE :
         * NEWUSERID :
         * NODEADDR :
         * NODENAME :
         * NODETYPE :
         * OLDADDRESS :
         * OLDEQUIPNO :
         * OLDHOUSETYPE :
         * OLDINPUTSUBWAYNAME :
         * OLDINPUTWAYNAME :
         * OLDNAMES :
         * OLDPHONE :
         * OLDUSERID : wh962189
         * OPCODE :
         * OPCODENAME :
         * OPERATOR :
         * OPERATORNAME :
         * OPTIME : 2017-04-10
         * ORDER_DATE : 2018-08-24 18:20:12.0
         * ORDER_ID : S1808246834968
         * PACKAGE_TYPE :
         * PERMARK :
         * PLACE :
         * PLACENAME :
         * PRI : 1
         * PROMISE_TIME :
         * QC_STATE :
         * REF_ORDER_ID :
         * REGION_ID : 15
         * SERIALNO :
         * SMS_ID :
         * SM_NO :
         * SM_STATE :
         * STAFF_ID : 34983
         * STANDBY1 :
         * STANDBY2 :
         * STANDBY3 :
         * STANDBY4 :
         * STANDBY5 :
         * STBATTRS :
         * STB_COMPANY :
         * STB_NO :
         * STYPE : 356710
         * SUBNUM : 0
         * SUBSETTYPE :
         * SUBSETTYPENAME :
         * SUPERVISE_REASON :
         * TALKTIME : 225
         * TERMINATOR_TYPE :
         * UNIT_CODE :
         * UNIT_NAME :
         * URGE_COUNT : 0
         * USERKIND :
         * USERKINDNAME :
         * WFRECID : 0
         * WORKTYPE :
         * ZIP :
         */

        private String ACC_NBR_TYPE;
        private String AREA_ID;
        private String AREA_NAME;
        private String BANDWIDTH;
        private String BF_ORDER_ID;
        private String BLANK0;
        private String BLANK1;
        private String BLANK2;
        private String BLANK3;
        private String BLANK4;
        private String BLANK5;
        private String BLANK6;
        private String BLANK7;
        private String BLANK8;
        private String BLANK9;
        private String BRANCH;
        private String BUSIKIND;
        private String CALLIN_CODE;
        private String CALL_ID;
        private String CARDTYPE;
        private String CARDTYPENAME;
        private String CATEGORY;
        private String CERT_CODE;
        private String CERT_TYPE;
        private String CITY;
        private String CLIENTTYPE;
        private String CLIENTTYPENAME;
        private String CMACCTNO;
        private String CMPWD;
        private String COMPLETE_DATE;
        private String CONTENT;
        private String CON_ADDR;
        private String CON_MOBILE;
        private String CON_NAME;
        private String CON_TEL;
        private String CTERNODENAME;
        private String CUST_ADDR;
        private String CUST_NAME;
        private String CUST_NO;
        private String CUST_PRI;
        private String CUST_STATE;
        private String CUST_TYPE;
        private String EMAIL;
        private String EQCARDTYPE;
        private String EQUIPKIND;
        private String EQUIPNO;
        private String FEEDBACK_TIME;
        private String GROUP_REASON;
        private String HOUSETYPENAME;
        private String INPUTOR;
        private String INPUTORNAME;
        private String INPUTSUBWAY;
        private String INPUTSUBWAYNAME;
        private String INPUTWAY;
        private String INPUTWAYNAME;
        private String INTEND_DATE;
        private String LAST_FAULT_REASON;
        private String LINEID;
        private String LINEKINDNAME;
        private String LINENAME;
        private String LINETYPE;
        private String MAINNUM;
        private String MEMO;
        private String MTYPE;
        private String NETWORK_TYPE;
        private String NEWADDRESS;
        private String NEWEQUIPNO;
        private String NEWHOUSETYPE;
        private String NEWINPUTSUBWAYNAME;
        private String NEWINPUTWAYNAME;
        private String NEWNAMES;
        private String NEWPHONE;
        private String NEWUSERID;
        private String NODEADDR;
        private String NODENAME;
        private String NODETYPE;
        private String OLDADDRESS;
        private String OLDEQUIPNO;
        private String OLDHOUSETYPE;
        private String OLDINPUTSUBWAYNAME;
        private String OLDINPUTWAYNAME;
        private String OLDNAMES;
        private String OLDPHONE;
        private String OLDUSERID;
        private String OPCODE;
        private String OPCODENAME;
        private String OPERATOR;
        private String OPERATORNAME;
        private String OPTIME;
        private String ORDER_DATE;
        private String ORDER_ID;
        private String PACKAGE_TYPE;
        private String PERMARK;
        private String PLACE;
        private String PLACENAME;
        private String PRI;
        private String PROMISE_TIME;
        private String QC_STATE;
        private String REF_ORDER_ID;
        private String REGION_ID;
        private String SERIALNO;
        private String SMS_ID;
        private String SM_NO;
        private String SM_STATE;
        private String STAFF_ID;
        private String STANDBY1;
        private String STANDBY2;
        private String STANDBY3;
        private String STANDBY4;
        private String STANDBY5;
        private String STBATTRS;
        private String STB_COMPANY;
        private String STB_NO;
        private String STYPE;
        private String SUBNUM;
        private String SUBSETTYPE;
        private String SUBSETTYPENAME;
        private String SUPERVISE_REASON;
        private String TALKTIME;
        private String TERMINATOR_TYPE;
        private String UNIT_CODE;
        private String UNIT_NAME;
        private String URGE_COUNT;
        private String USERKIND;
        private String USERKINDNAME;
        private String WFRECID;
        private String WORKTYPE;
        private String ZIP;

        public String getACC_NBR_TYPE() {
            return ACC_NBR_TYPE;
        }

        public void setACC_NBR_TYPE(String ACC_NBR_TYPE) {
            this.ACC_NBR_TYPE = ACC_NBR_TYPE;
        }

        public String getAREA_ID() {
            return AREA_ID;
        }

        public void setAREA_ID(String AREA_ID) {
            this.AREA_ID = AREA_ID;
        }

        public String getAREA_NAME() {
            return AREA_NAME;
        }

        public void setAREA_NAME(String AREA_NAME) {
            this.AREA_NAME = AREA_NAME;
        }

        public String getBANDWIDTH() {
            return BANDWIDTH;
        }

        public void setBANDWIDTH(String BANDWIDTH) {
            this.BANDWIDTH = BANDWIDTH;
        }

        public String getBF_ORDER_ID() {
            return BF_ORDER_ID;
        }

        public void setBF_ORDER_ID(String BF_ORDER_ID) {
            this.BF_ORDER_ID = BF_ORDER_ID;
        }

        public String getBLANK0() {
            return BLANK0;
        }

        public void setBLANK0(String BLANK0) {
            this.BLANK0 = BLANK0;
        }

        public String getBLANK1() {
            return BLANK1;
        }

        public void setBLANK1(String BLANK1) {
            this.BLANK1 = BLANK1;
        }

        public String getBLANK2() {
            return BLANK2;
        }

        public void setBLANK2(String BLANK2) {
            this.BLANK2 = BLANK2;
        }

        public String getBLANK3() {
            return BLANK3;
        }

        public void setBLANK3(String BLANK3) {
            this.BLANK3 = BLANK3;
        }

        public String getBLANK4() {
            return BLANK4;
        }

        public void setBLANK4(String BLANK4) {
            this.BLANK4 = BLANK4;
        }

        public String getBLANK5() {
            return BLANK5;
        }

        public void setBLANK5(String BLANK5) {
            this.BLANK5 = BLANK5;
        }

        public String getBLANK6() {
            return BLANK6;
        }

        public void setBLANK6(String BLANK6) {
            this.BLANK6 = BLANK6;
        }

        public String getBLANK7() {
            return BLANK7;
        }

        public void setBLANK7(String BLANK7) {
            this.BLANK7 = BLANK7;
        }

        public String getBLANK8() {
            return BLANK8;
        }

        public void setBLANK8(String BLANK8) {
            this.BLANK8 = BLANK8;
        }

        public String getBLANK9() {
            return BLANK9;
        }

        public void setBLANK9(String BLANK9) {
            this.BLANK9 = BLANK9;
        }

        public String getBRANCH() {
            return BRANCH;
        }

        public void setBRANCH(String BRANCH) {
            this.BRANCH = BRANCH;
        }

        public String getBUSIKIND() {
            return BUSIKIND;
        }

        public void setBUSIKIND(String BUSIKIND) {
            this.BUSIKIND = BUSIKIND;
        }

        public String getCALLIN_CODE() {
            return CALLIN_CODE;
        }

        public void setCALLIN_CODE(String CALLIN_CODE) {
            this.CALLIN_CODE = CALLIN_CODE;
        }

        public String getCALL_ID() {
            return CALL_ID;
        }

        public void setCALL_ID(String CALL_ID) {
            this.CALL_ID = CALL_ID;
        }

        public String getCARDTYPE() {
            return CARDTYPE;
        }

        public void setCARDTYPE(String CARDTYPE) {
            this.CARDTYPE = CARDTYPE;
        }

        public String getCARDTYPENAME() {
            return CARDTYPENAME;
        }

        public void setCARDTYPENAME(String CARDTYPENAME) {
            this.CARDTYPENAME = CARDTYPENAME;
        }

        public String getCATEGORY() {
            return CATEGORY;
        }

        public void setCATEGORY(String CATEGORY) {
            this.CATEGORY = CATEGORY;
        }

        public String getCERT_CODE() {
            return CERT_CODE;
        }

        public void setCERT_CODE(String CERT_CODE) {
            this.CERT_CODE = CERT_CODE;
        }

        public String getCERT_TYPE() {
            return CERT_TYPE;
        }

        public void setCERT_TYPE(String CERT_TYPE) {
            this.CERT_TYPE = CERT_TYPE;
        }

        public String getCITY() {
            return CITY;
        }

        public void setCITY(String CITY) {
            this.CITY = CITY;
        }

        public String getCLIENTTYPE() {
            return CLIENTTYPE;
        }

        public void setCLIENTTYPE(String CLIENTTYPE) {
            this.CLIENTTYPE = CLIENTTYPE;
        }

        public String getCLIENTTYPENAME() {
            return CLIENTTYPENAME;
        }

        public void setCLIENTTYPENAME(String CLIENTTYPENAME) {
            this.CLIENTTYPENAME = CLIENTTYPENAME;
        }

        public String getCMACCTNO() {
            return CMACCTNO;
        }

        public void setCMACCTNO(String CMACCTNO) {
            this.CMACCTNO = CMACCTNO;
        }

        public String getCMPWD() {
            return CMPWD;
        }

        public void setCMPWD(String CMPWD) {
            this.CMPWD = CMPWD;
        }

        public String getCOMPLETE_DATE() {
            return COMPLETE_DATE;
        }

        public void setCOMPLETE_DATE(String COMPLETE_DATE) {
            this.COMPLETE_DATE = COMPLETE_DATE;
        }

        public String getCONTENT() {
            return CONTENT;
        }

        public void setCONTENT(String CONTENT) {
            this.CONTENT = CONTENT;
        }

        public String getCON_ADDR() {
            return CON_ADDR;
        }

        public void setCON_ADDR(String CON_ADDR) {
            this.CON_ADDR = CON_ADDR;
        }

        public String getCON_MOBILE() {
            return CON_MOBILE;
        }

        public void setCON_MOBILE(String CON_MOBILE) {
            this.CON_MOBILE = CON_MOBILE;
        }

        public String getCON_NAME() {
            return CON_NAME;
        }

        public void setCON_NAME(String CON_NAME) {
            this.CON_NAME = CON_NAME;
        }

        public String getCON_TEL() {
            return CON_TEL;
        }

        public void setCON_TEL(String CON_TEL) {
            this.CON_TEL = CON_TEL;
        }

        public String getCTERNODENAME() {
            return CTERNODENAME;
        }

        public void setCTERNODENAME(String CTERNODENAME) {
            this.CTERNODENAME = CTERNODENAME;
        }

        public String getCUST_ADDR() {
            return CUST_ADDR;
        }

        public void setCUST_ADDR(String CUST_ADDR) {
            this.CUST_ADDR = CUST_ADDR;
        }

        public String getCUST_NAME() {
            return CUST_NAME;
        }

        public void setCUST_NAME(String CUST_NAME) {
            this.CUST_NAME = CUST_NAME;
        }

        public String getCUST_NO() {
            return CUST_NO;
        }

        public void setCUST_NO(String CUST_NO) {
            this.CUST_NO = CUST_NO;
        }

        public String getCUST_PRI() {
            return CUST_PRI;
        }

        public void setCUST_PRI(String CUST_PRI) {
            this.CUST_PRI = CUST_PRI;
        }

        public String getCUST_STATE() {
            return CUST_STATE;
        }

        public void setCUST_STATE(String CUST_STATE) {
            this.CUST_STATE = CUST_STATE;
        }

        public String getCUST_TYPE() {
            return CUST_TYPE;
        }

        public void setCUST_TYPE(String CUST_TYPE) {
            this.CUST_TYPE = CUST_TYPE;
        }

        public String getEMAIL() {
            return EMAIL;
        }

        public void setEMAIL(String EMAIL) {
            this.EMAIL = EMAIL;
        }

        public String getEQCARDTYPE() {
            return EQCARDTYPE;
        }

        public void setEQCARDTYPE(String EQCARDTYPE) {
            this.EQCARDTYPE = EQCARDTYPE;
        }

        public String getEQUIPKIND() {
            return EQUIPKIND;
        }

        public void setEQUIPKIND(String EQUIPKIND) {
            this.EQUIPKIND = EQUIPKIND;
        }

        public String getEQUIPNO() {
            return EQUIPNO;
        }

        public void setEQUIPNO(String EQUIPNO) {
            this.EQUIPNO = EQUIPNO;
        }

        public String getFEEDBACK_TIME() {
            return FEEDBACK_TIME;
        }

        public void setFEEDBACK_TIME(String FEEDBACK_TIME) {
            this.FEEDBACK_TIME = FEEDBACK_TIME;
        }

        public String getGROUP_REASON() {
            return GROUP_REASON;
        }

        public void setGROUP_REASON(String GROUP_REASON) {
            this.GROUP_REASON = GROUP_REASON;
        }

        public String getHOUSETYPENAME() {
            return HOUSETYPENAME;
        }

        public void setHOUSETYPENAME(String HOUSETYPENAME) {
            this.HOUSETYPENAME = HOUSETYPENAME;
        }

        public String getINPUTOR() {
            return INPUTOR;
        }

        public void setINPUTOR(String INPUTOR) {
            this.INPUTOR = INPUTOR;
        }

        public String getINPUTORNAME() {
            return INPUTORNAME;
        }

        public void setINPUTORNAME(String INPUTORNAME) {
            this.INPUTORNAME = INPUTORNAME;
        }

        public String getINPUTSUBWAY() {
            return INPUTSUBWAY;
        }

        public void setINPUTSUBWAY(String INPUTSUBWAY) {
            this.INPUTSUBWAY = INPUTSUBWAY;
        }

        public String getINPUTSUBWAYNAME() {
            return INPUTSUBWAYNAME;
        }

        public void setINPUTSUBWAYNAME(String INPUTSUBWAYNAME) {
            this.INPUTSUBWAYNAME = INPUTSUBWAYNAME;
        }

        public String getINPUTWAY() {
            return INPUTWAY;
        }

        public void setINPUTWAY(String INPUTWAY) {
            this.INPUTWAY = INPUTWAY;
        }

        public String getINPUTWAYNAME() {
            return INPUTWAYNAME;
        }

        public void setINPUTWAYNAME(String INPUTWAYNAME) {
            this.INPUTWAYNAME = INPUTWAYNAME;
        }

        public String getINTEND_DATE() {
            return INTEND_DATE;
        }

        public void setINTEND_DATE(String INTEND_DATE) {
            this.INTEND_DATE = INTEND_DATE;
        }

        public String getLAST_FAULT_REASON() {
            return LAST_FAULT_REASON;
        }

        public void setLAST_FAULT_REASON(String LAST_FAULT_REASON) {
            this.LAST_FAULT_REASON = LAST_FAULT_REASON;
        }

        public String getLINEID() {
            return LINEID;
        }

        public void setLINEID(String LINEID) {
            this.LINEID = LINEID;
        }

        public String getLINEKINDNAME() {
            return LINEKINDNAME;
        }

        public void setLINEKINDNAME(String LINEKINDNAME) {
            this.LINEKINDNAME = LINEKINDNAME;
        }

        public String getLINENAME() {
            return LINENAME;
        }

        public void setLINENAME(String LINENAME) {
            this.LINENAME = LINENAME;
        }

        public String getLINETYPE() {
            return LINETYPE;
        }

        public void setLINETYPE(String LINETYPE) {
            this.LINETYPE = LINETYPE;
        }

        public String getMAINNUM() {
            return MAINNUM;
        }

        public void setMAINNUM(String MAINNUM) {
            this.MAINNUM = MAINNUM;
        }

        public String getMEMO() {
            return MEMO;
        }

        public void setMEMO(String MEMO) {
            this.MEMO = MEMO;
        }

        public String getMTYPE() {
            return MTYPE;
        }

        public void setMTYPE(String MTYPE) {
            this.MTYPE = MTYPE;
        }

        public String getNETWORK_TYPE() {
            return NETWORK_TYPE;
        }

        public void setNETWORK_TYPE(String NETWORK_TYPE) {
            this.NETWORK_TYPE = NETWORK_TYPE;
        }

        public String getNEWADDRESS() {
            return NEWADDRESS;
        }

        public void setNEWADDRESS(String NEWADDRESS) {
            this.NEWADDRESS = NEWADDRESS;
        }

        public String getNEWEQUIPNO() {
            return NEWEQUIPNO;
        }

        public void setNEWEQUIPNO(String NEWEQUIPNO) {
            this.NEWEQUIPNO = NEWEQUIPNO;
        }

        public String getNEWHOUSETYPE() {
            return NEWHOUSETYPE;
        }

        public void setNEWHOUSETYPE(String NEWHOUSETYPE) {
            this.NEWHOUSETYPE = NEWHOUSETYPE;
        }

        public String getNEWINPUTSUBWAYNAME() {
            return NEWINPUTSUBWAYNAME;
        }

        public void setNEWINPUTSUBWAYNAME(String NEWINPUTSUBWAYNAME) {
            this.NEWINPUTSUBWAYNAME = NEWINPUTSUBWAYNAME;
        }

        public String getNEWINPUTWAYNAME() {
            return NEWINPUTWAYNAME;
        }

        public void setNEWINPUTWAYNAME(String NEWINPUTWAYNAME) {
            this.NEWINPUTWAYNAME = NEWINPUTWAYNAME;
        }

        public String getNEWNAMES() {
            return NEWNAMES;
        }

        public void setNEWNAMES(String NEWNAMES) {
            this.NEWNAMES = NEWNAMES;
        }

        public String getNEWPHONE() {
            return NEWPHONE;
        }

        public void setNEWPHONE(String NEWPHONE) {
            this.NEWPHONE = NEWPHONE;
        }

        public String getNEWUSERID() {
            return NEWUSERID;
        }

        public void setNEWUSERID(String NEWUSERID) {
            this.NEWUSERID = NEWUSERID;
        }

        public String getNODEADDR() {
            return NODEADDR;
        }

        public void setNODEADDR(String NODEADDR) {
            this.NODEADDR = NODEADDR;
        }

        public String getNODENAME() {
            return NODENAME;
        }

        public void setNODENAME(String NODENAME) {
            this.NODENAME = NODENAME;
        }

        public String getNODETYPE() {
            return NODETYPE;
        }

        public void setNODETYPE(String NODETYPE) {
            this.NODETYPE = NODETYPE;
        }

        public String getOLDADDRESS() {
            return OLDADDRESS;
        }

        public void setOLDADDRESS(String OLDADDRESS) {
            this.OLDADDRESS = OLDADDRESS;
        }

        public String getOLDEQUIPNO() {
            return OLDEQUIPNO;
        }

        public void setOLDEQUIPNO(String OLDEQUIPNO) {
            this.OLDEQUIPNO = OLDEQUIPNO;
        }

        public String getOLDHOUSETYPE() {
            return OLDHOUSETYPE;
        }

        public void setOLDHOUSETYPE(String OLDHOUSETYPE) {
            this.OLDHOUSETYPE = OLDHOUSETYPE;
        }

        public String getOLDINPUTSUBWAYNAME() {
            return OLDINPUTSUBWAYNAME;
        }

        public void setOLDINPUTSUBWAYNAME(String OLDINPUTSUBWAYNAME) {
            this.OLDINPUTSUBWAYNAME = OLDINPUTSUBWAYNAME;
        }

        public String getOLDINPUTWAYNAME() {
            return OLDINPUTWAYNAME;
        }

        public void setOLDINPUTWAYNAME(String OLDINPUTWAYNAME) {
            this.OLDINPUTWAYNAME = OLDINPUTWAYNAME;
        }

        public String getOLDNAMES() {
            return OLDNAMES;
        }

        public void setOLDNAMES(String OLDNAMES) {
            this.OLDNAMES = OLDNAMES;
        }

        public String getOLDPHONE() {
            return OLDPHONE;
        }

        public void setOLDPHONE(String OLDPHONE) {
            this.OLDPHONE = OLDPHONE;
        }

        public String getOLDUSERID() {
            return OLDUSERID;
        }

        public void setOLDUSERID(String OLDUSERID) {
            this.OLDUSERID = OLDUSERID;
        }

        public String getOPCODE() {
            return OPCODE;
        }

        public void setOPCODE(String OPCODE) {
            this.OPCODE = OPCODE;
        }

        public String getOPCODENAME() {
            return OPCODENAME;
        }

        public void setOPCODENAME(String OPCODENAME) {
            this.OPCODENAME = OPCODENAME;
        }

        public String getOPERATOR() {
            return OPERATOR;
        }

        public void setOPERATOR(String OPERATOR) {
            this.OPERATOR = OPERATOR;
        }

        public String getOPERATORNAME() {
            return OPERATORNAME;
        }

        public void setOPERATORNAME(String OPERATORNAME) {
            this.OPERATORNAME = OPERATORNAME;
        }

        public String getOPTIME() {
            return OPTIME;
        }

        public void setOPTIME(String OPTIME) {
            this.OPTIME = OPTIME;
        }

        public String getORDER_DATE() {
            return ORDER_DATE;
        }

        public void setORDER_DATE(String ORDER_DATE) {
            this.ORDER_DATE = ORDER_DATE;
        }

        public String getORDER_ID() {
            return ORDER_ID;
        }

        public void setORDER_ID(String ORDER_ID) {
            this.ORDER_ID = ORDER_ID;
        }

        public String getPACKAGE_TYPE() {
            return PACKAGE_TYPE;
        }

        public void setPACKAGE_TYPE(String PACKAGE_TYPE) {
            this.PACKAGE_TYPE = PACKAGE_TYPE;
        }

        public String getPERMARK() {
            return PERMARK;
        }

        public void setPERMARK(String PERMARK) {
            this.PERMARK = PERMARK;
        }

        public String getPLACE() {
            return PLACE;
        }

        public void setPLACE(String PLACE) {
            this.PLACE = PLACE;
        }

        public String getPLACENAME() {
            return PLACENAME;
        }

        public void setPLACENAME(String PLACENAME) {
            this.PLACENAME = PLACENAME;
        }

        public String getPRI() {
            return PRI;
        }

        public void setPRI(String PRI) {
            this.PRI = PRI;
        }

        public String getPROMISE_TIME() {
            return PROMISE_TIME;
        }

        public void setPROMISE_TIME(String PROMISE_TIME) {
            this.PROMISE_TIME = PROMISE_TIME;
        }

        public String getQC_STATE() {
            return QC_STATE;
        }

        public void setQC_STATE(String QC_STATE) {
            this.QC_STATE = QC_STATE;
        }

        public String getREF_ORDER_ID() {
            return REF_ORDER_ID;
        }

        public void setREF_ORDER_ID(String REF_ORDER_ID) {
            this.REF_ORDER_ID = REF_ORDER_ID;
        }

        public String getREGION_ID() {
            return REGION_ID;
        }

        public void setREGION_ID(String REGION_ID) {
            this.REGION_ID = REGION_ID;
        }

        public String getSERIALNO() {
            return SERIALNO;
        }

        public void setSERIALNO(String SERIALNO) {
            this.SERIALNO = SERIALNO;
        }

        public String getSMS_ID() {
            return SMS_ID;
        }

        public void setSMS_ID(String SMS_ID) {
            this.SMS_ID = SMS_ID;
        }

        public String getSM_NO() {
            return SM_NO;
        }

        public void setSM_NO(String SM_NO) {
            this.SM_NO = SM_NO;
        }

        public String getSM_STATE() {
            return SM_STATE;
        }

        public void setSM_STATE(String SM_STATE) {
            this.SM_STATE = SM_STATE;
        }

        public String getSTAFF_ID() {
            return STAFF_ID;
        }

        public void setSTAFF_ID(String STAFF_ID) {
            this.STAFF_ID = STAFF_ID;
        }

        public String getSTANDBY1() {
            return STANDBY1;
        }

        public void setSTANDBY1(String STANDBY1) {
            this.STANDBY1 = STANDBY1;
        }

        public String getSTANDBY2() {
            return STANDBY2;
        }

        public void setSTANDBY2(String STANDBY2) {
            this.STANDBY2 = STANDBY2;
        }

        public String getSTANDBY3() {
            return STANDBY3;
        }

        public void setSTANDBY3(String STANDBY3) {
            this.STANDBY3 = STANDBY3;
        }

        public String getSTANDBY4() {
            return STANDBY4;
        }

        public void setSTANDBY4(String STANDBY4) {
            this.STANDBY4 = STANDBY4;
        }

        public String getSTANDBY5() {
            return STANDBY5;
        }

        public void setSTANDBY5(String STANDBY5) {
            this.STANDBY5 = STANDBY5;
        }

        public String getSTBATTRS() {
            return STBATTRS;
        }

        public void setSTBATTRS(String STBATTRS) {
            this.STBATTRS = STBATTRS;
        }

        public String getSTB_COMPANY() {
            return STB_COMPANY;
        }

        public void setSTB_COMPANY(String STB_COMPANY) {
            this.STB_COMPANY = STB_COMPANY;
        }

        public String getSTB_NO() {
            return STB_NO;
        }

        public void setSTB_NO(String STB_NO) {
            this.STB_NO = STB_NO;
        }

        public String getSTYPE() {
            return STYPE;
        }

        public void setSTYPE(String STYPE) {
            this.STYPE = STYPE;
        }

        public String getSUBNUM() {
            return SUBNUM;
        }

        public void setSUBNUM(String SUBNUM) {
            this.SUBNUM = SUBNUM;
        }

        public String getSUBSETTYPE() {
            return SUBSETTYPE;
        }

        public void setSUBSETTYPE(String SUBSETTYPE) {
            this.SUBSETTYPE = SUBSETTYPE;
        }

        public String getSUBSETTYPENAME() {
            return SUBSETTYPENAME;
        }

        public void setSUBSETTYPENAME(String SUBSETTYPENAME) {
            this.SUBSETTYPENAME = SUBSETTYPENAME;
        }

        public String getSUPERVISE_REASON() {
            return SUPERVISE_REASON;
        }

        public void setSUPERVISE_REASON(String SUPERVISE_REASON) {
            this.SUPERVISE_REASON = SUPERVISE_REASON;
        }

        public String getTALKTIME() {
            return TALKTIME;
        }

        public void setTALKTIME(String TALKTIME) {
            this.TALKTIME = TALKTIME;
        }

        public String getTERMINATOR_TYPE() {
            return TERMINATOR_TYPE;
        }

        public void setTERMINATOR_TYPE(String TERMINATOR_TYPE) {
            this.TERMINATOR_TYPE = TERMINATOR_TYPE;
        }

        public String getUNIT_CODE() {
            return UNIT_CODE;
        }

        public void setUNIT_CODE(String UNIT_CODE) {
            this.UNIT_CODE = UNIT_CODE;
        }

        public String getUNIT_NAME() {
            return UNIT_NAME;
        }

        public void setUNIT_NAME(String UNIT_NAME) {
            this.UNIT_NAME = UNIT_NAME;
        }

        public String getURGE_COUNT() {
            return URGE_COUNT;
        }

        public void setURGE_COUNT(String URGE_COUNT) {
            this.URGE_COUNT = URGE_COUNT;
        }

        public String getUSERKIND() {
            return USERKIND;
        }

        public void setUSERKIND(String USERKIND) {
            this.USERKIND = USERKIND;
        }

        public String getUSERKINDNAME() {
            return USERKINDNAME;
        }

        public void setUSERKINDNAME(String USERKINDNAME) {
            this.USERKINDNAME = USERKINDNAME;
        }

        public String getWFRECID() {
            return WFRECID;
        }

        public void setWFRECID(String WFRECID) {
            this.WFRECID = WFRECID;
        }

        public String getWORKTYPE() {
            return WORKTYPE;
        }

        public void setWORKTYPE(String WORKTYPE) {
            this.WORKTYPE = WORKTYPE;
        }

        public String getZIP() {
            return ZIP;
        }

        public void setZIP(String ZIP) {
            this.ZIP = ZIP;
        }
    }
}