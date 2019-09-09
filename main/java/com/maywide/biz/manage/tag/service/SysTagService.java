package com.maywide.biz.manage.tag.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.inter.pojo.quetaginfo.QueTagInfoCmpReq;
import com.maywide.biz.inter.pojo.quetaginfo.QueTagInfoCmpResp;
import com.maywide.biz.inter.pojo.quetaginfo.QueTagInfoResp;
import com.maywide.biz.inter.service.InterWarnTagInfoService;
import com.maywide.biz.manage.tag.dao.SysTagDao;
import com.maywide.biz.manage.tag.entity.SysTag;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.CheckUtils;

@Service
@Transactional
public class SysTagService  extends BaseService<SysTag,Long>{

	@Autowired
	private SysTagDao  sysTagDao;
	
	@Autowired
	private PersistentService persistentService;
	
	@Autowired
	private InterWarnTagInfoService interWarnTagInfoService;
	
	@Override
	protected BaseDao<SysTag, Long> getEntityDao() {
		return sysTagDao;
	}

	@Transactional(readOnly=true)
	public PageImpl<SysTag> getODSsysTag(String city, Pageable pageable) throws Exception {
		PageImpl<SysTag> pageResult = null;
        Page<SysTag> page = new Page<SysTag>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        CheckUtils.checkNull(city, "地市不能为空");
        StringBuffer tmpSql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		
		tmpSql.append("SELECT * FROM (");
		tmpSql.append("SELECT t.tagid as id,t.tagname,t.tagdesc,t.owner,'"+city.toUpperCase()+"' as city from");
		tmpSql.append(" "+city.toLowerCase()+"_boss.TW_TAG_"+city.toUpperCase()+" t");
		tmpSql.append(" where 1=1 ");
		//获取本地sys_tag
		String existTags = findLocalExistTags();
		if(StringUtils.isNotBlank(existTags)){
			tmpSql.append(" AND t.tagid not in("+existTags+")");
		}
		tmpSql.append(") v");
		PersistentService odsPersistentService = SpringBeanUtil.getPersistentService();
		page = odsPersistentService.find(page, tmpSql.toString(), SysTag.class, paramList.toArray());
        List<SysTag> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<SysTag>(resultList, pageable, total);
        } else {
            pageResult = new PageImpl<SysTag>(new ArrayList<SysTag>(), pageable, 0);
        }
        return pageResult;
	}

	private String findLocalExistTags() throws Exception {
		String  sql = "SELECT t.tagid as id  FROM sys_tag t";
		List<SysTag> existTags = persistentService.find(sql, SysTag.class, null);
		
		if(existTags.size()>0){
			String separator=",";
			Set<String> existTagSet = new HashSet<String>();
			for (SysTag tag: existTags) {
				existTagSet.add(tag.getId().toString());
			}
			return StringUtils.join(existTagSet.toArray(),separator);
		}else{
			return "";
		}
	}
    
	
	public void saveSysTag(SysTag sysTag) throws Exception {
		persistentService.save(sysTag);
	}

	public List<SysTag> getCMPsysTagByInf(String city) throws Exception {
		List<SysTag> returnData = new ArrayList<SysTag>();
		QueTagInfoCmpReq req = new QueTagInfoCmpReq();
		QueTagInfoResp resp= new QueTagInfoResp();
		req.setCity(city);
		req.setAreaid("-1");//-1（所有业务区）
		
		interWarnTagInfoService.queTagInfo(req, resp);
		List<QueTagInfoCmpResp> datas = resp.getDatas();
		
		Map<String,SysTag> existTagsMap = findLocalExistTagsMapByCity(city);
		
		for(int i = 0 ; i < datas.size(); i++){
			QueTagInfoCmpResp remoteTag =  datas.get(i);
			if(!existTagsMap.containsKey(remoteTag.getTagid())){
				
				SysTag newTag = new SysTag();
				newTag.setTagcode(remoteTag.getTagid());//tagcode 记录cmp获取的标签ID
				newTag.setCity(remoteTag.getCity());
				newTag.setTagname(remoteTag.getTagname());
				newTag.setTagdesc(remoteTag.getTagdesc());
				newTag.setOwner(remoteTag.getOwner());
				returnData.add(newTag);
			}
		}
		
		return returnData;
	}
	private Map<String,SysTag> findLocalExistTagsMapByCity(String city) throws Exception{
		String  sql = "SELECT t.tagid as id ,t.tagcode FROM sys_tag t where t.city = '"+city+"'";
		List<SysTag> existTags = persistentService.find(sql, SysTag.class, null);
		Map<String,SysTag> localTagMap = new HashMap<String, SysTag>();
		for(SysTag tag : existTags){
			localTagMap.put(tag.getTagcode().toString(), tag);
		}
		return localTagMap;
	}
	
}
