package com.maywide.biz.ass.assdata.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.assdata.pojo.StasticResultDay;
import com.maywide.biz.ass.assdata.pojo.StasticResultMonth;
import com.maywide.biz.ass.daystat.entity.AssIndexDayprogress;
import com.maywide.biz.ass.monstat.entity.AssIndexMonprogress;
import com.maywide.biz.ass.topatch.entity.AssIndexTopatch;
import com.maywide.biz.core.service.CommonService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;

@Service
@Transactional
public class AssDataService extends CommonService {
	
    @Autowired
    private PersistentService persistentService;
    
    
    /**
     * 查询指定月份的业务收入类型统计结果
     * @param staMonth
     * @return
     * @throws Exception
     */
    public void handleMonthProgress(Date staMonthDate) throws Exception {
    	List<StasticResultMonth> dataList = queryStasticResult(staMonthDate);
    	analyzeStasticResultMonth(staMonthDate, dataList);
    }
    
    /**
     * 查询指定日期的发展新用户的统计结果
     * @param staDayDate
     * @throws Exception
     */
    public void handleDayProgress(Date staDayDate) throws Exception{
    	
    	String newservType = "1"; //1 数字电视主机有效用户数 2 高清双向机顶盒到达数 3 宽带业务有效用户数
    	String city = "GZ";
    	String assparam = "NEWSERV";
    	
    	List<StasticResultDay> datalist = queryStasticResultDay(staDayDate, newservType, city, assparam);
    	analyzeStasticResultDay(datalist);
    	
    	newservType = "2"; //1 数字电视主机有效用户数 2 高清双向机顶盒到达数 3 宽带业务有效用户数
    	datalist = queryStasticResultDay(staDayDate, newservType, city, assparam);
    	analyzeStasticResultDay(datalist);
    	
    	newservType = "3"; //1 数字电视主机有效用户数 2 高清双向机顶盒到达数 3 宽带业务有效用户数
    	datalist = queryStasticResultDay(staDayDate, newservType, city, assparam);
    	analyzeStasticResultDay(datalist);
    	
    }
    
    private void analyzeStasticResultDay(List<StasticResultDay> datalist) throws Exception{
    	
    	for (StasticResultDay stasticResultDay : datalist) {
			Long assid = stasticResultDay.getAssid();
			Long patchid = stasticResultDay.getPatchid();
			
			Date bdate = null;
    		Double revnum = null;
    		
    		// 根据assid和patchid，查询ass_index_topatch表，是否存在记录
    		AssIndexTopatch topatch = new AssIndexTopatch();
    		topatch.setAssid(assid);
    		topatch.setPatchid(patchid);
    		List<AssIndexTopatch> topatchlist = persistentService.find(topatch);
    		if (topatchlist != null && topatchlist.size() > 0) {
    			topatch = topatchlist.get(0);
    			revnum = topatch.getRevnum();
			} else {
    			revnum = 0d;
			}
    		
    		Double amount = stasticResultDay.getNewservAmount();
    		AssIndexDayprogress assIndexDayprogress = new AssIndexDayprogress();
    		assIndexDayprogress.setTdate(stasticResultDay.getTjdate());
    		assIndexDayprogress.setAssid(assid);
    		assIndexDayprogress.setObjtype("2");
    		assIndexDayprogress.setObjid(patchid);
    		assIndexDayprogress.setTotal(revnum);
    		assIndexDayprogress.setCurnum(stasticResultDay.getNewservAmount());
    		if (!revnum.equals(0d)) {
    			assIndexDayprogress.setRate(amount/revnum );
    		}
    		
    		persistentService.save(assIndexDayprogress);
    		
		}
    }
    
