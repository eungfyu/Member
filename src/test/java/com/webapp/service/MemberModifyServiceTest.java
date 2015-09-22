package com.webapp.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.webapp.exception.AlreadyExistingMemberException;
import com.webapp.exception.MemberModifyEmptyException;
import com.webapp.exception.MemberNotFoundException;
import com.webapp.model.Member;
import com.webapp.model.MemberList;

public class MemberModifyServiceTest {

	static Log log = LogFactory.getLog(MemberModifyServiceTest.class);
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle", "spring");
//		ctx.getEnvironment().setActiveProfiles("oracle", "mybatis");
//		ctx.getEnvironment().setActiveProfiles("mysql", "spring");
//		ctx.getEnvironment().setActiveProfiles("mysql", "mybatis");
		
		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml");
		ctx.refresh();
		
		MemberModifyService service = ctx.getBean(MemberModifyService.class);
		
		Member m = ctx.getBean(Member.class);
		m.setId(101000);
		m.setEmail("xxx@oracle.com");
		
		m.setPassword("qwer1234");
		m.setName("xxx");
		
		try {
			service.modify(m);
			log.info("id = " + m.getId());
		} catch (MemberModifyEmptyException e) {
			log.info("멤버 수정 에러", e);
		}
		
	}

}
