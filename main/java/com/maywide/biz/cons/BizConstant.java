package com.maywide.biz.cons;


public interface BizConstant {
    interface BizOpcode {
        /** 业务操作代码:BIZ_ASYNC_USER_NEW-报装 */
        String BIZ_ASYNC_USER_NEW = "BIZ_ASYNC_USER_NEW";
        /** 业务操作代码:BIZ_ASYNC_USER_UPG-升级 */
        String BIZ_ASYNC_USER_UPG = "BIZ_ASYNC_USER_UPG";
        /** 业务操作代码:BIZ_TEMPOPEN-用户点通 */
        String BIZ_TEMPOPEN = "BIZ_TEMPOPEN";
        /** 业务操作代码:BIZ_FRESHAUTH-刷新授权 */
        String BIZ_FRESHAUTH = "BIZ_REFLESH_AUTH";
        /** 业务操作代码:BIZ_FRESHAUTH-二次刷新授权 */
        String BIZ_FRESHPBN = "BIZ_FRESHPBN";
        /** 业务操作代码:BIZ_BIND_BANK-绑定银行卡 */
        String BIZ_BIND_BANK = "BIZ_BIND_BANK";
        /** 业务操作代码:BIZ_PRD_ORDER-产品订购 */
        String BIZ_PRD_ORDER = "BIZ_PRD_ORDER";
        /** 业务操作代码:BIZ_PRD_ORDER-产品退订 */
        String BIZ_PRD_CANCEL = "BIZ_PRD_CANCEL";
        /** 业务操作代码:BIZ_PRD_ORDER-产品转购 */
        String BIZ_PRD_TRANS = "BIZ_PRD_TRANS";
        /** 业务操作代码:BIZ_ROLL_BACK-业务回退 */
        String BIZ_ROLL_BACK = "BIZ_ROLL_BACK";
        /** 业务操作代码:BIZ_CHL_RESTART-开机业务 */
        String BIZ_CHL_RESTART = "BIZ_CHL_RESTART";
        /** 业务操作代码:BIZ_DEV_CHANGE-设备更换 */
        String BIZ_DEV_CHANGE = "BIZ_DEV_CHANGE";
        /** 业务操作代码:BIZ_ORDERDEAL-失败订单处理提交 */
        String BIZ_ORDERDEAL = "BIZ_ORDERDEAL";
        /** 业务操作代码:-终端销售下单 */
        String BIZ_SALES_TERMINAL = "BIZ_SALES_TERMINAL";
        
        
        
        /** (大连)业务操作代码:BIZ_UP_NOTPAIDFEES-确认未收款 */
        String BIZ_UP_NOTPAIDFEES = "BIZ_UP_NOTPAIDFEES";
        /** (大连)业务操作代码:BIZ_UP_CARD_RELATION-修改主副机关系 */
        String BIZ_UP_CARD_RELATION = "BIZ_UP_CARD_RELATION";
        /** (大连)业务操作代码:BIZ_VICEFEEKIND_CHG-副机优惠收费类型变更 */
        String BIZ_VICEFEEKIND_CHG = "BIZ_VICEFEEKIND_CHG";
        
        /**实时开户**/
        String BIZ_USER_NEW = "BIZ_USER_NEW";
        /**录入销售单**/
        String BIZ_BUSSINESS_ORDER = "BIZ_BUSSINESS_ORDER";
        /**更改UE标识**/
        String BIZ_USERUE_CHANGE = "BIZ_USERUE_CHANGE";
        /**预授权**/
        String AUTH_PREAUTHPRDS = "AUTH_PREAUTHPRDS";
        /**指定到期停**/
        String BIZ_SET_STOPTIME = "BIZ_SET_STOPTIME";
        /**杂项收费**/
        String BIZ_OTHERFEE = "BIZ_OTHERFEE";
        /**接单**/
        String OSS_ORDERS = "OSS_ORDERS";
        /**回单**/
        String OSS_RECEIPT = "OSS_RECEIPT";
        /**设备分配**/
        String OSS_DEV_ALLOCATE = "OSS_DEV_ALLOCATE";
        /**转单**/
        String OSS_RECEIPT_TURN = "OSS_RECEIPT_TURN";
        /**豁免**/
        String OSS_EXEMPTION = "OSS_EXEMPTION";
        /**延期**/
        String OSS_DELAY = "OSS_DELAY";
        
        String BIZ_PARTS_SALES = "BIZ_PARTS_SALES";
        /**开票**/
        String BIZ_ELEC_SALES = "BIZ_ELEC_SALES";
        /**开票**/
        String WAIT_ELEC_SALES = "WAIT_ELEC_SALES";
        /**VLAN号录入**/
        String BIZ_VLAN_NUM = "BIZ_VLAN_NUM";
        /**使用卡券**/
        String CRYPT_TICKEY_USER = "CRYPT_TICKEY_USER";
        
        String BIZ_FRESHVLAN = "BIZ_FRESHVLAN";
    }

    // --业务操作码 end

    // --接口处理类型 begin
    interface DealCustMarketingDealtype {
        /** 客户营销处理类型:0-转派 */
        String TRANS = "0";
        /** 客户营销处理类型:1-接单 */
        String RECEIVE = "1";
        /** 客户营销处理类型:3-处理 */
        String DEAL = "3";
        /** 客户营销处理类型:2-保存结果 */
        String SAVE = "2";

    }

    interface ApplyInstallDealtype {
        /** 报装类型:0-报装 */
        String USER_NEW = "0";
        /** 业务轨迹操作类型:1-升级 */
        String USER_UPG = "1";
    }

    // --接口处理类型 end

    // --表字段枚举参数 begin
    interface BizGridObjObjtype {
        /** 网格对象类型:0-片区型 (管理网格)*/
        String PATCH = "0";
        /** 网格对象类型:1-网格型 (片区网格)*/
        String GRID = "1";
        /** 网格对象类型:2-地址型(地址网格) */
        String ADDR = "2";
    }

    interface BizMarketBatchStatus {
        /** 客户营销批次状态:0-无效 */
        String INVALID = "0";
        /** 客户营销批次状态:1-有效 */
        String VALID = "1";
    }

    interface BizCustMarketingDealstatus {
        /** 客户营销处理状态:0-初始 */
        String INIT = "0";
        /** 客户营销处理状态:1-已接单 */
        String RECEIVED = "1";
        /** 客户营销处理状态:3-处理中 */
        String DEALING = "3";
        /** 客户营销处理状态:2-结束 */
        String DOWN = "2";

    }
    
