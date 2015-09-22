package com.webapp.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.webapp.mapper.IdGeneratorMapper;

public class HashMapTest {

	static Log log = LogFactory.getLog(HashMapTest.class);
	public static void main(String[] args) throws IOException {
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
		SqlSessionFactory factory = builder.build(inputStream, "oracle");
		SqlSession session =  factory.openSession(false);

//		Map<String, Object> map = session.selectOne("com.webapp.mapper.IdGeneratorMapper.selectByName", "memberId");
		IdGeneratorMapper mapper = session.getMapper(IdGeneratorMapper.class);
		Map<String, Object> map = mapper.selectByName("memberId");
		
		String name = (String) map.get("name");
		int nextval = ((BigDecimal) map.get("nextval")).intValue() ;
		int incval = ((BigDecimal) map.get("incval")).intValue();	
		int seqno = nextval + incval;
		
		log.info("name = " + name + 
				 ", nextval = " + nextval +
				 ", incval = " + incval +
				 ", seqno = " + seqno
				);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nextval", seqno);
		param.put("name", "memberId");
//		session.update("com.webapp.mapper.IdGeneratorMapper.update", param);
		mapper.update(param);
		
		session.commit();
		session.close();
	}

}
