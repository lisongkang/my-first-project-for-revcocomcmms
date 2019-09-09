package com.maywide.core.web.listener;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.common.collect.Maps;
import com.maywide.biz.ass.assdata.job.DayAssDataStatJob;
import com.maywide.biz.ass.assdata.job.MonAssDataStatJob;
import com.maywide.biz.ass.assdata.job.OrderAssDataJob;
import com.maywide.biz.ass.assdata.job.UpdateAchievementsJob;
import com.maywide.biz.ass.assdata.job.UpdateSaleOrderPointJob;
import com.maywide.biz.ass.assdata.job.UpdateStaAnalysisJob;
import com.maywide.biz.core.service.ParamService;
import com.maywide.biz.core.service.ParamSettingService;
import com.maywide.biz.core.servlet.SpringBeanUtil;
import com.maywide.biz.core.servlet.SysConfig;
import com.maywide.biz.inter.job.BizGrpArrearDetailSync;
import com.maywide.biz.inter.job.UpdateAccountOpenCustGroupMsgJob;
import com.maywide.biz.inter.job.UpdateCustWithDrawJob;
import com.maywide.biz.pay.unify.job.UploadUnifyBillFileJob;
import com.maywide.biz.remind.job.RemindBatchStatJob;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.core.annotation.MetaData;
import com.maywide.core.cons.Datas;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.dao.jpa.ExtPersistenceUnitPostProcessor;
import com.maywide.core.nio.NettyServerBootstrap;

/**
 * Spring容器加载“之后”的ServletContextListener
 */
public class ApplicationContextPostListener implements ServletContextListener {

	private final Logger logger = LoggerFactory.getLogger(ApplicationContextPostListener.class);

	private final static ExecutorService executor = Executors.newCachedThreadPool();

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.debug("Invoke ApplicationContextPostListener contextInitialized");
		try {
			ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event
					.getServletContext());

			SpringContextHolder.setApplicationContext(applicationContext);

			ServletContext sc = event.getServletContext();
			String appName = sc.getServletContextName();
			logger.info("[{}] init context ...", appName);

			ParamSettingService paramSettingService = SpringContextHolder.getBean(ParamSettingService.class);
			paramSettingService.getErrorConvertList(null);

			ParamService paramService = SpringContextHolder.getBean(ParamService.class);

			Map<String, Map<? extends Serializable, String>> scEnumsMap = Maps.newHashMap();
			//存储枚举名称和对应Class映射的Map
			Map<String, Class<?>> enumShortNameClassMapping = Maps.newHashMap();

			ExtPersistenceUnitPostProcessor persistenceUnitPostProcessor = (ExtPersistenceUnitPostProcessor) applicationContext
					.getBean(PersistenceUnitPostProcessor.class);

			MutablePersistenceUnitInfo pui = persistenceUnitPostProcessor.getMutablePersistenceUnitInfo();

			//循环所有Entity对象中的Enum定义，转换成对应的Map结构数据存入ServletContext属性中，便于Web层标签直接方便获取
			for (Class<?> entityClass : convertClassNamesToClasses(pui.getManagedClassNames())) {
				logger.trace("Post processing entity: {}", entityClass);
				Field[] fields = entityClass.getDeclaredFields();
				for (Field field : fields) {
					Class fieldClass = field.getType();
					if (fieldClass.isEnum()) {
						String simpleName = fieldClass.getSimpleName();
						Class<?> existClass = enumShortNameClassMapping.get(simpleName);
						if (existClass == null) {
							//logger.info(" - Put Enum short name mapping: {}={}", simpleName, fieldClass);
							enumShortNameClassMapping.put(simpleName, fieldClass);
						} else {
							if (!existClass.equals(fieldClass)) {
								throw new IllegalStateException("Duplicate simple name: " + simpleName + ", class1="
										+ existClass + ", class2=" + fieldClass);
							} else {
								continue;
							}
						}

						Map<java.lang.Enum, String> enumDataMap = Maps.newLinkedHashMap();
						for (Field enumfield : fieldClass.getFields()) {
							MetaData entityComment = enumfield.getAnnotation(MetaData.class);
							String value = enumfield.getName();
							if (entityComment != null) {
								value = entityComment.value();
							}
							enumDataMap.put(Enum.valueOf(fieldClass, enumfield.getName()), value);
						}
						String attrName = StringUtils.uncapitalize(fieldClass.getSimpleName());
						scEnumsMap.put(attrName, enumDataMap);
					}
				}
			}