    interface BizWeixinPaystatus {
    	/** 0-支付失败 */
        String FAILED = "0";
        /** 1-支付成功 */
        String SUCCESS = "1";
        /** 2-退款 */
        String REFUND = "2";
        /** 3-退款失败 */
        String REFUND_FAILED = "3";

    }

    interface BizCustMarketingPri {
        /** 客户营销优先级:H-高 */
        String HIGH = "H";
        /** 客户营销优先级:M-中 */
        String MID = "M";
        /** 客户营销优先级:L-低 */
        String LOW = "L";

    }

    interface BizCustMarketingResult {
        /** 客户营销处理结果:0-失败 */
        String FAIL = "0";
        /** 客户营销处理结果:1-成功 */
        String SUCCESS = "1";

    }

    interface BizTraceOptype {
        /** 业务轨迹操作类型:Q-查询 */
        String QUERY = "Q";
        /** 业务轨迹操作类型:S-数据提交 */
        String SUBMIT = "S";
    }

    interface BizCustorderOrderstatus {
        /** 客户订单状态:INIT-初始状态 */
        String INIT = "INIT";
        /** 客户订单状态:SYNC-已同步到BOSS */
        String SYNC = "SYNC";
        /** 客户订单状态:CANCEL-已取消 */
        String CANCEL = "CANCEL";
        /** 客户订单状态:LOCK-已锁定 */
        String LOCK = "LOCK";
        /** 客户订单状态:ROLLBACK-BOSS已回退 */
        String ROLLBACK = "ROLLBACK";
    }

    interface BizCustorderSyncmode {
        /** 客户订单同步方式:SYNC-实时调用 */
        String SYNC = "SYNC";
        /** 客户订单同步方式:ASYNC-异步 */
        String ASYNC = "ASYNC";
    }

    interface BizApplyProductOstatus {
        /** 申请产品动作:0-订购 */
        String ORDER = "0";
        /** 申请产品动作:3-退订 */
        String CANCEL = "3";
    }

    interface PrdSalespkgKnowObjtype {
        /** 营销方案知识库对象类型:0-产品 */
        String PRD = "0";
        /** 营销方案知识库对象类型:1-营销方案 */
        String SALESPKG = "1";
        /** 商品对象类型:1-商品 */
        String GOODS = "3";
        /** 商品小类对象类型:商品 */
        String GOODS_TYPE = "4";
    }
    
    interface PrdSalespkgKnowObjtypeName{
    	/** 营销方案知识库对象类型:0-产品 */
        String PRD = "产品";
        /** 营销方案知识库对象类型:1-营销方案 */
        String SALESPKG = "营销方案";
        /** 商品对象类型:1-商品 */
        String GOODS = "商品";
        /** 商品对象类型:1-商品小类 */
        String GOODS_TYPE = "商品小类";
    }

    interface SalespkgOptionflag {
        /** 营销方案可选标识:0-必选 */
        String REQUIRED = "0";
        /** 营销方案可选标识:1-可选默认已选 */
        String OPTIONAL_SELECTED = "1";
        /** 营销方案可选标识:2-可选默认未选 */
        String OPTIONAL_NO_SELECTED = "2";
    }

    /**
     * 渠道订单状态
     * 
     */
    public interface PORTAL_ORDER_STATUS {
        /** 未支付 **/
        public final static String PORTAL_ORDER_STATUS_NOPAY = "0";
        /** 逻辑删除 **/
        public final static String PORTAL_ORDER_STATUS_LOGICDEL = "1";
        /** 已支付 **/
        public final static String PORTAL_ORDER_STATUS_PAYED = "2";
        /** 已过期 **/
        public final static String PORTAL_ORDER_STATUS_OVERDUE = "3";
        /** BOSS支付 **/
        public final static String PORTAL_ORDER_STATUS_BOSSPAD = "4";
        /**统一支付平台已支付**/
        public final static String PORTAL_ORDER_STATUS_UNIFY = "5";
        /**大连支付中心已支付**/
        public final static String PORTAL_ORDER_STATUS_ELECTION = "6";
        /** BOSS已回退 **/
        public final static String PORTAL_ORDER_STATUS_BOSS_ROLLBACK = "10";
        
        public final static String PORTAL_ORDER_STATUS_PLATM_FAIT = "12";
    }
    
    public interface CUSTBIZ_ORDER_STATUS{
    	String ORDER_ADD_INIT = "1"; //已添加到订单池
    	String ORDER_FINISH = "2";//订单已完成分配
    }

    /**
     * 渠道订单类型
     * 
     */
    public interface PORTAL_ORDER_ORDERTYPE {
        /** 0:订购产品 **/
        public final static String PORTAL_ORDER_TYPE_PRD = "0";
        /** 1:订购营销方案 **/
        public final static String PORTAL_ORDER_TYPE_PKG = "1";
        /** 2:账本充值 **/
        public final static String PORTAL_ORDER_TYPE_FEEBOOK = "2";
        /** 3:缴纳欠费 **/
        public final static String PORTAL_ORDER_TYPE_PAYBILL = "3";
        /** 4:产品缴费 **/
        public final static String PORTAL_ORDER_TYPE_PRD_FEEBOOK = "4";
    }

    /**
     * 渠道订单支付方式
     * 
     */
    public interface PORTAL_ORDER_PAYWAY {
        /** boss账本支付 **/
        public final static String PORTAL_ORDER_PAYWAY_FEEBOOK = "00";
        /** boss集团账本支付 **/
        public final static String PORTAL_ORDER_PAYWAY_GRPFEEBOOK = "01";
        /** 广电银行支付 **/
        public final static String PORTAL_ORDER_PAYWAY_BANK = "11";
        /** 银行卡无卡支付 **/
        public final static String PORTAL_ORDER_PAYWAY_NOCARD = "22";
        /** 第三方支付 **/
        public final static String PORTAL_ORDER_PAYWAY_THIRDPAY = "33";
    }

    /**
     * 支付编码
     * 
     */
    public interface PORTAL_ORDER_PAYCODE {
        /** 其它支付 -- 支付宝支付 **/
        public final static String OTHER_ALIPAY = "040400";
        /** 其它支付 -- 现金支付 **/
        public final static String OTHER_CASH = "041100";
        /** 其它支付 -- POS机支付 **/
        public final static String OTHER_POS = "041200";
        /** 其它支付 -- 支票支付 **/
        public final static String OTHER_CHEQUE = "041300";
    }

    interface CATALOG_CONDITION_TYPE {
        /** 产品目录适用条件类型:0-指定操作员 */
        String OPERATOR = "0";
        /** 产品目录适用条件类型:1-指定部门 */
        String DEPT = "1";
        /**产品目录适用条件类型:2-指定操作类型**/
        String OPCODE = "2";
    }

