package com.webapp.mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.util.ResourceUtils;

import com.webapp.model.Member;

public class MybatisTest {

	public static void main(String[] args) throws IOException {
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
//		InputStream inputStream = MybatisTest.class.getClassLoader().getResourceAsStream("mybatis/mybatis_config.xml");
		
//		InputStream inputStream = Resources.getUrlAsStream("file:mybatis_xxx.xml");
//		FileInputStream inputStream = new FileInputStream("mybatis_xxx.xml");
		
//		File file = ResourceUtils.getFile("classpath:mybatis/mybatis_config.xml");
//		FileInputStream inputStream = new FileInputStream(file);
		
//		File file = ResourceUtils.getFile("file:mybatis_xxx.xml");
//		FileInputStream inputStream = new FileInputStream(file);
		
		SqlSessionFactory factory = builder.build(inputStream);
		
		SqlSession session =  factory.openSession();
		List<Member> list =  session.selectList("com.webapp.dao.MemberDao.selectAll");

		for (Member m : list)
			System.out.println(m.getId() + " " + m.getEmail());
		
		
		Member m = session.selectOne("com.webapp.dao.MemberDao.selectById", 1001);
		System.out.println(m.getId() + " " + m.getEmail());
		
		
	}

}