			//设置默认的boolean类型数据转义显示
			Map<Boolean, String> booleanLabelMap = Maps.newLinkedHashMap();
			booleanLabelMap.put(Boolean.TRUE, "是");
			booleanLabelMap.put(Boolean.FALSE, "否");
			scEnumsMap.put("booleanLabel", booleanLabelMap);

			Map<Integer, String> statusLabelMap = Maps.newLinkedHashMap();
			statusLabelMap.put(1, "是");
			statusLabelMap.put(0, "否");
			scEnumsMap.put("statusLabel", statusLabelMap);

			Map<String, String > sysYesNoMap = Maps.newLinkedHashMap();
			sysYesNoMap.put("Y","是");
			sysYesNoMap.put("N","否");
			scEnumsMap.put("sysYesNo", sysYesNoMap);

			Map<Integer,String> ejecttypeMap = Maps.newLinkedHashMap();
			ejecttypeMap.put(2,"退套餐");
			ejecttypeMap.put(0,"退产品");
			scEnumsMap.put("ejecttype", ejecttypeMap);

			scEnumsMap.put("prvcity", paramService.getSelectData("PRV_CITY"));

			if (logger.isInfoEnabled()) {
				logger.info("Put enums data to ServletContext: ");
				for (Map.Entry<String, Map<? extends Serializable, String>> me : scEnumsMap.entrySet()) {
					logger.info(" -  {} = {}", me.getKey(), me.getValue());
				}
			}
			sc.setAttribute("enums", scEnumsMap);

			//初始化系统参数
			LoadDataTask loadDataTask = new LoadDataTask();
			executor.submit(loadDataTask);

			new Thread(new Runnable() {
				@Override
				public void run() {
					//new MessageServer().startServer();
					try {
						NettyServerBootstrap bootstrap = new NettyServerBootstrap();
					} catch (Exception e) {
						logger.error("启动nio服务端出错，", e);
					}

				}
			}).start();

			//TaskService taskService = (TaskService) SpringContextHolder.getBean(TaskService.class);
			//taskService.start();

			// 统计考核内容定时器
			executeJobForTimer();


			if (SysConfig.getDBconnection().equals("Y")) {
				new Thread(new Runnable() {

					@Override
					public void run() {

						try{
							ClassPathXmlApplicationContext dbcontext = new ClassPathXmlApplicationContext(
									new String[] { "classpath:/context/db-oracle-context.xml" });
							SpringBeanUtil.setDBContext(dbcontext);
						}catch(Exception e){
							logger.error("报表数据库连接失败");
						}

					}
				}).start();


				new Thread(new Runnable() {

					@Override
					public void run() {
						try{
							ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
									new String[] { "classpath:/context/oracle-context.xml" });
							SpringBeanUtil.setContext(context);
						}catch(Exception e){
							logger.error("BI数据库连接失败");
						}
					}
				}).start();
				