    // --表字段枚举参数 end

    // --其它常量 begin
    /**
     * boss接口-服务
     * 
     */
    public interface BossInterfaceService {
        /** boss接口-服务:查询用户信息接口(所有用户) ***/
        public final static String QUE_SERVSTINFO = "QUE_SERVSTINFO";
        /** boss接口-服务:查询客户信息 ***/
        public final static String QUE_CUSTINFO = "QUE_CUSTINFO";
        /** boss接口-服务:查询用户信息 ***/
        public final static String QUE_USERINFO = "QUE_USERINFO";
        /** boss接口-服务:查询点通方案 ***/
        public final static String QUE_TEMPOPENPLAN = "QUE_TEMPOPEN";
        /** boss接口-服务:查询客户银行 ***/
        public final static String QUE_CUSTBANK = "QUE_CUSTBANK";
        /** boss接口-服务:查询地址 ***/
        public final static String QUE_RESHOUSE = "QUE_RESHOUSE";
        /** boss接口-服务:查询用户产品接口 ***/
        public final static String QUE_USERPRDINFO = "QUE_USERPRDINFO";
        /** boss接口-服务:查询用户有效营销方案接口 ***/
        public final static String QUE_USERPKGINFO = "QUE_USERPKGINFO";
        /** boss接口-服务:查询欠费记录接口 ***/
        public final static String QUE_ARREARDET = "QUE_ARREARDET";
        /** boss接口-服务:查询智能卡产品信息 ***/
        public final static String QUE_SERVPRDINFO = "QUE_USERPRDINFO";
        /** boss接口-服务:查询订单接口 */
        public final static String QUE_ORDERINFO = "QUE_ORDERINFO";
        /** boss接口-服务:查询消费记录接口 */
        public final static String QUE_SERVPRDDET = "QUE_SERVPRDDET";
        /** boss接口-查询:获取异步订单处理结果*/
        public final static String QUE_ASYNC_ORDERRESULT = "QUE_ASYNC_ORDERRESULT";
        /** boss接口-查询:获取业务订单一次性费用*/
        public final static String QUE_BIZ_SERVFEES = "QUE_BIZ_SERVFEES"; 
        /** boss接口-查询:查询客户住宅信息 */
        public final static String QUE_CUSTADDR = "QUE_CUSTADDR"; 

        /** boss接口-服务:创建客户 ***/
        public final static String BIZ_CREATECUSTINF = "BIZ_CREATECUSTINF"; //已整理
        /** boss接口-服务:用户点通 ***/
        public final static String BIZ_TEMPOPEN = "BIZ_TEMPOPEN";
        /** boss接口-服务:刷新授权 ***/
        public final static String BIZ_FRESHAUTH = "BIZ_FRESHAUTH";
        /** boss接口-服务:绑定银行卡 ***/
        public final static String BIZ_BINDBANK = "BIZ_BINDBANK";
        /** boss接口-服务:产品订购鉴权 ***/
        public final static String BIZ_PREPROCESS = "BIZ_PREPROCESS";
        /** boss接口-服务:缴纳欠费接口 ***/
        public final static String BIZ_FEEIN = "BIZ_FEEIN";
        /** boss接口-服务:订单确认接口 ***/
        public final static String BIZ_ORDERCOMMIT = "BIZ_ORDERCOMMIT";
        /** boss接口-服务:修改操作员密码 ***/
        public final static String BIZ_CHGOPERATORPWD = "BIZ_CHG_OPERATOR_PWD";
        /** boss接口-服务:删除订单接口 */
        public final static String BIZ_DELORDER = "BIZ_DELORDER"; 
        /** boss接口-服务:修改客户信息*/
        public final static String BIZ_EDIT_CUST = "BIZ_EDIT_CUST";
        /** boss接口-服务:查询公众客户信息*/
        public final static String QUE_CUSTPUBINF = "QUE_CUSTPUBINF";  
        /** boss接口-服务:查询账户余额*/
        public final static String QUE_FEEBOOKINFO = "QUE_FEEBOOKINFO";
        
        /*设备更换业务新增*/
        /** boss接口-服务:查询客户设备信息*/
        public final static String QUE_CUSTDEVINFO = "QUE_CUSTDEVINFO";    
        /** boss接口-服务:查询配件信息*/
        public final static String QUE_FITTING_INFO = "QUE_FITTING_INFO";   
        /** boss接口-服务:设备销售校验*/
        public final static String CHK_NEWDEV = "CHK_NEWDEV";   
        /** boss接口-服务:设备更换鉴权*/
        public final static String BIZ_SAVEDEVTMP = "BIZ_SAVEDEVTMP";  
        /** boss接口-服务:设备更换确认接口*/
        public final static String BIZ_CHGDEV = "BIZ_CHGDEV";    
        

        /** boss接口-服务:业务异动查询*/
        public final static String QUE_BIZLOGBYPAGE = "QUE_BIZLOGBYPAGE";  //已整理

        /** boss接口-服务:业务异动查询*/
        public final static String QUE_CUST_MARKETINFO = "QUE_CUST_MARKETINFO"; 
        /** boss接口-服务:工单同步接口*/
        public final static String BIZ_ASYNC_ORDER = "BIZ_ASYNC_ORDER";  
       

        /** boss接口-服务:2.6.1.4.查询即将到期用户资料*/
        public final static String QUE_SERV_EXPIRE = "QUE_SERV_EXPIRE"; 
        /** boss接口-服务:2.6.1.3.获取即将到期用户数量*/
        public final static String QUE_SERV_EXPIRENUM = "QUE_SERV_EXPIRENUM";
        /** boss接口-服务3.1.107. 查询宽带信息接口*/
        public final static String QUE_CMASSIST = "QUE_CMASSIST";

        /***账户充值接口**/
        String BIZ_CHARGING = "BIZ_CHARGING";//未配置
        
        
        /** 快速缴费接口(按地址缴费) **/
        String BIZ_FEEIN_AND_ORDER = "BIZ_FEEIN_AND_ORDER";
        
        /** 获取客户未开票信息 **/
        String QUE_UNPRINT_INVINFO = "QUE_UNPRINT_INVINFO";
        
        /**打印受理单**/
        String BIZ_PRINT_ACCINFO = "BIZ_PRINT_ACCINFO";
        
        /**打印发票**/
        String BIZ_PRINT_INVINFO = 	"BIZ_PRINT_INVINFO";
        
        /***获取下一个发票号**/
        String GET_OPER_NEXT_INVO = "GETOPERNEXTINVO";
        
