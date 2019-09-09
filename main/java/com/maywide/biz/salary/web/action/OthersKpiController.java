


package com.maywide.biz.salary.web.action;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.service.GridInfoService;
import com.maywide.biz.prv.entity.PrvArea;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.biz.salary.SalaryConstants;
import com.maywide.biz.salary.entity.BaseWage;
import com.maywide.biz.salary.entity.OthersKpi;
import com.maywide.biz.salary.entity.OthersKpiExcelTemp;
import com.maywide.biz.salary.entity.OthersKpiTextConfig;
import com.maywide.biz.salary.service.OthersKpiAuditService;
import com.maywide.biz.salary.service.OthersKpiService;
import com.maywide.biz.salary.service.OthersKpiTextConfigService;
import com.maywide.core.cons.Constant;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.DateUtils;
import com.maywide.core.web.view.OperationResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.rest.HttpHeaders;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 积分项目配置
 * <p>

 */
public class OthersKpiController extends SalaryController<OthersKpi, Long> {

    @Autowired
    private OthersKpiService othersKpiService;
    @Autowired
    private OthersKpiAuditService othersKpiAuditService;
    @Autowired
    private OthersKpiTextConfigService othersKpiTextConfigService;
    @Autowired
    private PersistentService persistentService;
    @Autowired
    private GridInfoService gridInfoService;
    private OthersKpi othersKpi;

    @Override
    protected BaseService<OthersKpi, Long> getEntityService() {
        return othersKpiService;
    }

