package com.maywide.common.pub.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.web.SimpleController;
import com.maywide.core.web.listener.ApplicationContextPostListener;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 公共数据服务处理
 */
public class DataController extends SimpleController {

    /**
     * @see ApplicationContextPostListener
     * @return
     */
    @MetaData("枚举数据集合")
    public HttpHeaders enums() {
        ServletContext sc = ServletActionContext.getServletContext();
        setModel(sc.getAttribute("enums"));
        return buildDefaultHttpHeaders();
    }

    /**
     * @see ApplicationContextPostListener
     * @return
     */
    @MetaData("数据字典数据集合")
    public HttpHeaders dictDatas() {
        List<Map<String, Object>> datas = Lists.newArrayList();
        setModel(datas);
        return buildDefaultHttpHeaders();
    }
}