        /**查询发票信息接口**/
        String QUE_INVINFO = "QUE_INVINFO";
        
        /**实时出账接口**/
        String GEN_REALTIMEARREAR = "GEN_REALTIMEARREAR";
        
        /**业务回退**/
        String DO_ROLLBACK = "DO_ROLLBACK";
        
        /**查询停机用户列表**/
        String QUE_STOPUSER = "QUE_STOPUSER";
        
        /**开机接口**/
        String BIZ_CHL_RESTART = "BIZ_CHL_RESTART";     
        
        /**修改宽带密码接口**/
        String BIZ_UPDATE_CM_PWD = "BIZ_UPDATE_CM_PWD";
        
        /**重新提交失败的异步订单接口***/
        String BIZ_ORDERRECOM = "BIZ_ORDERRECOM";
        
        /**查询异步订单信息接口(新)***/
        String QUE_ASYNC_ORDERINFO = "QUE_ASYNC_ORDERINFO";
        
        /**失败订单处理提交接口***/
        String BIZ_ORDERDEAL = "BIZ_ORDERDEAL";
        
        /**实时开户接口**/
        String BIZ_CHL_INSTALL = "BIZ_CHL_INSTALL";
        /**开户确认接口**/
        String BIZ_INSTALL_COMMIT = "BIZ_INSTALL_COMMIT";
        /**查询未收款确认订单表**/
        String QUE_CONFIRMPAY = "QUE_CONFIRMPAY";
        /**收款确认接口**/
        String BIZ_CONFIRMPAY = "BIZ_CONFIRMPAY";
        /**查询是否在报盘中接口**/
        String CHECK_BIZACCEPT = "CHECK_BIZACCEPT";
        /**销售单录入接口**/
        String BIZ_BUSSINESS_ORDER = "BIZ_BUSSINESS_ORDER";
        /**销售单查询接口**/
        String QUE_BIZ_BUSSINESS = "QUE_BIZ_BUSSINESS";
        /**更改UE标识接口**/
        String BIZ_USERUE_CHANGE = "BIZ_USERUE_CHANGE";
        /**商业用户查询接口**/
        String QUE_COMM_CUST = "QUE_COMM_CUST";
        
        String QUE_PREAUTHPRDS = "QUE_PREAUTHPRDS";
        
        String AUTH_PREAUTHPRDS = "AUTH_PREAUTHPRDS";
        /**预授权日志查询接口**/
        String QUE_AUTH_PREAUTHPRDS = "QUE_AUTH_PREAUTHPRDS";
        
        String QUE_PAPERLESS_CONTENT = "QUE_PAPERLESS_CONTENT";
        /**指定到期停接口**/
        String BIZ_STOP_SALES = "BIZ_STOP_SALES";
        
        /**杂项收费查询接口**/
        String QUE_OTHER_FEE = "QUE_OTHER_FEE";
        /**杂项收费接口**/
        String BIZ_OTHER_FEE_IN = "BIZ_OTHER_FEE_IN";
        /**查询装维工单接口**/
        String QUE_SERVORDER = "QUE_SERVORDER";
        /**查询竣工工单接口**/
        String QUE_SERVORDER_LOG = "QUE_SERVORDER_LOG";
        /**回单接口**/
        String BIZ_DEAL_ORDER = "BIZ_DEAL_ORDER";
        /**转单接口**/
        String BIZ_CHANGE_ORDER = "BIZ_CHANGE_ORDER";
        /**延期接口**/
        String BIZ_DELAY_ORDER = "BIZ_DELAY_ORDER";
        /**工单委托记录查询接口**/
        String QUE_GRID_APPLY = "QUE_GRID_APPLY";
        /**工单委托申请接口**/
        String BIZ_GRID_APPLY = "BIZ_GRID_APPLY";
        /**豁免工单查询接口**/
        String QUE_EXEMPT_APPLY = "QUE_EXEMPT_APPLY";
        /**工单豁免申请接口**/
        String BIZ_EXEMPT_APPLY = "BIZ_EXEMPT_APPLY";
        /**工单豁免审批接口**/
        String BIZ_EXEMPT_DEAL = "BIZ_EXEMPT_DEAL";
        /**同步客户属性扩展接口**/
        String CUST_PT_EXPINFO = "CUST_PT_EXPINFO";
        /**工单统计查询接口**/
        String QUE_ORDER_TOTAL = "QUE_ORDER_TOTAL";
        /**装维员备注填写接口**/
        String BIZ_ORDER_OPERMEMO = "BIZ_ORDER_OPERMEMO";
        
        /**配件查询接口**/
        String QUE_FITTING_INFO_NEW = "QUE_FITTING_INFO_NEW";
        /**配件销售接口**/
        String BIZ_PARTS_SALES = "BIZ_PARTS_SALES";
        /**配件销售确认接口**/
        String BIZ_SALES_COMMIT = "BIZ_SALES_COMMIT";
        /**网格生成电子发票接口（异步）**/
        String WAIT_ELEC_SALES = "WAIT_ELEC_SALES";
        /**电子发票开票接口**/
        String BIZ_ELEC_SALES = "BIZ_ELEC_SALES";
        /**VLAN号录入接口**/
        String BIZ_VLAN_NUM = "BIZ_VLAN_NUM";
        /**BOSS密码鉴权接口**/
        String CRYPT_TICKET_VERIFY = "CRYPT_TICKET_VERIFY";
        /**查询产品密码卡状态**/
        String QUE_CRYPT_TICKET = "QUE_CRYPT_TICKET";
        /**密码使用接口**/
        String CRYPT_TICKET_USE = "CRYPT_TICKET_USE";
        /**查询宽带账户在线情况**/
        String QUE_USER_ONLINE = "QUE_USER_ONLINE";
        /**频道商品查询接口**/
        String QUE_PRDSALES_CHANNELS = "QUE_PRDSALES_CHANNELS";
        /**快速续费产品列表**/
        String QUE_FEEIN_SERV = "QUE_FEEIN_SERV";
        
        String QUE_CMMC_ACCTNO = "QUE_CMCC_ACCTNO";
        
        String LOCK_CMCC_ACCTNO = "LOCK_CMCC_ACCTNO";
        
        String UNLOCK_CMCC_ACCTNO = "UNLOCK_CMCC_ACCTNO"; 
        
        String QUE_GRP_AREA_CTL = "QUE_GRP_AREA_CTL";
        
        /**获取设备产品列表接口***/
        String QUE_DEVPRDINFO = "QUE_DEVPRDINFO";
        