	private List<StasticResultDay> queryStasticResultDay(Date staDayDate,
			String newservType, String city, String assparam) throws Exception {
		
		String staDate = DateUtils.getFormatDateString(staDayDate, "yyyyMMdd");
		String _staDate = DateUtils.getFormatDateString(staDayDate, "yyyy-MM-dd");

    	List plist = new ArrayList();
    	StringBuilder buff = new StringBuilder();
    	
    	buff.append("SELECT v.tjdate,a.assid,v.patchid");
    	buff.append(getNewservStasticField(newservType));
    	buff.append(" FROM syn_boss_tw2_performance v, ass_index_store a, ass_index_object b ");
    	buff.append(" WHERE 1=1 ");
    	buff.append(" AND a.assid = b.assid ");
    	buff.append(" AND a.assparam = ? ");
    	plist.add(assparam);
    	buff.append(" AND b.assobj = ? ");
    	plist.add(newservType);
    	buff.append(" AND v.tjdate = ? ");
    	plist.add(staDate);
    	buff.append(" AND v.city = ? ");
    	plist.add(city);
    	buff.append(" AND a.city = ? ");
    	plist.add(city);
    	buff.append(" AND a.expdate >= STR_TO_DATE(?, '%Y-%m-%d') ");
    	plist.add(_staDate);
    	
		List<StasticResultDay> resultlist = getDAO().find(buff.toString(),
				StasticResultDay.class, plist.toArray());

    	return resultlist;
    }
    
    private String getNewservStasticField(String newservType) {
    	if ("1".equals(newservType)) {
    		return ", v.dh_valids newservAmount";
    	} else if ("2".equals(newservType)) {
    		return ", v.hd_stbs newservAmount ";
    	} else if ("3".equals(newservType)) {
    		return ", v.cm_valids newservAmount";
    	} else {
    		return " ";
    	}
    }
    
    /**
     * 统计累积数量
     * @param staMonthDate
     * @param dataList
     * @throws Exception
     */
    public void analyzeStasticResultMonth(Date staMonthDate, List<StasticResultMonth> dataList) throws Exception {
    	for (StasticResultMonth stasticResultMonth : dataList) {
    		Long patchid = stasticResultMonth.getPatchid();
    		Long assid = stasticResultMonth.getAssid();
    		
    		Date bdate = null;
    		Double revnum = null;
    		Double comnum = stasticResultMonth.getIncome();
    		
    		// 根据assid和patchid，查询ass_index_topatch表，是否存在记录
    		AssIndexTopatch topatch = new AssIndexTopatch();
    		topatch.setAssid(assid);
    		topatch.setPatchid(patchid);
    		List<AssIndexTopatch> topatchlist = persistentService.find(topatch);
    		if (topatchlist != null && topatchlist.size() > 0) {
    			topatch = topatchlist.get(0);
    		
    			// 获取考核起始日期和接受任务数
    			bdate = topatch.getBdate();
    			revnum = topatch.getRevnum();
    			
			} else {
				// 考核起始日期默认1月份， 接受任务数默认为0
				bdate = getFirstMonth();
    			revnum = 0d;
			}
    		
    		// 如果是stasticResultMonth.getTmonth()是1月份，不需要累积，否则需要累积
    		if (!isFirstMonth(stasticResultMonth.getTmonth())) {
    			// 从ass_index_monprogress表中查询上个月的记录，如果存在，则进行累积，否则从考核起日期计算来累积
        		AssIndexMonprogress monprogress = new AssIndexMonprogress();
        		monprogress.setAssid(assid);
        		monprogress.setObjtype("2"); // 2：片区
        		monprogress.setObjid(patchid);
        		monprogress.setTmonth(getPremonth(stasticResultMonth.getTmonth()));
        		
        		List<AssIndexMonprogress> monprogresslist = persistentService.find(monprogress);
        		if (monprogresslist != null && monprogresslist.size() > 0) {
        			monprogress = monprogresslist.get(0);
        			
        			// 累积总梳理
        			comnum += monprogress.getComnum();
        		} else {
        			List<String> monthSet = getMonthSet(staMonthDate,bdate);
        			if (monthSet != null && monthSet.size() > 0) {
        				StasticResultMonth result = stasticCurMonthResult(staMonthDate, assid,patchid,monthSet);
            			if (result != null) {
            				comnum += result.getIncome();
            			}
        			}
        			
        		}
    		}
    		
    		// 保存AssIndexMonprogress对象
    		AssIndexMonprogress monprogress = new AssIndexMonprogress();
    		monprogress.setAssid(stasticResultMonth.getAssid());
    		monprogress.setTmonth(stasticResultMonth.getTmonth());
    		monprogress.setObjtype("2");
    		monprogress.setObjid(stasticResultMonth.getPatchid());
    		monprogress.setTotal(revnum);
    		monprogress.setCurnum(stasticResultMonth.getIncome());
    		monprogress.setComnum(comnum);
    		
    		persistentService.save(monprogress);
    		
		}
    }
    
