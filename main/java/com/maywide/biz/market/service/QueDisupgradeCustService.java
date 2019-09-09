package com.maywide.biz.market.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.monstat.service.AssIndexMonprogressService;
import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.core.service.CommonService;
import com.maywide.biz.inter.pojo.querycatalog.QueCustMarkInfoRespBO;
import com.maywide.biz.inter.pojo.querycatalog.QueCustMarktInfoReq;
import com.maywide.biz.inter.pojo.querycatalog.QueCustMarktInfoResp;
import com.maywide.biz.inter.service.PubService;
import com.maywide.biz.market.bo.QueDisupgradeCustResultBO;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional
public class QueDisupgradeCustService extends CommonService {
    @Autowired
    private PersistentService          persistentService;

    @Autowired
    private PubService                 pubService;

    @Autowired
    private AssIndexMonprogressService assIndexMonprogressService;

    @Autowired
    private GridInfoService            gridInfoService;

    public PageImpl<QueDisupgradeCustResultBO> queryDisupgradeCustInfo(QueCustMarktInfoReq param, Pageable pageable)
            throws Exception {
        CheckUtils.checkNull(param, "查询条件不能为空");
        CheckUtils.checkNull(param.getPatchids(), "片区不能为空");
        CheckUtils.checkNull(param.getNetattr(), "设备单双属性不能为空");

//        param.setBizorderid(persistentService.getSequence("SEQ_BIZ_CUSTORDER_ID").toString());
        param.setBizorderid(getBizorderid());
        param.setIscust("N"); // 该功能默认发送N
        param.setCurrentPage(String.valueOf(pageable.getPageNumber() + 1));
        param.setPagesize(pageable.getPageSize() + "");
        param.setPatchids(param.getPatchids().replaceAll(", ", ",")); // 前端控件发送过来的字符串会有逗号+空格分割参数，需要先处理

        QueCustMarktInfoResp resp = new QueCustMarktInfoResp();
        ReturnInfo returnInfo = pubService.queCustMarktInfo(param, resp);
        Long returnCode = returnInfo.getCode();
        if (!returnCode.equals(0L)) {
            throw new Exception(returnInfo.getMessage());
        }

        Map<String, String> gridPathMap = new HashMap<String, String>();
        List<QueCustMarkInfoRespBO> queCustResultList = resp.getResult();
        List<QueDisupgradeCustResultBO> resultList;
        if (null != queCustResultList && queCustResultList.size() > 0) {
            resultList = new ArrayList<QueDisupgradeCustResultBO>(queCustResultList.size());
            for (QueCustMarkInfoRespBO queCustRespBo : queCustResultList) {
                QueDisupgradeCustResultBO qdcrb = new QueDisupgradeCustResultBO();
                BeanUtils.copyProperties(qdcrb, queCustRespBo);
                addExtraInfo(qdcrb, gridPathMap);
                resultList.add(qdcrb);
            }
        } else {
            resultList = new ArrayList<QueDisupgradeCustResultBO>();
        }

        Page<QueDisupgradeCustResultBO> page = new Page<QueDisupgradeCustResultBO>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());

        PageImpl<QueDisupgradeCustResultBO> pageResult = new PageImpl<QueDisupgradeCustResultBO>(resultList, pageable,
                Integer.parseInt(resp.getTotalCount()));
        return pageResult;
    }

    /**
     * 额外查询网格路径信息
     * 
     * @param resultList
     * @throws Exception
     */
    private void addExtraInfo(QueDisupgradeCustResultBO qdcrb, Map<String, String> gridPathMap) throws Exception {
        String patchId = qdcrb.getPatchid();
        if (!gridPathMap.containsKey(patchId)) {
            String gridPath = gridInfoService.findPatchPath(Long.parseLong(patchId), 1);
            gridPathMap.put(patchId, gridPath);
        }
        qdcrb.setGridpath(gridPathMap.get(patchId));
    }
}