        /**二次刷新授权**/
        String BIZ_FRESHPBN = "BIZ_FRESHPBN";
        
        /**重新分配**/
        String BIZ_FRESHVLAN = "BIZ_FRESHVLAN";
        
        /**查询订单对应的返还计划(网格新增)**/
        String QUE_ORDER_PKGINFO = "QUE_ORDER_PKGINFO";
        
        String QUE_DEV_BY_STB = "QUE_DEV_BY_STB"; 
        
        /**根据机顶盒号和智能卡号查询出一体机机顶盒账号  --番禺**/
        String QUE_INTEGRATED = "QUE_INTEGRATED"; 
        
        
        /**预报装设备分配查询接口**/
        String WFL_QUE_EQUIPINFO = "WFL_QUE_EQUIPINFO"; 
        
        /**预报装设备分配接口**/
        String WFL_ASSIGN_EQUIPINFO = "WFL_ASSIGN_EQUIPINFO"; 
        
        /**VLAN下发ACS查询 (用于网口网管排障查询)**/
        String SEQ_VLAN_LOG = "SEQ_VLAN_LOG";
        
        /**1.4设备分配明细查询接口*/
        String WFL_QUE_EQUIPDETINFO = "WFL_QUE_EQUIPDETINFO";
        
        /**1.3录入销售单无纸化通过这个接口返回无纸化信息*/
        String QUE_SALESORDER_CONTENT = "QUE_SALESORDER_CONTENT";
        /**1.1.	终端销售查询终端信息接口*/
        String QUE_TERMINALSALES_PRICE = "QUE_TERMINALSALES_PRICE";
        /**1.2.	终端销售下单接口*/
        String BIZ_SALES_TERMINAL = "BIZ_SALES_TERMINAL";
        
        /**业务工单设备分配提交接口**/
        String WFL_EQUIPINFO_SUBMIT = "WFL_EQUIPINFO_SUBMIT";
        /**设备库存查询**/
        String QUE_DEVSTORE = "QUE_DEVSTORE";

        /**组网业务订单池查询预受理工单**/
        String QUE_PREACCEPT = "QUE_PREACCEPT";
        /**组网业务订单池处理工单**/
        String DEAL_PREACCEPT = "DEAL_PREACCEPT";
        /**资源销售获取配件绑定列表**/
        String QUE_BINDSALES = "QUE_BINDSALES";

        /**查询设备loid及vlan信息接口**/
        String QUE_LOID = "QUE_LOID";


    }
    
    
    public interface ServerCityBossInterface{
    	//** boss接口-服务:查询智能卡产品信息 ***/
        public final static String QUE_SERVPRDINFO = "QUE_SERVPRDINFOWT";
        /** boss接口-服务:查询欠费记录接口 ***/
        public final static String QUE_ARREARDET = "QUE_ARREARDETWG";
        /** boss接口-服务:查询订单接口 */
        public final static String QUE_ORDERINFO = "QUE_ORDERINFOWG";
        /** boss接口-服务:查询客户信息 ***/
        public final static String QUE_CUSTINFO = "QUE_CUSTINFOFORWT";
       /** boss接口-服务:业务异动查询*/
        public final static String QUE_BIZLOGBYPAGE = "QUE_BIZLOGBYPAGEWG";
        /** boss接口-服务:查询点通方案 ***/
        public final static String QUE_TEMPOPENPLAN = "QUE_TEMPOPENWG";
        /** boss接口-服务:查询用户信息接口(所有用户) ***/
        public final static String QUE_SERVSTINFO = "QUE_SERVSTINFOWG";
        /** boss接口-服务:查询用户产品接口 ***/
        public final static String QUE_USERPRDINFO = "QUE_USERPRDINFOWG";
        /** boss接口-服务:查询用户有效营销方案接口 ***/
        public final static String QUE_USERPKGINFO = "QUE_USERPKGINFOWG";
        /** boss接口-服务:产品订购鉴权 ***/
        public final static String BIZ_PREPROCESS = "BIZ_PREPROCESSWG";
        /** boss接口-服务:订单确认接口 ***/
        public final static String BIZ_ORDERCOMMIT = "BIZ_ORDERCOMMITWG";
        /** boss接口-服务:缴纳欠费接口 ***/
        public final static String BIZ_FEEIN = "BIZ_FEEINWG";
        /** boss接口-服务:绑定银行卡 ***/
        public final static String BIZ_BINDBANK = "BIZ_BINDBANKWG";
        /** boss接口-服务:设备销售校验*/
        public final static String CHK_NEWDEV = "CHK_NEWDEVWG";   
        /** boss接口-服务:设备更换鉴权*/
        public final static String BIZ_SAVEDEVTMP = "BIZ_SAVEDEVTMPWG";
        /** boss接口-服务:设备更换确认接口*/
        public final static String BIZ_CHGDEV = "BIZ_CHGDEVWG";
        
        String BIZ_FEEIN_AND_ORDER = "BIZ_FEEIN_AND_ORDERWG";
        
        
        /**查询主副卡关系接口**/
        String QUE_CARD_RELATION = "QUE_CARD_RELATION";
        /**更改主副卡关系接口**/
        String UP_CARD_RELATION = "UP_CARD_RELATION";
        /**用户收费类型查询接口**/
        String QUE_VICEFEEKIND = "QUE_VICEFEEKIND";
        /**更改收费类型接口**/
        String BIZ_VICEFEEKIND_CHG = "BIZ_VICEFEEKIND_CHG";
        /**未收款信息查询接口**/
        String QUE_NOTPAIDFEES = "QUE_NOTPAIDFEES";
        /**未收款确认接口**/
        String UP_NOTPAIDFEES = "UP_NOTPAIDFEES";
        /**大连获取开户宽带帐号接口**/
        String BIZ_CM_GETACCTNOWG = "BIZ_CM_GETACCTNOWG";
        /**大连开户鉴权接口**/
        String BIZ_ASYNC_ORDER = "BIZ_ASYNC_ORDER";
        /**开户确认接口**/
        String BIZ_INSTALL_COMMIT = "BIZ_INSTALL_COMMIT";
        /**住宅客户信息查询接口**/
        String QUE_CUSTINFOWG = "QUE_CUSTINFOWG";
        /**住宅用户信息查询接口**/
        String QUE_USERINFOWG = "QUE_USERINFOWG";
        /**用户保有数查询接口**/
        String QUE_USERCOUNTWG = "QUE_USERCOUNTWG";
        /**设备销售报表接口**/
        String QUE_DEVSALESOPERWG = "QUE_DEVSALESOPERWG";
        /**客户确认未收款查询接口**/
        String QUE_CONFIRMFEEWG = "QUE_CONFIRMFEEWG";
        /**营业日报查询接口**/
        String QUE_WORKDAYREPORTWG = "QUE_WORKDAYREPORTWG";
        /**网格收入指标查询**/
        String GET_FEES_INFO = "GET_FEES_INFO";
        /**网格积分排名**/
        String GET_POINT_INFO = "GET_POINT_INFO";
        /**无纸化打印数据查询接口***/
        String QUE_PAPERLESS_CONTENT = "QUE_PAPERLESS_CONTENT";
        
    }
    
