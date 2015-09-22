package com.webapp.mybatis.xxx;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.webapp.mapper.IdGeneratorMapper;
import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class SpringContainerTest2 {

	static Log log = LogFactory.getLog(SpringContainerTest2.class);
	
	public static void main(String[] args) throws InterruptedException {
		GenericXmlApplicationContext f = new GenericXmlApplicationContext("com/webapp/mybatis/xxx/beans2.xml");
		
		SqlSessionFactory factory = f.getBean(SqlSessionFactory.class);
		SqlSession session = factory.openSession();
		IdGeneratorMapper idGen = session.getMapper(IdGeneratorMapper.class);
		MemberMapper member = session.getMapper(MemberMapper.class);
		idGen.selectByName("memberId");
		List<Member> list =  member.selectAll();
		log.info("size = " + list.size());
		Member m = new Member();
		m.setId(444444444);
		m.setEmail("111xxx@skjfsdkfjf.com");
		m.setName("skdfj");
		m.setPassword("sdkfj");
		m.setRegdate(new Date());
//		member.insert(m);
		
		
	}
}
