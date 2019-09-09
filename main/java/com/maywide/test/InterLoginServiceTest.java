package com.maywide.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.maywide.biz.core.pojo.ReturnInfo;
import com.maywide.biz.inter.pojo.login.LoginInterReq;
import com.maywide.biz.inter.pojo.login.LoginInterResp;
import com.maywide.biz.inter.pojo.querydepartment.DepartmentInterInfo;
import com.maywide.biz.inter.pojo.querydepartment.QueryDepartmentInterReq;
import com.maywide.biz.inter.pojo.queryopermenu.OperMenuInterInfo;
import com.maywide.biz.inter.service.InterLoginService;
import com.maywide.biz.prv.entity.PrvMenudef;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.PersistentService;

public class InterLoginServiceTest {
	private final Object LOCK = new Object();
	ClassPathXmlApplicationContext context = null;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;

	// private final static Logger logger = Logger.getLogger(NoticeTest.class);
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:/context/spring-context.xml" });
		entityManagerFactory = (EntityManagerFactory) context
				.getBean("entityManagerFactory");
		em = entityManagerFactory.createEntityManager();
		TransactionSynchronizationManager.bindResource(entityManagerFactory,
				new EntityManagerHolder(em));
	}

	@After
	public void tearDown() throws Exception {
		TransactionSynchronizationManager.unbindResource(entityManagerFactory);
		EntityManagerFactoryUtils.closeEntityManager(em);
	}

	@Test
	public void run() throws Exception {
		try {
			// DAO dao = (DAO) context.getBean("dao");
			/*
			 * Batch batch = new Batch(); batch.set_notnull_kind("1");
			 * dao.find(batch);
			 */

			/*
			 * //--测试queryDepartment InterLoginService interLoginService =
			 * (InterLoginService) context.getBean("interLoginService");
			 * 
			 * QueryDepartmentInterReq req = new QueryDepartmentInterReq();
			 * req.setLoginname("GZCYKFA001");
			 * 
			 * ArrayList resp = new ArrayList();
			 * 
			 * ReturnInfo retInfo = interLoginService.queryDepartment(req,
			 * resp);
			 * 
			 * System.out.println(retInfo.getCode());
			 * System.out.println(retInfo.getMessage());
			 */

			PersistentService persistentService = (PersistentService) context
					.getBean("persistentService");

			Page page = new Page();
			page.setPageSize(15);

			StringBuffer sql = new StringBuffer();
			List paramList = new ArrayList();

			sql.append(" SELECT id,       premenuid,       NAME,       linkurl,       iconid, ");
			sql.append("      ");
			sql.append("                flag,       prefix,       attr,       pinyin,       memo, ");
			sql.append("      ");
			sql.append("                bizcode,       target,       seqno, oflag1, oflag2  ");
			sql.append("      ");
			sql.append("           FROM (SELECT a.menuid    id,               a.premenuid premenuid, ");
			sql.append("      ");
			sql.append("                        a.name      NAME,               a.linkurl   linkurl, ");
			sql.append("      ");
			sql.append("                        a.iconid    iconid,               a.flag      flag, ");
			sql.append("      ");
			sql.append("                        a.prefix    prefix,               a.attr      attr, ");
			sql.append("      ");
			sql.append("                        a.pinyin    pinyin,               a.memo      memo, ");
			sql.append("      ");
			sql.append("                        a.bizcode   bizcode,               a.target    target, ");
			sql.append("      ");
			sql.append("                        a.seqno     seqno,                a.oflag1    oflag1, ");
			sql.append("                    a.oflag2    oflag2  ");
			sql.append("               FROM prv_menudef a, prv_roleprivs b ");
			sql.append("              WHERE a.menuid = b.menuid ");
			sql.append("      ");
			sql.append("                    AND a.attr = 'Y' ");
			sql.append("                AND b.roleid = ? ");
			sql.append("             UNION ");
			sql.append("      ");
			sql.append("                 SELECT a.menuid    id,               a.premenuid premenuid, ");
			sql.append("      ");
			sql.append("                        a.name      NAME,               a.linkurl   linkurl, ");
			sql.append("      ");
			sql.append("                        a.iconid    iconid,               a.flag      flag, ");
			sql.append("      ");
			sql.append("                        a.prefix    prefix,               a.attr      attr, ");
			sql.append("      ");
			sql.append("                        a.pinyin    pinyin,               a.memo      memo, ");
			sql.append("      ");
			sql.append("                        a.bizcode   bizcode,               a.target    target, ");
			sql.append("      ");
			sql.append("                        a.seqno     seqno,                a.oflag1    oflag1, ");
			sql.append("                    a.oflag2    oflag2  ");
			sql.append("               FROM prv_menudef a, prv_operprivs b ");
			sql.append("              WHERE a.menuid = b.menuid ");
			sql.append("      ");
			sql.append("                    AND a.attr = 'Y' ");
			sql.append("                AND b.operid = ?) AS menu  ");
			sql.append("      ORDER BY prefix, seqno ");
			paramList.add("1");
			paramList.add("1");

			

			page = persistentService.find(page, sql.toString(), OperMenuInterInfo.class,
					paramList.toArray());
			
			List list = page.getResult();
			long l = page.getTotalCount();

			// //--测试queryDepartment
			// InterLoginService interLoginService = (InterLoginService)
			// context.getBean("interLoginService");
			//
			// QueryDepartmentInterReq req = new QueryDepartmentInterReq();
			// req.setLoginname("GZCYKFA001");
			//
			// ArrayList<DepartmentInterInfo> resp = new ArrayList();
			//
			// ReturnInfo retInfo = interLoginService.queryDepartment(req,
			// resp);
			//
			// System.out.println(retInfo.getCode());
			// System.out.println(retInfo.getMessage());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

}