    /**
     * Portal 接口-服务
     */
    interface PortalInterfaceService{
    	/**查询通过 boss工号 查询portal登录账号*/
    	public static final String QUERYPORTALUSER = "queryPortalUser";
    	
    	/**修改 portal登录账号*/
    	public static final String SAVEPORTALUSER = "savePortalUser";
    }
    
    
    /**
     * 认证平台接口-服务
     * 
     */
    interface UAPInterfaceService {

        /** 认证平台接口-服务:随机码发送接口 */
        public static final String SEND_RANDOM_CODE = "UAP_sendTemplateSMS";
        /** 认证平台接口-服务:随机码验证接口 */
        public static final String AUTH_RANDOM_CODE = "UAP_authRandomCode";
        /** 认证平台接口-服务:随机码作废接口 */
        public static final String INVALID_RANDOM_CODE = "UAP_invalidRandomCode";
        // 先完成以上几个接口的对接，后面有需要再增加
        
        /** 认证平台接口-服务：短信内容服务接口**/
        public static final String UAP_sendInformInfo = "UAP_sendInformInfo";
        /** 认证平台接口-服务：短信内容服务接口**/
        public static final String UAP_SOS_SMS	= "SOS_SMS";
        
        String UAP_QUERYGDUSERINFO = "UAP_queryGDUserInfo";
    }
    
    /**
     * ywp集客商机管理接口
     */
    interface BusinessManagement{
    	/**1.2.	查询商机集客经理接口*/
    	public static final String QUE_GRPBYAREA_INFO = "QUE_GRPBYAREA_INFO";
    	/**1.3.	查询商机列表信息接口*/
    	public static final String QUE_BUSLISTINFO = "QUE_BUSLISTINFO";
    	/**1.4.	新建or修改商机信息接口*/
    	public static final String SAVE_BUSINFO = "SAVE_BUSINFO";
    	/**1.5.	查询商机轨迹列表接口*/
    	public static final String QUE_BUSTRAILLISTINFO = "QUE_BUSTRAILLISTINFO";
    	/**1.6.	查询商机跟踪信息接口*/
    	public static final String QUE_BUSTRACKINFO = "QUE_BUSTRACKINFO";
    	/**1.7.	跟踪商机接口*/
    	public static final String SAVE_BUSTRACKINFO = "SAVE_BUSTRACKINFO";
    	/**漫游控制接口*/
    	public static final String ROAM_CTRL = "ROAM_CTRL";
    }
    /**
     * 认证平台接口-操作代码
     * 
     */
    public interface UAPOpcode {
        /** 认证平台接口-操作代码:随机码发送接口 */
        final static String SEND = "B01";
        /** 认证平台接口-操作代码:随机码验证接口 */
        final static String AUTH = "B02";
        /** 认证平台接口-操作代码:随机码作废接口 */
        final static String INVAL = "B03";
    }
    
    public interface SMSTemplateCode{
    	String RAN_DOM_CODE_SMS = "randomCodeSms";
    }

    interface SysparamGcode {
        String PRV_CITY = "PRV_CITY";
        String PRV_TOWN = "PRV_TOWN";
        String CATALOG_CONDITION_TYPE = "CATALOG_CONDITION_TYPE";
        String SYS_ACCT_KIND = "SYS_ACCT_KIND";
        String SYS_BANK = "SYS_BANK";
        String SYS_CARD_TYPE = "SYS_CARD_TYPE";
        String SYS_ACCT_TYPE = "SYS_ACCT_TYPE";
        String BIZ_OPCODE="BIZ_OPCODE";//业务操作码
        String GRID_TYPE = "GRID_TYPE";//网格类型
    }

    /**
     * 规则表取参 参数
     */
    interface BizRuleParams {
    	String GOODS_BIZCODES = "GOODS_BIZCODES";//获取目录关联业务操作 规则
    	String PERMISSION_TYPE_Y = "Y";
    	String DEFAULTCITY="*";
    	String GOODS_BIZCODES_ALL="GOODS_BIZCODES_ALL";//获取所有的业务操作
    	String SHOWALL_ADDR = "SHOWALL_ADDR";//标准地址和非标准地址都显示
    	String NOT_JUDGE_GRID = "NOT_JUDGE_GRID";//不判断网格权限
    }
    /**
     * 银很账号对公标志
     */
    interface SysAcctType {
        /** 对公标志:对私 */
        public static final String SYS_ACCT_TYPE_PRIVATE = "0";
    }
    
    interface CustType {
        /** 客户类型:个人 */
        public static final String CUST_TYPE_PERSON = "0";
        /** 客户类型:集团 */
        public static final String CUST_TYPE_GROUP = "1";
        /** 客户类型:商业 */
        public static final String CUST_TYPE_BUSINESS = "2";
        /** 客户类型:专线 */
        public static final String CUST_TYPE_CMST = "3";
    }

    interface CustSubType {
        /** 客户子类型:普通用户 */
        public static final String CUST_SUB_TYPE_NORMAL = "00";
    }

    interface CustSecType {
        /** 客户二级子类型:公众用户 */
        public static final String SYS_CUST_SEC_TYPE_PUB = "0001";
    }

    /**
     * 缴费方式
     * 
     */
    public interface SysFeeway {
        /** 缴费方式:银行托收 ***/
        public final static String BANK = "1";
        /** 缴费方式:前台缴费 **/
        public final static String MONEY = "0";

    }

    /**
     * 支付方式
     * 
     */
    public interface SysPayway {
        /** 支付方式:现金 ***/
        public final static String SYS_PAYWAY_CASH = "0";
        /** 支付方式:支票 **/
        public final static String SYS_PAYWAY_CHEQUE = "1";
        /** 支付方式:POS支付 **/
        public final static String SYS_PAYWAY_POS = "2";
        /** 统一支付平台 **/
        public final static String SYS_PAYWAY_UNIFY = "U";
        
        public final static String SYS_PAYWAY_UNIFY2= "33";
        /** 统一支付平台 (大连)**/
        public final static String SYS_PAYWAY_ELECTIONS = "E";

    }

