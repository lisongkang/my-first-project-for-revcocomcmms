package com.maywide.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.print.attribute.standard.Severity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.maywide.biz.extendattr.pojo.PrvAttrruleBo;
import com.maywide.biz.extendattr.service.PrvAttrruleService;
import com.maywide.biz.system.entity.PrvSysparam;
import com.maywide.common.datatransfer.service.MappingConfigService;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.service.PersistentService;
import com.maywide.core.util.BeanUtils;

/**
 * Tests {@link DefaultIoFilterChainBuilder}.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class DataTest {
	private final Object LOCK = new Object();
	ClassPathXmlApplicationContext context = null;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;
	PersistentService persistentService = null;
	MappingConfigService queryMappingService = null;
	//private final static Logger logger = Logger.getLogger(NoticeTest.class);
    @Before
    public void setUp() throws Exception {
        context = new ClassPathXmlApplicationContext(new String[] { "classpath:/context/spring-context.xml" });
		entityManagerFactory = (EntityManagerFactory) context.getBean("entityManagerFactory");
		em = entityManagerFactory.createEntityManager();
		TransactionSynchronizationManager.bindResource(entityManagerFactory, new EntityManagerHolder(em));
    }

    @After
    public void tearDown() throws Exception {
        TransactionSynchronizationManager.unbindResource(entityManagerFactory);
		EntityManagerFactoryUtils.closeEntityManager(em);
    }

    @Test
    public void run() throws Exception {
    	try {
    		
    	//	BizSurveyListService service = (BizSurveyListService) context.getBean("bizSurveyListService");
    	
    	//	service5.save(bizQaRelation2);
    		
    		
    		PrvAttrruleService  service = (PrvAttrruleService) context.getBean("prvAttrruleService");
    		
    		PrvAttrruleBo bo =new  PrvAttrruleBo();
    		bo.setAttrcode("test");
    		bo.setAttrname("123");
    		bo.setDefaultvalue("");
    		bo.setIfnecessary("Y");
    		bo.setValueparam("3");
    		bo.setValuesrc("1");
    		List<String> cityList = new ArrayList<String>();
    		cityList.add("Fs");
    		cityList.add("GZ");
    		//service.saveSysAttrArea(15L, cityList);
    		//service.deleteSysAttrAreaByattId(15L);
    	//	List<PrvSysparam> list = service.getCityMapById("40");
    	//	System.out.println(list.size());
    		//System.out.println(service.getMnameById("36"));
    	   // Long id =  service.doSave(bo);
    	   // System.out.println(id);
    		//int id = service.isOktoDelorUpdate("1");
    		//System.out.println(id);
    		//DAO dao = (DAO) context.getBean("dao");
    		//persistentService = (PersistentService) context.getBean("persistentService");
    		/*queryMappingService = (MappingConfigService) context.getBean("queryMappingService");
    		
    		List list = queryMappingService.queryData("com.maywide.biz.prv.entity.PrvArea");
    		System.out.println(list.size());*/
    		/*Batch batch = new Batch();
    		batch.set_notnull_kind("1");
    		dao.find(batch);*/
    		
    		/*SurveyService surveyService = (SurveyService) context.getBean("surveyService");
    		List<Candidate> candidates = surveyService.queryCandidates("0");
    		
    		for (Candidate candidate : candidates) {
    			//System.out.println(candidate.getName() + " - " + candidate.getSex().getMname());
    		}
    		
    		surveyService.votes(1L, "66881");*/
    		/*SessionFactory sf = ((HibernateEntityManagerFactory) entityManagerFactory).getSessionFactory();
    		Map<String, ClassMetadata> map = sf.getAllClassMetadata();
			entitySet.addAll(map.keySet());*/
    		//Map<String, ClassMetadata> map = sf.getAllClassMetadata();
    		//for (String key : map.keySet()) {
    			//System.out.println(key + " = " + map.get(key).);
    		//}
    		//System.out.println(map.size());
    		/*String className = "com.maywide.biz.core.entity.ErrorConvert";
    		Class clazz = Class.forName(className);
    		
    		Field field = BeanUtils.getDeclaredField(clazz, "convertId");
    		
    		System.out.println(field.getType().getName());*/
    		
    		
    		
    		//PropertyFilter filter = new PropertyFilter(clazz, "interfaceName", "QUE_SERVPRDINFO");
    		
    		//persistentService.findUniqueByProperty(clazz, "interfaceName", )
    		
    		//String retval = transProperty("ErrorConvert", "interfaceName", "convertId", "1");
    		//System.out.println(retval);
    		
    		/*PrvDepartmentService prvDepartmentService = (PrvDepartmentService) context.getBean("prvDepartmentService");
    		List<TreeMenuVO> tree = prvDepartmentService.findTreeList(null);
    		for (TreeMenuVO vo : tree) {
    			System.out.println(vo.getId() + "," + vo.getName() + "," + vo.getPid() + "," 
    					+ vo.getIsParent() + ",");
    		}*/
    		
    		/*String content = "<xml><appid><![CDATA[wxa7621a1ce2ed3064]]></appid><attach><![CDATA[test]]></attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1220331001]]></mch_id><nonce_str><![CDATA[72zcp2dbhzczsxxa10033028wha4v4fi]]></nonce_str><openid><![CDATA[oyJ5ot6GCkxDshfJ5i0ojNpagHMQ]]></openid><out_trade_no><![CDATA[1000000002]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[79111538CF141828A624553EA7EAD618]]></sign><time_end><![CDATA[20151010112059]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[1007760614201510101147259346]]></transaction_id></xml>";
    		PayCallbackNotify payCallbackNotify = (PayCallbackNotify) Util.getObjectFromXML(content, PayCallbackNotify.class);
			persistentService.save(payCallbackNotify);*/
    		
    		//WeixinPayService weixinPayService = (WeixinPayService) context.getBean("weixinPayService");
    		//weixinPayService.refund("1091294");
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    private static Set<String> entitySet = new HashSet();
    
    public String transProperty(String className, String propertyName, String conditionProperty, String conditionValue) {
		String fullName = getFullName(className);
		if (fullName == null) return null;
		
		try {
			Class clazz = Class.forName(fullName);
			Field field = BeanUtils.getDeclaredField(clazz, conditionProperty);
			Object propertyValue = getPropertyValue(field.getType(), conditionValue);
			Object obj = persistentService.findUniqueByProperty(clazz, conditionProperty, propertyValue);
			
			Object propertyObj = BeanUtils.getFieldValue(obj, propertyName);
			
			if (propertyObj != null) return propertyObj.toString();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		return null;
	}
	
	private String getFullName(String className) {
		for (String entityName : entitySet) {
			if (entityName.endsWith("." + className)) {
				return entityName;
			}
		}
		return null;
	}
	
	private Object getPropertyValue(Class clazz, String propertyValue) {
		Object retval = propertyValue;
		if ("short".equals(clazz.getSimpleName().toLowerCase())) {
			retval = Short.valueOf(propertyValue);
		} else if ("int".equals(clazz.getSimpleName().toLowerCase()) || "integer".equals(clazz.getSimpleName().toLowerCase())) {
			retval = Integer.valueOf(propertyValue);
		} else if ("long".equals(clazz.getSimpleName().toLowerCase())) {
			retval = Long.valueOf(propertyValue);
		}
		
		return retval;
	}
}