				//ywp 增加portal数据库连接
				new Thread(new Runnable() {

					@Override
					public void run() {
						try{
							ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
									new String[] { "classpath:/context/portal-mysql-context.xml" });
							SpringBeanUtil.setPortalContext(context);
						}catch(Exception e){
							logger.error("portal数据库连接失败");
						}
					}
				}).start();

				//lsk增加小额工程物料获取第三方数据库
				new Thread(new Runnable() {

					@Override
					public void run() {
						try{
							ClassPathXmlApplicationContext nccontext = new ClassPathXmlApplicationContext(
									new String[] { "classpath:/context/nc-oracle-context.xml" });
							SpringBeanUtil.setNccontext(nccontext);
						}catch(Exception e){
							logger.error("NC数据库连接失败");
						}
					}
				}).start();


				//billing数据库
				new Thread(new Runnable() {
					@Override
					public void run() {
						try{
							ClassPathXmlApplicationContext nccontext = new ClassPathXmlApplicationContext(
									new String[] { "classpath:/context/billing-oracle-context.xml" });
							SpringBeanUtil.setBillingcontext(nccontext);
						}catch(Exception e){
							logger.error("BILLING数据库连接失败");
						}
					}
				}).start();


				//lyz增加ods获取第三方数据库
				new Thread(new Runnable() {

					@Override
					public void run() {
						try{
							ClassPathXmlApplicationContext odsContext = new ClassPathXmlApplicationContext(
									new String[] { "classpath:/context/oracle-oss-context.xml" });
							SpringBeanUtil.setOdsContext(odsContext);
						}catch(Exception e){
							logger.error("ODS数据库连接失败");
						}
					}
				}).start();


				new Thread(new Runnable() {

					@Override
					public void run() {
						updateAchievementsData();		
						updateAccountOpenMsgData();
						updateCustWithDrawMsgData();
					}
				}).start();
			}
		} catch (Exception e) {
			logger.error("error detail:", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.debug("Invoke ApplicationContextPostListener contextDestroyed");
	}

	/**
	 * 复制实现Apache Commons Lang的ClassUtils对应接口实现
	 * 主要是为了解决把应用的jar文件以tomcat\lib共享模式部署类加载机制访问问题
	 * @param classNames
	 * @return
	 */
	private List<Class<?>> convertClassNamesToClasses(List<String> classNames) {
		if (classNames == null) {
			return null;
		}
		List<Class<?>> classes = new ArrayList<Class<?>>(classNames.size());
		for (String className : classNames) {
			try {
				classes.add(Class.forName(className));
			} catch (Exception ex) {
				classes.add(null);
			}
		}
		return classes;
	}

	private class LoadDataTask implements Runnable {
		public void run() {
			try {
				while (true) {
					Datas.initData();
					Thread.sleep(100 * 60 * 1000); //10分钟
				}
			} catch (Exception e) {
				logger.error("加载数据出错", e);
			}
		}
	}


	private boolean executeJobForTimer() {

		// 月数据统计线程
		Thread monStaticTh = new Thread(new Runnable() {
			@Override
			public void run() {
				executeMonStasticJobForTimer();
			}
		});
		monStaticTh.setName("MON-STASTIC-THREAD");
		monStaticTh.start();

		// 日数据统计线程
		Thread dayStaticTh = new Thread(new Runnable() {
			@Override
			public void run() {
				executeDayStasticJobForTimer();
			}
		});
		dayStaticTh.setName("DAY-STASTIC-THREAD");
		dayStaticTh.start();

		// 日数据预警任务统计线程
		Thread remindBatchStaticTh = new Thread(new Runnable() {
			@Override
			public void run() {
				executeRemindBatchStasticJobForTimer();
			}
		});
		remindBatchStaticTh.setName("REMIND_BATCH-STASTIC-THREAD");
		remindBatchStaticTh.start();

		Thread updateAnalysisTh = new Thread(new Runnable() {

			@Override
			public void run() {
				update_sta_analysis();
			}
		});
		updateAnalysisTh.setName("UPDATE_STA_ANALYSIS-THREAD");
		updateAnalysisTh.start();

		Thread updateSaleOrderTh = new Thread(new Runnable() {

			@Override
			public void run() {
				update_sales_order_point();
			}
		});
		updateSaleOrderTh.setName("UPDATE_SALE_ORDER_POINT-THREAD");
		updateSaleOrderTh.start();

		if(StringUtils.isBlank(SysConfig.getServiceCity())){

			//定时清理过期未支付订单
			Thread orderCleanStaticTh = new Thread(new Runnable() {

				@Override
				public void run() {
					executeOrderStasticJobForTimer();	
				}
			});
			orderCleanStaticTh.setName("ORDER_CLEAN-STASTIC-THREAD");
			orderCleanStaticTh.start();

			//定时生成上传统一支付对账文件
			if(StringUtils.isBlank(SysConfig.getServiceCity())){
				new Thread(new Runnable() {

					@Override
					public void run() {
						produceUnifyBillFile();
					}
				}).start();
			}
		}
		return true;
	}

	/**
	 * 执行月数据统计的定时任务
	 * @return boolean
	 * @throws ParseException 
	 */
	private boolean executeMonStasticJobForTimer(){
		try{
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);
			PrvSysparam param= paramService.getData("MON_QUARTZ_TIMER", "EXPRE_STRING");
			String expreString = null;
			if (param == null || StringUtils.isEmpty(param.getData())) {
				expreString = "0 30 23 1 * ?"; // 每月月初1号23点30分开始跑任务
			} else {
				expreString = param.getData();
			}

			String jobName = "mon_ass_data_stat_job";
			String groupName = "mon_ass_data_stat_group";
			String triggerName = "mon_ass_data_stat_trigger";

			JobDetail jobDetail = JobBuilder.newJob(MonAssDataStatJob.class)
					.withIdentity(jobName, groupName).build();

			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "mon_ass_data_stat_scheduler");
			props.put("org.quartz.threadPool.threadCount", "10");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			Scheduler scheduler = schedulerFactory.getScheduler();


			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();

			return true;
		} catch (SchedulerException e1){
			logger.error("schedule Job fail!", e1);
			return false;
		} catch (Exception e) {
			logger.error("schedule Job fail!", e);
			return false;
		}

	}

	/**
	 * 执行日数据统计的定时任务
	 * @return boolean
	 * @throws ParseException 
	 */
	private boolean executeDayStasticJobForTimer(){
		try{
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);
			PrvSysparam param= paramService.getData("DAY_QUARTZ_TIMER", "EXPRE_STRING");
			String expreString = null;
			if (param == null || StringUtils.isEmpty(param.getData())) {
				expreString = "0 0 6 * * ?"; // 每天的06点00分开始跑任务
			} else {
				expreString = param.getData();
			}

			String jobName = "day_ass_data_stat_job";
			String groupName = "day_ass_data_stat_group";
			String triggerName = "day_ass_data_stat_trigger";

			JobDetail jobDetail = JobBuilder.newJob(DayAssDataStatJob.class)
					.withIdentity(jobName, groupName).build();

			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "day_ass_data_stat_scheduler");
			props.put("org.quartz.threadPool.threadCount", "10");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			Scheduler scheduler = schedulerFactory.getScheduler();


			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();

			return true;
		} catch (SchedulerException e1){
			logger.error("schedule Job fail!", e1);
			return false;
		} catch (Exception e) {
			logger.error("schedule Job fail!", e);
			return false;
		}

	}

	/**
	 * 执行日数据预警任务统计的定时任务
	 * @return boolean
	 * @throws ParseException 
	 */
	private boolean executeRemindBatchStasticJobForTimer(){
		try{
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);
			PrvSysparam param= paramService.getData("REMIND_BATCH_QUARTZ_TIMER", "EXPRE_STRING");
			String expreString = null;
			if (param == null || StringUtils.isEmpty(param.getData())) {
				expreString = "0 30 23 * * ?"; // 每天的23点30分开始跑任务
			} else {
				expreString = param.getData();
			}

			String jobName = "remind_batch_stat_job";
			String groupName = "remind_batch_stat_group";
			String triggerName = "remind_batch_stat_trigger";

			JobDetail jobDetail = JobBuilder.newJob(RemindBatchStatJob.class)
					.withIdentity(jobName, groupName).build();

			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "remind_batch_stat_scheduler");
			props.put("org.quartz.threadPool.threadCount", "10");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			Scheduler scheduler = schedulerFactory.getScheduler();


			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();

			return true;
		} catch (SchedulerException e1){
			logger.error("schedule Job fail!", e1);
			return false;
		} catch (Exception e) {
			logger.error("schedule Job fail!", e);
			return false;
		}

	}

	private boolean executeOrderStasticJobForTimer(){
		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
		String jobName = "order_clean_stat_job";
		String groupName = "order_clean_stat_group";
		String triggerName = "order_clean_stat_trigger";
		JobKey jobKey = new JobKey(jobName, groupName);
		Scheduler scheduler = null;
		try {
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);
			PrvSysparam param= paramService.getData("ORDER_CLEAN_QUARTZ_TIMER", "EXPRE_STRING");
			String expreString = null;
			if (param == null || StringUtils.isBlank(param.getData())) {
				expreString = "1 0 0 * * ?"; // 每天的0点0分开始跑任务(现在测试阶段 ,每1分钟跑一次)
			} else {
				expreString = param.getData();
			}

			JobDetail jobDetail  = JobBuilder.newJob(OrderAssDataJob.class).
					withIdentity(jobKey)
					.build();

			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();


			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "order_clean_stat_scheduler");
			props.put("org.quartz.threadPool.threadCount", "2");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			scheduler = schedulerFactory.getScheduler();


			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			return true;
		}catch (SchedulerException e1){
			logger.error("schedule Job fail!", e1);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(scheduler.checkExists(jobKey)){
					scheduler.deleteJob(jobKey);
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 定时更新问卷数据统计结果
	 * @return
	 * @throws Exception 
	 */
	private boolean update_sta_analysis() {
		try{
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);
			PrvSysparam param= paramService.getData("UPDATE_STA_ANALYSIS_TIMER", "EXPRE_STRING");
			String expreString = null;
			if (param == null || StringUtils.isBlank(param.getData())) {
				expreString = "0 1 0 * * ? "; // 每天的0点0分开始跑任务(现在测试阶段 ,每1分钟跑一次)
			} else {
				expreString = param.getData();
			}
			String jobName = "update_sta_analysis_job";
			String groupName = "update_sta_analysis_group";
			String triggerName = "update_sta_analysis_trigger";
			JobDetail jobDetail  = JobBuilder.newJob(UpdateStaAnalysisJob.class).
					withIdentity(jobName, groupName).build();
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "update_sta_analysis_scheduler");
			props.put("org.quartz.threadPool.threadCount", "10");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			Scheduler scheduler = schedulerFactory.getScheduler();


			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			return true;
		}catch(Exception e){
			logger.error("update_sta_analysis fail!", e);
			return false;
		}

	}

	/**
	 * 定时更新销售积分
	 * @return
	 */
	private boolean update_sales_order_point(){
		try{
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);
			PrvSysparam param= paramService.getData("UPDATE_STA_ORDER_POINT_TIMER", "EXPRE_STRING");
			String expreString = null;
			if (param == null || StringUtils.isBlank(param.getData())) {
				expreString = "0 1 0 * * ?  ";
			} else {
				expreString = param.getData();
			}
			String jobName = "update_sales_order_point_job";
			String groupName = "update_sales_order_point_group";
			String triggerName = "update_sales_order_point_trigger";
			JobDetail jobDetail  = JobBuilder.newJob(UpdateSaleOrderPointJob.class).
					withIdentity(jobName, groupName).build();
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "update_sales_order_point_scheduler");
			props.put("org.quartz.threadPool.threadCount", "10");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			Scheduler scheduler = schedulerFactory.getScheduler();


			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			return true;
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * 定时上传统一支付平台对账文件
	 * @return
	 */
	@Deprecated
	private boolean uploadUnifyBillFile(){
		try{
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);
			PrvSysparam param= paramService.getData("UPLOAD_UNIFY_BILL_FILE_TIMER", "EXPRE_STRING");
			String expreString = null;
			if (param == null || StringUtils.isBlank(param.getData())) {
				expreString = "0 0 2-18 * * ?";
			} else {
				expreString = param.getData();
			}

			String jobName = "upload_unify_bill_file_job";
			String groupName = "upload_unify_bill_file_group";
			String triggerName = "upload_unify_bill_file_trigger";
			JobDetail jobDetail  = JobBuilder.newJob(UploadUnifyBillFileJob.class).
					withIdentity(jobName, groupName).build();
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "upload_unify_bill_file_scheduler");
			props.put("org.quartz.threadPool.threadCount", "10");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			Scheduler scheduler = schedulerFactory.getScheduler();


			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean produceUnifyBillFile(){
		try{
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);
			PrvSysparam param= paramService.getData("UPLOAD_UNIFY_BILL_FILE_TIMER2", "EXPRE_STRING");
			String expreString = null;
			if (param == null || StringUtils.isBlank(param.getData())) {
				expreString = "0 0 1 * * ?";
			} else {
				expreString = param.getData();
			}

			String jobName = "upload_unify_bill_file_job";
			String groupName = "upload_unify_bill_file_group";
			String triggerName = "upload_unify_bill_file_trigger";
			JobDetail jobDetail  = JobBuilder.newJob(UploadUnifyBillFileJob.class).
					withIdentity(jobName, groupName).build();
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "upload_unify_bill_file_scheduler");
			props.put("org.quartz.threadPool.threadCount", "10");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			Scheduler scheduler = schedulerFactory.getScheduler();


			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	private boolean updateAchievementsData(){
		try {
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);

			PrvSysparam param = paramService.getData("UPDATE_ACHIEVEMENTS_DATA_TIMER", "EXPRE_STRING");

			String expreString = null;
			if (param == null || StringUtils.isBlank(param.getData())) {
				expreString = "0 0 1 * * ? ";
			} else {
				expreString = param.getData();
			}
			String jobName = "update_achievements_data_job";
			String groupName = "update_achievements_data_group";
			String triggerName = "update_achievements_data_trigger";
			JobDetail jobDetail  = JobBuilder.newJob(UpdateAchievementsJob.class).
					withIdentity(jobName, groupName).build();
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "update_achievements_data_scheduler");
			props.put("org.quartz.threadPool.threadCount", "10");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			Scheduler scheduler = schedulerFactory.getScheduler();

			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * scope=1代表使用新修改的代码可以提醒给领导和省公司，其他代表先不更新，等到测试通过后只要把scope改为1，就会调用另一个job
	 * 1:用data,0用：fmt
	 */
	private boolean updateAccountOpenMsgData(){
		try {
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);
			PrvSysparam param = paramService.getData("UPDATE_ACCOUNTOPENMSG_DATA_TIMER", "BIZ_GRP_ARREAR_DETAIL_SYNC");
			boolean flag = false;
			if(param != null){
				flag = "1".equals(param.getScope()+"");
			}
			String expreString = null;
			if (param == null || StringUtils.isBlank(param.getData())) {
				expreString = "0 0/30 * * * ? ";
			} else {
				if(flag){
					expreString = param.getData();
				}else{
					expreString = param.getFmt();
				}
			}
			String jobName = "update_accountopenmsg_data_job";
			String groupName = "update_accountopenmsg_data_group";
			String triggerName = "update_accountopenmsg_data_trigger";
			//ywp 修改同步的类
			JobDetail jobDetail  = JobBuilder.newJob(UpdateAccountOpenCustGroupMsgJob.class).
					withIdentity(jobName, groupName).build();
			if(flag){
				jobDetail  = JobBuilder.newJob(BizGrpArrearDetailSync.class).
						withIdentity(jobName, groupName).build();
			}
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "update_accountopenmsg_data_scheduler");
			props.put("org.quartz.threadPool.threadCount", "10");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			Scheduler scheduler = schedulerFactory.getScheduler();

			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	private boolean updateCustWithDrawMsgData(){
		try {
			ParamService paramService = SpringContextHolder.getBean(ParamService.class);

			PrvSysparam param = paramService.getData("UPDATE_CUSTWITHDRAWMSG_DATA_TIMER", "EXPRE_STRING");

			String expreString = null;
			if (param == null || StringUtils.isBlank(param.getData())) {
				expreString = "0 0/30 * * * ? ";
			} else {
				expreString = param.getData();
			}
			String jobName = "update_custwithdrawmsg_data_job";
			String groupName = "update_custwithdrawmsg_data_group";
			String triggerName = "update_custwithdrawmsg_data_trigger";
			JobDetail jobDetail  = JobBuilder.newJob(UpdateCustWithDrawJob.class).
					withIdentity(jobName, groupName).build();
			CronTrigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(triggerName, groupName)
					.withSchedule(CronScheduleBuilder.cronSchedule(expreString))
					.build();

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties props = new Properties();
			props.put("org.quartz.scheduler.instanceName", "update_custwithdrawmsg_data_scheduler");
			props.put("org.quartz.threadPool.threadCount", "10");
			props.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
			schedulerFactory.initialize(props);
			Scheduler scheduler = schedulerFactory.getScheduler();

			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