    /**
     * 获取月份集合
     * @param staMonthDate
     * @param bdate
     * @return
     */
    private List<String> getMonthSet(Date staMonthDate, Date bdate) {
    	List<String> monthSet = new ArrayList<String>();
    	Calendar bdateCal = Calendar.getInstance();
    	bdateCal.setTime(bdate);
    	
    	Calendar staCal = Calendar.getInstance();
    	staCal.setTime(staMonthDate);
    	
    	// 如果考核起日期大于当前日期，则无效
    	if (bdate.after(staCal.getTime())) {
    		return null;
    	}
    	
    	// 不考虑年份， 只对月份进行比较
    	int bdateMonth = bdateCal.get(Calendar.MONTH);
    	int staMonth = staCal.get(Calendar.MONTH);
    	
    	// 考核月份大于当前月份,则无效
    	if (bdateMonth >= staMonth) {
    		return null;
    	}
    	
    	while(staMonth > bdateMonth) {
    		monthSet.add(DateUtils.getFormatDateString(bdateCal.getTime(), "yyyyMM"));
    		bdateCal.add(Calendar.MONTH, 1);
    		
    		bdateMonth = bdateCal.get(Calendar.MONTH);
    	}
    	
    	return monthSet;
    }
    
    /**
     * 判断是否1月份
     * @param statMonth
     * @return
     */
    private boolean isFirstMonth(String statMonth){
    	String month = statMonth.substring(statMonth.length()-2, statMonth.length());
    	return "01".equals(month);
    }
    
    /**
     * 获取上个月份
     * @param curmonth
     * @return
     */
    private String getPremonth(String curmonth) {
    	String month = curmonth.substring(curmonth.length()-2, curmonth.length());
    	int imonth = Integer.parseInt(month);
    	Calendar bdateCal = Calendar.getInstance();
    	bdateCal.set(Calendar.MONTH, (imonth-1));
    	bdateCal.add(Calendar.MONTH, -1);
    	
    	return DateUtils.getFormatDateString(bdateCal.getTime(), "yyyyMM");
    }
    
    /**
     * 获取1月份
     * @return
     */
    private Date getFirstMonth(){
    	Calendar firstCal = Calendar.getInstance();
    	firstCal.set(Calendar.MONTH, 0);
    	return firstCal.getTime();
    }
    
