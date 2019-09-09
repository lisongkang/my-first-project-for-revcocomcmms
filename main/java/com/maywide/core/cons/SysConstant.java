package com.maywide.core.cons;

public interface SysConstant {
    interface Operator {
        /** 操作员状态：启用 */
        final String PRV_USE_STATUS_ENABLED = "0";
        /** 操作员状态：停用 */
        final String PRV_USE_STATUS_DISABLED = "1";
    }

    interface PrvBizRightType {
        /** 权限类型：客户受理权限 */
        final String BIZACCPT = "0";
        /** 权限类型：区域管理权限 */
        final String USERQUE = "1";
        /** 权限类型：设备销售权限 */
        final String RESUSE = "2";
        /** 权限类型：组织管理权限 */
        final String RESQUE = "3";
        /** 权限类型：报表查看权限 */
        final String REPORT = "4";
        /** 权限类型：发展 */
        final String DEVELOPING = "5";
    }

    interface SysYesNo {
        /** 是否标志：是 */
        public static final String YES = "Y";
        /** 是否标志：否 */
        public static final String NO = "N";
    }

    interface mcr {
        String OPEN_ID = "OPEN_ID";
    }

    interface nfyy {
        String validOpenid = "validOpenid";
    }

    interface PrvSysparam {
        /** 动态SQL，用于分级展示的参数 */
        public static final String DYNAMIC_SQL_LEVEL = "@DYNAMIC_LEVEL";
        /** 动态SQL，用于翻译展示的参数 */
        public static final String DYNAMIC_SQL_DETAIL = "@DYNAMIC_DETAIL";
        /** 动态SQL，用于格式定义的参数 */
        public static final String DYNAMIC_SQL = "@DYNAMIC_SQL";
        /** 普通参数 */
        public static final String ROOT = "@ROOT";
        /** 表示格式型参数 */
        public static final String FORMAT = "@FORMAT";
    }

    public interface PrintScope {
        final static String HEAD = "HEAD";
        final static String FOOT = "FOOT";
        final static String BODY = "BODY";

        final static String SPLITSTR = "<split-str>";
    }

    public interface ProdStatus {
        String SETTING = "0"; // 设置中
        String FINISH = "1"; // 设置完成
        String AUDIT = "2"; // 已审核
        String PUBLISH = "3"; // 已发布
    }

    interface SysCardType {
        /** 证件类型：身份证 */
        public static final String IDCARD = "1";
        /** 证件类型：户口簿 */
        public static final String BOOKLET = "2";
        /** 证件类型：军官证 */
        public static final String MILICERT = "3";
        /** 证件类型：警官证 */
        public static final String POLCERT = "4";
        /** 证件类型：志愿兵证 */
        public static final String VOLCERT = "5";
        /** 证件类型：证件遗失证明 */
        public static final String LOSSCERT = "6";
        /** 证件类型：单位证明,营业执照 */
        public static final String BIZCERT = "7";
        /** 证件类型：其他类型 */
        public static final String OTHERCERT = "0";
    }
	
	interface SysType {
		Integer ROOT_SYS = 1;
	}
	
	interface MatchType {
		String EQ = "EQ";
		String LE = "LE"; //小于或等于
		String LT = "LT"; //小于
		String GE = "GE"; //大于或等于
		String GT = "GT"; //大于
		String NE = "NE"; //不等于
		String LIKE = "LIKE"; 
		String NULL = "NULL";
		String NOTNULL = "NOTNULL";
		String IN = "IN";
		String NOTIN = "NOTIN";
	}
	
	interface TaskType {
		String CRON        = "C";
		String SIMPLE      = "S";
		String IMMEDIATELY = "I";

	}
	
	interface CmdType {
		String MESSAGE_NEW  = "1";
		String WEIXIN_REPLY = "2";
		String UNIFY_PAY_NOTICE = "3";
		String ELECTION_PAY_NOTICE = "4";
	}
}
