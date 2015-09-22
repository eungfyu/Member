package com.webapp.spring;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class CreateBeanTest {

	static Log log = LogFactory.getLog(CreateBeanTest.class);
	
	public static void main(String[] args) throws Exception {
		/*
		 * Object(객체)를 생성방법
		 * 
		 * 1. new 생성
		 * 2. static method 객체생성 newInstance() why Singleton
		 * 3. FactoryBean 으로 객체생성
		 */
		String resourceLocations = "com/webapp/spring/createbean.xml";
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(resourceLocations);
		
		SqlSessionTemplate session1 = ctx.getBean(SqlSessionTemplate.class);
		SqlSession session = ctx.getBean(SqlSession.class);
		
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		List<Member> list = mapper.selectAll();
		log.info("size = " + list.size());
		
	}

}
