package com.webapp.idgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class MySQLAutoIncrementTest {
	
	static Log log = LogFactory.getLog(MySQLAutoIncrementTest.class);

	public static void main(String[] args) throws SQLException, IOException {
//		jdbc();
//		jdbcTemplate();
//		SqlSession();
		SqlSessionTemplate();
	}
	
	static void SqlSessionTemplate() throws IOException, SQLException {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml");
		ctx.refresh();
		
		DataSource ds = (DataSource) ctx.getBean("dataSource");
		
		Connection con = ds.getConnection();
		log.info("db = " + con.getMetaData().getDatabaseProductName());
		log.info("version = " + con.getMetaData().getDatabaseProductVersion());
		log.info("major = " + con.getMetaData().getDatabaseMajorVersion());
		log.info("minor = " + con.getMetaData().getDatabaseMinorVersion());
		
		MemberMapper mapper = ctx.getBean(MemberMapper.class);
		Member member = ctx.getBean(Member.class);
		member.setEmail(member.getEmail() + (int)(Math.random()*1000000));
		log.info(member.getRegdate());

		mapper.insert(member);
		log.info("id = " + member.getId());
		
	}
	
	static void SqlSession() throws IOException {
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
//		SqlSessionFactory factory =  builder.build(inputStream, "mysql");
		SqlSessionFactory factory =  builder.build(inputStream, "oracle");
		
		SqlSession session = factory.openSession(false);
		
		Member parameter = new Member();
		parameter.setEmail("xxx@mysql.com" + (int)(Math.random()*1000));
		parameter.setName("홍길동");
		parameter.setPassword("1234");
		parameter.setRegdate(new Date());
		
		session.insert("com.webapp.mapper.MemberMapper.insert", parameter);
		log.info("id = " + parameter.getId());
		
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		
		parameter = new Member();
		parameter.setEmail("xxx@mysql.com" + (int)(Math.random()*1000));
		parameter.setName("홍길동");
		parameter.setPassword("1234");
		parameter.setRegdate(new Date());
		
		mapper.insert(parameter);
		log.info("id = " + parameter.getId());
		
		session.commit();
		
	}
	
	static void jdbcTemplate() throws SQLException {
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.load("classpath:spring/beans.xml");
		ctx.refresh();
		
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		
		final String insert = "insert into member " +
					 		  "(email, password, name, regdate) " +
					 		  "values " +
					 		  "(?, 'yyy', 'xxx', '2015/08/11') ";
		
		KeyHolder keyholder = new GeneratedKeyHolder();
		
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, (int)(Math.random()*10000000));
				return pstmt;
			}
		}, keyholder);
		
		log.info("key = " + keyholder.getKey().intValue());
		
		
	}
	
	static void jdbc() throws SQLException {
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.load("classpath:spring/beans.xml");
		ctx.refresh();
		
		BasicDataSource ds = ctx.getBean(BasicDataSource.class);
		log.info(ds.getUrl());
		
		Connection con = ds.getConnection();
		
		String insert = "insert into member " +
				 		"(email, password, name, regdate) " +
				 		"values " +
				 		"( ?, 'yyy', 'xxx', '2015/08/11') ";
		PreparedStatement pstmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, "" + (int)(Math.random()*10000));
		pstmt.executeUpdate();
		
		ResultSet rs = pstmt.getGeneratedKeys();
		rs.next();
		int seq = rs.getInt(1);
		log.info("seq = " + seq);
		
		con.close();
		
	}

}











