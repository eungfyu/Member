package com.webapp.mybatis.xxx;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringContainerTest {

	static Log log = LogFactory.getLog(SpringContainerTest.class);
	
	ApplicationContext factory;
	SimpleDateFormat format;
	
	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}
	
	public void setFactory(ApplicationContext factory) {
		this.factory = factory;
	}
	
	public void print() throws InterruptedException {
			Date curr = factory.getBean(Date.class);
			log.info("Current Time = " + format.format(curr));
	}
	
	public int sum(int a, int b) {
		return a + b;
	}
	
	public int div(int a, int b) {
		return a/b;
	}
	
	public static void main(String[] args) throws InterruptedException {
		GenericXmlApplicationContext f = new GenericXmlApplicationContext("com/webapp/mybatis/blob/beans.xml");
		
		try {
			SpringContainerTest t = f.getBean(SpringContainerTest.class);
	//		t.print();
	//		t.sum(10, 15);
			t.div(10, 10);
			log.info(t.getClass());
		} catch (Exception e) {
			
		}
	}
	
	
}
