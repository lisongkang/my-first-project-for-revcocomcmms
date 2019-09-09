package com.maywide.test.constant;

public interface TestConstant {

    // --其它常量 begin

    interface CallIpPortType {
    
        /** 测试机类型：本地开发(127.0.0.1:8180) */
        public static final String LOCAL_DEV = "127.0.0.1:8180";
        
        /** 测试机类型：公网测试(121.33.246.213:80) */
        public static final String PUB_TEST = "121.33.246.213:80";
        
        /** 测试机类型：内部开发(10.205.28.102:8082) */
        public static final String INNER_DEV = "10.205.28.102:8082";
        
        /** 测试机类型：内部测试(180.200.3.150:80) */
        public static final String INNER_TEST = "180.200.3.150:80";
    }

    // --其它常量 end

}
