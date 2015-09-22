package com.webapp.dao;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.webapp.model.Member;

public class MemberDaoTest {

	static Log log = LogFactory.getLog(MemberDaoTest.class);
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle");
//		ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml");
		ctx.refresh();
		
		MemberDao dao = ctx.getBean("mybatisMemberDao", MemberDao.class);
//		MemberDao dao = ctx.getBean("springMemberDao", MemberDao.class);
		
		List<Member> list = dao.selectAll();
		for (Member m : list)
			System.out.println(m.getId() + " " + m.getEmail());
		
		log.info("totalItem = " + dao.selectTotalCount());
		
	}

}