    /**
     * 分页搜索
     */
    public HttpHeaders findByPage() {
        List<OthersKpi> list = new ArrayList<OthersKpi>();
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        try {
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            if (!loginInfo.getLoginname().equals(Constant.ADMINISTRATOR)) {
                othersKpi.setCity(loginInfo.getCity());
            }
            setModel(othersKpiService.findByPage(othersKpi, pageable));

        } catch (Exception e) {
            e.printStackTrace();
            setModel(new PageImpl<OthersKpi>(list, pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }
    /**
     * 分页搜索
     */
    public HttpHeaders findDetail() {
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        try {
           String dateMonth = othersKpi.getDateMonth();
           Long operid = othersKpi.getOperid();
           String grid = othersKpi.getGrid();
           setModel(othersKpiService.findDetail(grid,operid,dateMonth,pageable));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(new PageImpl<OthersKpi>(new ArrayList<OthersKpi>(), pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }
    /**
     * 新增或者修改
     *
     * @return
     */
    @Override
    public HttpHeaders doSave() {
        LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        try {
            List<OthersKpi> saves = new ArrayList<OthersKpi>();
            //网格及人员
            String[] grids = bindingEntity.getGrids().replace(" ","").split(",");
            String[] operids = bindingEntity.getOperids().replace(" ","").split(",");
            //积分项目
            String[] ids = bindingEntity.getTextConfigIds().replace(" ","").split(",");
            String[] scores = bindingEntity.getScores().replace(" ","").split(",");
            String[] remarks = bindingEntity.getRemarks().replace(" ","").split(",");
            for (int j = 0;j<grids.length;j++){
                if(bindingEntity.getId()==null) {
                    Boolean bool = othersKpiService.exists(grids[j],Long.valueOf(operids[j]),
                            bindingEntity.getDateMonth(),null);
                    if (bool) {
                        throw new Exception("该网格人员在" + bindingEntity.getDateMonth() + "月份下已有数据!");
                    }
                }
                OthersKpi kpi = null;
                for (int i=0;i<ids.length;i++) {
                    if (null == getId() || StringUtils.isBlank(getId().toString())) {

                        bindingEntity.setCreateAt(new Date());
                        bindingEntity.setCreateBy(loginInfo.getLoginname());
                    }
                    bindingEntity.setTextConfigId(Long.valueOf(ids[i]));
                    bindingEntity.setScore(Double.valueOf(scores[i]));
                    bindingEntity.setRemark(remarks[i]);
                    bindingEntity.setUpdateAt(new Date());
                    bindingEntity.setUpdateBy(loginInfo.getLoginname());
                    kpi = new OthersKpi();
                    BeanUtils.copyProperties(bindingEntity,kpi);
                    kpi.setGrid(grids[j]);
                    kpi.setOperid(Long.valueOf(operids[j]));
                    saves.add(kpi);
                }
            }
            othersKpiService.save(saves,bindingEntity.getAuditUser());
            setModel(OperationResult.buildSuccessResult("保存操作成功", bindingEntity));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("保存操作失败,"+e.getMessage()));
        }

        return buildDefaultHttpHeaders();
    }

    /**
     * 删除
     */
    public HttpHeaders doDelete() {
        try {
            Collection<OthersKpi> entities =  getEntitiesByParameterIds();
            othersKpiService.deleteByOperidAndDateMonth((ArrayList<OthersKpi>)entities);
            setModel(OperationResult.buildSuccessResult("成功删除所选选取记录:" + entities.size() + "条"));
        }catch (Exception e){
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("删除失败:"+e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }

    public HttpHeaders batchAudit(){
        try {
            Collection<OthersKpi> entities =  getEntitiesByParameterIds();
            String auditUser = getParameter("auditUser");
            if(StringUtils.isEmpty(auditUser)){
                throw new Exception("审核人不能为空!");
            }
            othersKpiAuditService.batchAudit((List<OthersKpi>)entities,auditUser);
            setModel(OperationResult.buildSuccessResult("成功提交所选选取记录:" + entities.size() + "条"));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("提交失败:"+e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }

 public HttpHeaders uploadExcelTemp(){
        try {
            LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
            List<OthersKpi> list = new ArrayList<OthersKpi>();
            List<OthersKpiExcelTemp> rowList = persistentService.findByProperty(OthersKpiExcelTemp.class,
                    "createBy",loginInfo.getOperid()+"");
            OthersKpi kpi = null;
            for (OthersKpiExcelTemp othersKpiExcelTemp : rowList) {
                if(!"1".equals(othersKpiExcelTemp.getCheckFlag())){
                    throw new Exception("该批数据没有全部通过校验!");
                }
                kpi = new OthersKpi();
                BeanUtils.copyProperties(othersKpiExcelTemp,kpi);
                kpi.setScore(Double.valueOf(othersKpiExcelTemp.getScore()));
                kpi.setId(null);
                kpi.setCreateAt(new Date());
                kpi.setCreateBy(loginInfo.getLoginname());
                kpi.setUpdateAt(new Date());
                kpi.setUpdateBy(loginInfo.getLoginname());
                list.add(kpi);
            }
            othersKpiService.save(list,bindingEntity.getAuditUser());
            persistentService.executeSql("delete from salary_others_kpi_excel_temp where create_by=?",
                    loginInfo.getOperid()+"");
            setModel(OperationResult.buildSuccessResult("导入成功"));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("提交失败:"+e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }


    /**
     * excel临时导入数据分页搜索
     */
    public HttpHeaders findExcelTempByPage() {
        List<OthersKpiExcelTemp> list = new ArrayList<OthersKpiExcelTemp>();
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(getRequest());
        try {
            setModel(othersKpiService.findExcelTempByPage(new OthersKpiExcelTemp(), pageable));
        } catch (Exception e) {
            e.printStackTrace();
            setModel(new PageImpl<OthersKpiExcelTemp>(list, pageable, 1));
        }
        return buildDefaultHttpHeaders();
    }

    /**
     * 更新excel临时数据
     * @return
     */
    public HttpHeaders excelUpdate(){
        try {
            OthersKpiExcelTemp temp = (OthersKpiExcelTemp)persistentService.find(OthersKpiExcelTemp.class,getId());
            temp.setGridName(getRequest().getParameter("gridName"));
            temp.setAccount(getRequest().getParameter("account"));
            temp.setDateMonth(getRequest().getParameter("dateMonth"));
            temp.setConfigType(getRequest().getParameter("configType"));
            temp.setConfigContext(getRequest().getParameter("configContext"));
            temp.setScore(getRequest().getParameter("score"));

            List<OthersKpiExcelTemp> rowList = persistentService.find("from OthersKpiExcelTemp where createBy=? and id<>?",
                    AuthContextHolder.getLoginInfo().getOperid()+"",getId());

            //校验数据
            boolean bool = checkExcelData(temp,rowList);
            //更新数据
            persistentService.update(temp);
            setModel(OperationResult.buildSuccessResult("数据更新成功,校验"+(bool?"通过":"不通过")));
        }catch (Exception e){
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("数据更新异常"));
        }
        return buildDefaultHttpHeaders();
    }
    /**
     * 删除excel临时数据
     * @return
     */
    public HttpHeaders excelDelete(){
        try {
            OthersKpiExcelTemp temp  = null;
            for (String id : getParameterIds()) {
                temp = new OthersKpiExcelTemp();
                temp.setId(Long.valueOf(id));
                persistentService.delete(temp);
            }
            setModel(OperationResult.buildSuccessResult("成功删除所选选取记录:" + getParameterIds().length + "条"));
        }catch (Exception e){
            e.printStackTrace();
            setModel(OperationResult.buildFailureResult("删除失败:"+e.getMessage()));
        }
        return buildDefaultHttpHeaders();
    }
    /**
    /**
     * 批量导入row转实体类
     * @param row
     * @param rowList
     * @param rowIndex
     * @return
     */
    @Override
    public OthersKpi bulidExcelEntity(Row row, List<OthersKpi> rowList, int rowIndex) throws Exception{
        OthersKpi result = new OthersKpi();
        OthersKpiExcelTemp bw = new OthersKpiExcelTemp();
        bw.setGridName(eu.getCellValue(row.getCell(0)));//网格名称
        bw.setAccount(eu.getCellValue(row.getCell(1)));//网格人员账号
        bw.setDateMonth(eu.getCellValue(row.getCell(2)));//月份
        bw.setConfigType(eu.getCellValue(row.getCell(3)));//积分类型
        bw.setConfigContext(eu.getCellValue(row.getCell(4)));//积分项目
        bw.setScore(eu.getCellValue(row.getCell(5)));//得分
        bw.setRemark(eu.getCellValue(row.getCell(6)));//扣分说明
        bw.setCity(bindingEntity.getCity());
        bw.setAreaid(bindingEntity.getAreaid());
        bw.setStatus(SalaryConstants.OthersKpi.AUDIT_STAUTS);
        //校验数据
        List<OthersKpiExcelTemp> rows = new ArrayList<OthersKpiExcelTemp>();
        OthersKpiExcelTemp temp = null;
        for (OthersKpi kpi : rowList) {
            temp = new OthersKpiExcelTemp();
            BeanUtils.copyProperties(kpi.getOthersKpiExcelTemp(),temp);
            rows.add(temp);
        }
        checkExcelData(bw,rows);
        result.setOthersKpiExcelTemp(bw);
        return  result;
    }
    @Override
    public void excelSave(List<OthersKpi> list) throws Exception{
        List<OthersKpiExcelTemp> tempList = new ArrayList<OthersKpiExcelTemp>();
        for (OthersKpi kpi : list) {
            tempList.add(kpi.getOthersKpiExcelTemp());
        }
        othersKpiService.uploadExcelTempData(tempList);
    }


    public boolean checkExcelData(OthersKpiExcelTemp bw,List<OthersKpiExcelTemp>rowList) throws Exception{

        String gridName = bw.getGridName();//网格名称
        String account = bw.getAccount();//网格人员账号
        String dateMonth = bw.getDateMonth();//月份
        String configType = bw.getConfigType();//积分类型
        String configContext = bw.getConfigContext();//积分项目
        String score = bw.getScore();//得分
//        String remark = bw.getRemark();//扣分说明

        bw.setErrorMessage("");
        bw.setCheckFlag("1");  //默认通过校验
        bw.setCreateBy(AuthContextHolder.getLoginInfo().getOperid()+"");

        if(StringUtils.isBlank(gridName) || StringUtils.isBlank(account)
                || StringUtils.isBlank(configType) || StringUtils.isBlank(configContext)
                || StringUtils.isBlank(score) || StringUtils.isBlank(dateMonth)){
            bw.setErrorMessage("除说明项，其他字段不能为空!");
        }

        if(!AuthContextHolder.getLoginInfo().isAdmin() && !AuthContextHolder.getLoginInfo().getCity().equals(bw.getCity())){
            bw.setErrorMessage("无法导入其他地市的积分数据！");
        }
        String[] dateMonths=dateMonth.split("-");
        if(dateMonths.length!=2 || dateMonths[0].length()!=4 || dateMonths[1].length()!=2){
            bw.setErrorMessage("年月格式不正确！");
        }
        //设置支公司信息
//        List<PrvArea> areas = persistentService.findByProperty(PrvArea.class,"name",areaName);
//        if(areas.size()==0 || areas.size()>1){
//            throw new Exception("导入失败！无法找到支公司信息，请检查第"+rowIndex+"行数据！");
//        } else{
//            if(!city.equalsIgnoreCase(areas.get(0).getCity())){ //地市和网格必须对应
//                throw new Exception("导入失败！无法在地市【"+city+"】找到支公司【"+areaName+"】,请检查第"+rowIndex+"行数据！");
//            }
//            bw.setAreaid(areas.get(0).getId()+"");
//        }
        //设置网格信息
        List<BizGridInfo> grids=gridInfoService.findByFilter(new PropertyFilter(PropertyFilter.MatchType.EQ, "gridname", gridName));
        if(grids.size()==0 || grids.size()>1){
            bw.setErrorMessage("无法找到网格信息！");
        } else{
            if(!bw.getCity().equalsIgnoreCase(grids.get(0).getCity())){ //地市和网格必须对应
                bw.setErrorMessage("无法在地市【"+bw.getCity()+"】找到网格【"+gridName+"】!");
            }
            bw.setGrid(grids.get(0).getId()+"");
        }
        //设置网格人员信息
        List<PrvOperator> operids=persistentService.findByProperty(PrvOperator.class,"loginname",account);
        if(operids.size()==0 || operids.size()>1){
            bw.setErrorMessage("无法找到网格人员信息!");
        } else{
            bw.setOperid(operids.get(0).getId());
        }
        //设置积分项目id
        OthersKpiTextConfig config = new OthersKpiTextConfig();
        config.setCity(bw.getCity());
        config.setAreaid(bw.getAreaid());
        config.setType(configType);
        config.setContext(configContext);
        List<OthersKpiTextConfig> configs = othersKpiTextConfigService.findList(config);
        if(configs==null || configs.size()==0){
            bw.setErrorMessage("无法找到积分项目信息!");
        }else{
            bw.setTextConfigId(configs.get(0).getId());
        }

        //查看excel是否有重复数据
//        List<OthersKpiExcelTemp>rowList = persistentService.findByProperty(OthersKpiExcelTemp.class,"create_by",
//                AuthContextHolder.getLoginInfo().getOperid());
        for(OthersKpiExcelTemp othersKpi : rowList){
            if(bw.getGrid().equals(othersKpi.getGrid()) && bw.getOperid().longValue()==othersKpi.getOperid()
                    && bw.getDateMonth().equals(othersKpi.getDateMonth()) &&
                    configType.equals(othersKpi.getConfigType()) && configContext.equals(othersKpi.getConfigContext())){
                bw.setErrorMessage("Excel数据存在重复,【网格，网格人员,月份,积分项目】必须唯一!");
            }
        }
        //查看数据库是否存在该条记录
        if(bw.getTextConfigId()!=null &&  bw.getOperid()!=null &&
                bw.getDateMonth()!=null && bw.getGrid()!=null) {
            boolean exist = othersKpiService.exists(bw.getGrid(), bw.getOperid(), bw.getDateMonth(), bw.getTextConfigId());
            if (exist) {
                bw.setErrorMessage("该条数据在数据库中已存在!");
            }
        }
        if(StringUtils.isNotEmpty(bw.getErrorMessage())){
            bw.setCheckFlag("0");  //未通过校验
            return false;
        }
        return  true;
    }

    public Map<String, String> getStatusMap(){
        return getParamByGcode("SALARY-OTHERS-KPI-STATUS",null);
    }
    public Map<String, String> getAuditMap() throws Exception{
        Map<String,String> map = new HashMap<String,String>();
        List<PrvOperator> list = othersKpiAuditService.findAduitOperator();
        for (PrvOperator prvOperator : list) {
            map.put(prvOperator.getId().toString(),prvOperator.getName());
        }
        return map;
    }

    public OthersKpi getOthersKpi() {
        return othersKpi;
    }

    public void setOthersKpi(OthersKpi othersKpi) {
        this.othersKpi = othersKpi;
    }

}