    /**
     * 授权操作类型
     * 
     */
    public interface SysFreshKind {
        /** 授权操作类型:刷新授权 */
        public static final String FREASH = "0";
        /** 授权操作类型:取消授权 */
        public static final String CANCELFREASH = "2";
        /** 授权操作类型:重发机卡配对指令 */
        public static final String REMATCH = "3";
        /** 授权操作类型:清除PIN码 */
        public static final String CLEARPIN = "4";
        /** 授权操作类型:刷新VOD授权 */
        public static final String VODFRESH = "5";
        /** 授权操作类型:刷新sodate授权 */
        public static final String SODATE = "6";
        /** 授权操作类型:刷新socode授权 */
        public static final String SOCODE = "7";
        /** 授权操作类型:设置区域码 */
        public static final String SOAREACODE = "8";

    }

    /**
     * 退订类型
     * 
     */
    public interface BizEjectType {
        /** 退订类型:退订产品 ***/
        public final static String PRD = "0";
        /** 退订类型:退订营销方案 **/
        public final static String PKG = "2";

    }

    /**
     * 排序方式
     * 
     */
    public interface SortType {
        /** 排序方式:升序 ***/
        public final static String ASC = "1";
        /** 排序方式:降序 **/
        public final static String DESC = "0";

    }

    /**
     * 业务参数配置名称
     * 
     */
    public interface BizParamCfgName {
        /** 业务参数配置名称:业务参数生效级别 ***/
        public final static String BIZ_PARAM_CTL_LEVEL = "BIZ_PARAM_CTL_LEVEL";
        
        /** 业务参数配置名称:网格默认业务网格类型配置 ***/
        public final static String BIZ_GRID_TYPE_BIZTYPE_CFG = "BIZ_GRID_TYPE_BIZTYPE_CFG";
        
        /** 业务参数配置名称:网格对象类型配置 ***/
        public final static String BIZ_GRID_OBJTYPE_CFG = "BIZ_GRID_OBJTYPE_CFG";
        
        /** 业务参数配置名称:业务受理权限配置 **/
        public final static String BIZ_RIGHT_BIZACCEPT_CFG = "BIZ_RIGHT_BIZACCEPT_CFG";
        /** 业务参数配置名称:操作员体验授权次数配置 **/
        public final static String BIZ_RIGHT_TMPOPEN_NUM_CFG = "BIZ_RIGHT_TMPOPEN_NUM_CFG";
        /** 业务参数配置名称:操作员对同一方案体验授权次数配置 **/
        public final static String BIZ_RIGHT_SAMEPLAN_TMPOPEN_NUM_CFG = "BIZ_RIGHT_SAMEPLAN_TMPOPEN_NUM_CFG";
        
        /** 业务参数配置名称:产品订购参数默认参数值配置**/
        public final static String BIZ_PRD_ORDERPARAM_DEFVALUE_CFG = "BIZ_PRD_ORDERPARAM_DEFVALUE_CFG";

    }

    /**
     * 业务参数生效级别
     * 
     */
    public interface BizParamCtlLevel {
        /** 业务参数生效级别:默认级别 */
        public static final String BIZ_PARAM_CTL_LEVEL_DEFAUL = "0";
        /** 业务参数生效级别:地市 */
        public static final String BIZ_PARAM_CTL_LEVEL_CITY = "1";
        /** 业务参数生效级别:业务区 */
        public static final String BIZ_PARAM_CTL_LEVEL_AREA = "2";
        /** 业务参数生效级别:部门 */
        public static final String BIZ_PARAM_CTL_LEVEL_DEPT = "3";
        /** 业务参数生效级别:操作员 */
        public static final String BIZ_PARAM_CTL_LEVEL_OPER = "4";
        /** 业务参数生效级别:部门+操作员 */
        public static final String BIZ_PARAM_CTL_LEVEL_DEPTOPER = "5";
        /** 业务参数生效级别:外部对象 */
        public static final String BIZ_PARAM_CTL_LEVEL_OBJID = "6";
        ///** 业务参数生效级别:角色 */--角色级别的控制，可以通过把角色id做为外部对象id，转换成外部对象级别去控制
        //public static final String BIZ_PARAM_CTL_LEVEL_ROLE = "7";
    }

    /**
     * 权限
     * 
     */
    public interface RIGHTS {
        public static final String LOW = "0";
        /** 权限:权限值--中权 */
        public static final String NORMAL = "5";
        /** 权限:权限值--中高权 */
        public static final String SECOND_HIGH = "7";
        /** 权限:权限值--高权 */
        public static final String HIGH = "9";
    }

    interface Print {
        /** 通用MAC地址 */
        public static final String MAC_ALL = "00-00-00-00-00-00";

        /** 发票打印项取值类型:常量 */
        public static final String VALUE_TYPE_CONSTANT = "0";
        /** 发票打印项取值类型:入参 */
        public static final String VALUE_TYPE_PARAMS = "1";
        /** 发票打印项取值类型:SQL */
        public static final String VALUE_TYPE_SQL = "2";
    }
    
    /**
     *调查问卷的状态
     */
    interface SurveyStatus{
    	
    	/** 问卷状态：使用中*/
    	public static final String USING = "0";
    	
    	/** 问卷状态：截止 */
    	public static final String STOP = "1";
    	 	
    }
    
    /**
     * 问卷是否实名制
     */
    interface RealNameStatus{
    	/** 问卷是否实名制：是 */
    	public static final String YES = "Y";
    	
    	/** 问卷是否实名制：否 */
    	public static final String NO = "N";
    }
    
    // --其它常量 end
    interface GridType {
        public static final int      MANAGE_GRID     = 0;
        public static final int      PATCH_GRID      = 1;
        public static final int      ADDRESS_GRID    = 2;
        public static final String[] GRID_TYPE_NAMES = { "管理网格", "片区网格", "地址网格" };
        public static final String[] GRID_TYPE_SKINS = { "manageGrid", "patchGrid", "addressGrid" };
    }
    
    interface DataReportFlag1{
    	String IN_MAIN = "1"; // 在用主机用户
    	String IN_BUSINESS = "2"; //在用业务用户
    	String IN_NUMBER = "3";//在用数字用户
    	String IN_BROADBAND = "4"; // 在用宽带用户
    	String IN_INTERACTIVE = "5";//在用高清互动用户
    }
    
    interface DataReportFlag2{  //Satisfaction
    	String INSTALL_SATISFACTION = "1"; //安装满意度
    	String DEFEND_SATISFACTION = "2"; //维护满意度
    	String FAILURE_RATE = "3"; //故障率
    }
    
