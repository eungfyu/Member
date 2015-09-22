package com.webapp.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.webapp.exception.AlreadyExistingMemberException;
import com.webapp.exception.MemberNotFoundException;
import com.webapp.model.Member;
import com.webapp.model.MemberList;

public class MemberRegisterServiceTest {

	static Log log = LogFactory.getLog(MemberRegisterServiceTest.class);
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//		ctx.getEnvironment().setActiveProfiles("oracle", "spring");
		ctx.getEnvironment().setActiveProfiles("oracle", "mybatis");
//		ctx.getEnvironment().setActiveProfiles("mysql", "spring");
//		ctx.getEnvironment().setActiveProfiles("mysql", "mybatis");
		
		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml");
		ctx.refresh();
		
		MemberRegisterService service = ctx.getBean(MemberRegisterService.class);
		MemberInfoService infoService = ctx.getBean(MemberInfoService.class);
		
		Member m = ctx.getBean(Member.class);
		m.setEmail(m.getEmail() + "xxx111");
		try {
			service.register(m);
			log.info("id = " + m.getId());
		} catch (AlreadyExistingMemberException e) {
			log.info("멤버가 존재합니다", e);
		}
		
	}

}