    /**
     * 不按月归并， 提供月份集合、assid、patchid查询，计算累计
     * @param assid 考核ID
     * @param patchid 片区ID
     * @param monthSet 月份集合
     * @throws Exception
     */
    public StasticResultMonth stasticCurMonthResult(Date staMonthDate, Long assid, Long patchid, List<String> monthSet) throws Exception{
    	
    	List plist = new ArrayList();
    	String staDate = new SimpleDateFormat("yyyy-MM-dd").format(staMonthDate);
    	
    	StringBuilder buff = new StringBuilder();
    	
    	buff.append(" SELECT a.assid, v.* from ass_index_store a, ass_index_object b, ");
    	
    	buff.append("(");
    	buff.append(" SELECT t.city,t.patchid,s.extpermark,SUM(t.income) income ");
    	buff.append(" FROM syn_boss_tw2_income_month t, ass_stat_feecode s ");
    	buff.append(" WHERE 1=1 ");
    	buff.append(" AND FIND_IN_SET(t.rfeecode,s.rfeecodes) ");
    	buff.append(getMonthSql(monthSet));
    	buff.append(" GROUP BY t.city, t.patchid, s.extpermark ");
    	
    	buff.append(") v ");
    	
    	buff.append(" WHERE 1=1 ");
    	buff.append(" AND a.assid = b.assid ");
    	buff.append(" AND b.assobj = v.extpermark ");
    	buff.append(" AND a.assparam = 'BUSINCOME' ");
    	buff.append(" AND a.expdate >= STR_TO_DATE(?, '%Y-%m-%d') ");
    	plist.add(staDate);
    	
    	buff.append(" AND a.assid = ? ");
    	plist.add(assid);
    	
    	buff.append(" AND v.patchid = ? ");
    	plist.add(patchid);
    	
    	List<StasticResultMonth> resultlist = getDAO().find(buff.toString(), StasticResultMonth.class, plist.toArray());
    	if (resultlist != null && resultlist.size() > 0) {
    		StasticResultMonth result = resultlist.get(0);
    		return result;
    	} else {
    		return null;
    	}
    	
    }
    
    /**
     * 构造多个月份查询SQL
     * @param monthSet
     * @return
     */
    private String getMonthSql(List<String> monthSet) {
    	
    	if (monthSet == null || monthSet.size() <= 0) {
    		return "";
    	}
    	
    	StringBuilder buff = new StringBuilder();
    	buff.append(" AND t.tmonth in (");
    	for (String string : monthSet) {
    		buff.append("'").append(string).append("'").append(",");
		}
    	// 去掉最后一个逗号
    	buff.delete(buff.length()-1, buff.length());
    	
    	buff.append(")");
    	
    	return buff.toString();
    }
    
    
    /**
     * 查询指定月份的业务收入类型统计结果
     * @param staMonth 统计月份
     * @return
     * @throws Exception
     */
    public List<StasticResultMonth> queryStasticResult(Date staMonthDate) throws Exception {
    	String staDate = DateUtils.getFormatDateString(staMonthDate, "yyyy-MM-dd");
    	String staMonth = DateUtils.getFormatDateString(staMonthDate, "yyyyMM");
    	
    	StringBuilder buff = new StringBuilder();
    	
    	buff.append("SELECT a.assid, v.* from ass_index_store a, ass_index_object b, ");
    	
    	buff.append("(");
    	buff.append("SELECT t.tmonth,t.city,t.patchid,s.extpermark,SUM(t.income) income ");
    	buff.append("FROM syn_boss_tw2_income_month t, ass_stat_feecode s ");
    	buff.append("WHERE 1=1 ");
    	buff.append("AND FIND_IN_SET(t.rfeecode,s.rfeecodes) ");
    	buff.append("AND t.tmonth = ?");
    	buff.append(" GROUP BY t.tmonth, t.city, t.patchid, s.extpermark");
    	
    	List plist = new ArrayList();
    	plist.add(staMonth);
    	
    	buff.append(") v ");
    	
    	buff.append("WHERE 1=1 ");
    	buff.append("AND a.assid = b.assid ");
    	buff.append("AND b.assobj = v.extpermark ");
    	buff.append("AND a.assparam = 'BUSINCOME' ");
    	buff.append("AND a.expdate >= STR_TO_DATE(?, '%Y-%m-%d')");
    	
    	plist.add(staDate);
    	
    	List<StasticResultMonth> resultlist = getDAO().find(buff.toString(), StasticResultMonth.class, plist.toArray());
    	
    	return resultlist;
    }
    
}
