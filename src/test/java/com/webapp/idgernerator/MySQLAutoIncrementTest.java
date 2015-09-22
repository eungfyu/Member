package com.webapp.idgernerator;

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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class MySQLAutoIncrementTest {
	
	static Log log=LogFactory.getLog(MySQLAutoIncrementTest.class);
	public static void main(String[] args) throws SQLException, IOException {
//		jdbc();
//		jdbcTemplate();
//		SqlSession();
		SqlSessionTemplate();
	}
	
	static void SqlSessionTemplate() throws IOException, SQLException{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("mysql");//이 부분 설정파일로 뺄 수 있다.
		ctx.load("spring/beans_oracle.xml","spring/beans_mysql.xml");//매퍼만 가져와서 insert했다
		ctx.refresh();
		
		DataSource ds = (DataSource) ctx.getBean("dataSource");
		
		Connection con = ds.getConnection();
		log.info("db = " +con.getMetaData().getDatabaseProductName());//메타데이타의 프로덕트네임 가져옴
		log.info("Version = " +con.getMetaData().getDatabaseProductVersion());//버전정보
		log.info("Major = " +con.getMetaData().getDatabaseMajorVersion());
		log.info("Minor = " +con.getMetaData().getDatabaseMinorVersion());
		
		MemberMapper mapper = ctx.getBean(MemberMapper.class); 
		
//		이부분 전부 빈파일루 설정
//		Member member = new Member();
//		member.setEmail("abc@cdb.com"+(int)(Math.random()*1000000));
//		member.setName("abc");
//		member.setPassword("1234");
//		member.setRegdate(new Date());
		
		Member member = ctx.getBean(Member.class);
		member.setEmail(member.getEmail()+(int)(Math.random()*1000000));
		log.info(member.getRegdate());
		
		mapper.insert(member);
		log.info("id = " + member.getId());
	}
	
	static void SqlSession() throws IOException{
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
		SqlSessionFactory factory =  builder.build(inputStream, "mysql");//mysql을 oracle로 변경해도 작동한다. insert에 속성을 주었다
		
		SqlSession session = factory.openSession(false);
		
		Member parameter = new Member();
		parameter.setEmail("xxx@mysql.com"+(int)(Math.random()*1000));
		parameter.setName("죽먹자");
		parameter.setPassword("diediedie");
		parameter.setRegdate(new Date());
		
		session.insert("com.webapp.mapper.MemberMapper.insert", parameter);
		log.info("id = "+ parameter.getId());//매퍼 사용 안한거
		
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		
		parameter = new Member();
		parameter.setEmail("xxx@mysql.com"+(int)(Math.random()*1000));
		parameter.setName("죽먹자");
		parameter.setPassword("diediedie");
		parameter.setRegdate(new Date());
				
		mapper.insert(parameter);
		log.info("id = "+ parameter.getId());//매퍼 사용한 거
		session.commit();
	}
	
	static void jdbcTemplate() throws SQLException{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("mysql");//beans.xml에서 환경정보 가져오기. 오라클 프로파일만 가져오도록 설정
		ctx.load("classpath:spring/beans.xml");//경로지정 로딩. 데이터 소스 가져오기위함
		ctx.refresh();//공장 설정후 refresh해줘야 함		
		
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		
		final String insert = "insert into member" +  
				"(email, password, name, regdate)" + 
				"values " +
				"(?, 'yyyy','xxx','2015/08/11')";
		
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
	
	static void jdbc() throws SQLException{
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("mysql");//beans.xml에서 환경정보 가져오기. 오라클 프로파일만 혹은 mysql프로파일만 가져오도록 설정
		ctx.load("classpath:spring/beans.xml");//경로지정 로딩. 데이터 소스 가져오기위함
		ctx.refresh();//공장 설정후 refresh해줘야 함		
		
//		DriverManagerDataSource ds = ctx.getBean(DriverManagerDataSource.class);//데이타소스 가져옴
		BasicDataSource ds = ctx.getBean(BasicDataSource.class);
		
		log.info(ds.getUrl());
		
		Connection con = ds.getConnection();// 커넥션 가져옴
		
		String insert = "insert into member" +  
						"(email, password, name, regdate)" + 
						"values " +
						"(?, 'yyyy','xxx','2015/08/11')";
		
		PreparedStatement pstmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, ""+(int)(Math.random()*10000));//Math는 소수점 리턴. 그래서 10000곱하고 이걸 int로 캐스트하고 null string(""+)으로 다시 캐스팅 
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		rs.next();
		int seq  = rs.getInt(1);
		log.info("seq = "+ seq);
		
		con.close();
	}

}