    public interface Platform_Service{
    	String PAY = "Pay";
    	String PAYBACKOFF = "PayBackOff";
    	String REFUND = "Refund";
    	String QUEORDER = "QueOrder";
    }
    
    public interface PAY_CLIENT_TYPE{
    	String MERCHANT_MOBILE = "05";
    }
    
    public interface DLplatform_Params{
    	String WX_NATIVE = "WX_NATIVE";
    	String ALIPAY_NATIVE = "ALIPAY_NATIVE";
    }
    
	/**
	 * 支付业务类型
	 *
	 */
	public interface PAY_BIZ_TYPE {
		String ORDER = "1";
		String CHG_DEV = "2";
	}
	
	
	/**
	 *    
	 *<p> 
	 * cmp接口路径
	 *<p>
	 * Create at 2017-3-12
	 */
	public interface CmpPath {
		String PUSH = "/push2boss/send.json";
		String CALLBACK = "/bosscback/send.json";

		String TAG_INFO = "tag/info.json";
		String CUST_LIST = "tag/custlist.json";
		
		/**活动任务数查询接口**/
		String TASK_COUNT = "cmms/taskcount.json";
		/**活动列表查询接口**/
		String ACTIVITY_LIST = "cmms/activitylist.json";
		/**网格任务列表查询接口**/
		String TASK_LIST = "cmms/tasklist.json";
		/**营销任务列表查询接口**/
		String TASK_LIST_BY_CUSTID = "cmms/tasklistByCustid.json";
		/**任务反馈接口**/
		String TASK_FEEDBACK = "cmms/taskfeedback.json";
		
		String TAG_FOR_CUSTID = "tag/tagInfoByCustid.json";
		
		String WORK_ORDER_LIST_CUSTID = "cmms/workOrderList.json";
	}
	
	public interface CmpTaskStatus{
		//未接触
		String UNTOUCH = "0";
	}
	
	/**
	 * 微厅接口路径
	 */
	public interface McrPath {
		/**TV厅二维码解析接口**/
		String TV_QRCODE_ANALYZE = "tvQrcodeAnalyze";
        /**TV厅绑定智能卡接口**/
        String WECHAT_BINDING = "wechatBinding";
	}
	
	/**
	 * OAuth接口
	 */
	public interface OAuthApi {
		/**查询APP用户接口**/
		String QUERY_APP_USER = "queryAppUser";
	}
	
	/**
	 * 问题申诉 问题处理状态
	 */
	
	public interface SYS_PLSTATE{
		String SYS_PLSTATE_SUBMIT = "0";//待提交
		String SYS_PLSTATE_DEAL = "1"; //待处理
		String SYS_PLSTATE_WAITFINISH = "2"; //待结单
		String  SYS_PLSTATE_FINISHED = "3";//已结单
	}
	
	/**
	 * 短信模板 状态
	 */
	public interface  SYS_SMSTEMP_STATU{
		String  SYS_SMSTEMP_STATU_NOSUBMIT = "0"; //未提交
		String  SYS_SMSTEMP_STATU_WAITAUDIT = "1";//待审核
		String  SYS_SMSTEMP_STATU_AUDITPASS = "2";//审核通过
		String  SYS_SMSTEMP_STATU_AUDITFAIL = "3";//审核不通过
		String  SYS_SMSTEMP_STATU_USERLESS = "4";//失效
		
	}
	
	/**
	 * 
	 *<p> 
	 * 轨迹表数据库操作类型
	 *<p>
	 */
	public interface SQL_OPER_TYPE{
		/**删除前**/
		String SQL_OPER_TYPE_1 ="1";
		
		/**插入后**/
		String SQL_OPER_TYPE_2 ="2";
		
		/**更新后**/
		String SQL_OPER_TYPE_3 ="3";
	}
	
	/**
	 * 
	 *<p> 
	 * 广告类型
	 *<p>
	 */
	public interface AD_TYPE{
		/**商品**/
		String AD_TYPE_1 = "1";
		
		/**外链接**/
		String AD_TYPE_2 = "2";
		
		/**'图片**/
		String AD_TYPE_3 = "3";
	}
	/**
	 * 广告状态
	 */
	public interface AD_STATUS{
		/**已保存**/
		String AD_STATUS_1 = "1";
		/**待审核 **/
		String AD_STATUS_2 = "2";
		/**审核通过**/
		String AD_STATUS_3 = "3";
		/**审核不通过**/
		String AD_STATUS_4 = "4";
		/**已上架**/
		String AD_STATUS_5 = "5";
		/**已下架**/
		String AD_STATUS_6 = "6";
		
		
	}

	/**
	 * 部门类型
	 */
	public interface DEP_KIND{
		/**装维部门**/
		String WORK = "3";
		/**支撑部门**/
		String SUPPORT = "C";
	}
	
	/**
	 * 部门层级
	 */
	public interface DEP_LEVEL{
		String LEVEL_2 = "2";
		String LEVEL_3 = "3";
		String LEVEL_4 = "4";
	}
	
	interface System {
        final String SysConfig = "sysconfig";
    }	
	
	interface AuthServiceCode{
		String QUE_SEQ_GRANT = "qryswplog";
		String QUE_SEQ_GRANT_LOG = "qryswpiog";
		
		
	}
	
	interface TaskModel{
		/**任务是否过期**/
		public static final String BIZ_TASK_ISEXPIRED_N = "N";//未过期
		public static final String BIZ_TASK_ISEXPIRED_Y = "Y";//过期
		
		/**任务状态**/
		public static final String BIZ_TASK_STATUS_0 = "0"; //编辑
		public static final String BIZ_TASK_STATUS_1 = "1"; //提交
		public static final String BIZ_TASK_STATUS_2 = "2"; //'已审批
		public static final String BIZ_TASK_STATUS_3 = "3"; //执行
		public static final String BIZ_TASK_STATUS_4 = "4"; //暂停
		public static final String BIZ_TASK_STATUS_5 = "5"; //取消
		public static final String BIZ_TASK_STATUS_6 = "6"; //完成'
	}
	
	interface PermarkType{
		String DIGIT = "1";
		String CM = "2";
		String VOD = "3";
	}
	
	interface BusinessCode{
		/**
		 * 打印受理单编号
		 */
		String PRINT_BUSINESS_PAGE = "MWWGPAGE";
		
		/**
		 * 打印电子发票
		 */
		String PRINT_ENVOICE_PAGE = "MWWGVOICE";
	}
	
	interface VisitCommentRecordStatus{
		String VISIT_UNSEND = "0";
		String VISIT_SENDED = "1";
	}
}
