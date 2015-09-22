package com.webapp.idgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.webapp.mapper.IdGeneratorMapper;
import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class GeneratorTableTest {
	
	static Log log = LogFactory.getLog(GeneratorTableTest.class);

	static GenericXmlApplicationContext ctx;
	
	public static void main(String[] args) throws SQLException {
		ctx = new GenericXmlApplicationContext();
//		ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.getEnvironment().setActiveProfiles("oracle");
		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml");
		ctx.refresh();
		
		for (int i=0; i<1; i++) {
			Runnable r = new Runnable() {
				
				@Override
				public void run() {
					try {
//						jdbc();
//						jdbcTemplate();
//						SqlSession();
						SqlSessionTemplate();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			Thread t = new Thread(r);
			t.start();
		}

	}
	
	static void SqlSessionTemplate () throws InterruptedException {
		PlatformTransactionManager tm = ctx.getBean(PlatformTransactionManager.class);
		
		DefaultTransactionDefinition td = new DefaultTransactionDefinition();
		td.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		td.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		
		TransactionStatus status = tm.getTransaction(td);
		try {
			memberInsert();
//			memberUpdate();
			tm.commit(status);
		} catch (BadSqlGrammarException e) {
			log.info("BadSql..." + e.getMessage(), e);
		} catch (Exception e) {
			tm.rollback(status);
			e.printStackTrace();
		}
	}
	static void memberUpdate() throws InterruptedException {
		Member member = new Member();
		member.setId(16400);
		member.setPassword("xxxx1234");
		member.setName("yyyy");
		MemberMapper memberMapper = ctx.getBean(MemberMapper.class);
		
		memberMapper.update(member);
		
	}
	
	static void memberInsert() throws InterruptedException {
		Member member = ctx.getBean(Member.class);
		IdGeneratorMapper idGenMapper = ctx.getBean(IdGeneratorMapper.class);
		MemberMapper memberMapper = ctx.getBean("memberMapper", MemberMapper.class);
		
		Map<String, Object> idGen = idGenMapper.selectByName("memberId");
		int nextval = ((BigDecimal)idGen.get("nextval")).intValue();
		int incval = ((BigDecimal)idGen.get("incval")).intValue();
		int seqno = nextval + incval;
		idGen.put("nextval", seqno);
		
		Thread.sleep((int)(Math.random()*1000));
		idGenMapper.update(idGen);
		
		member.setId(seqno);
		member.setEmail(member.getEmail() + seqno);
		memberMapper.insertGenerator(member);
		log.info("seqno = " + seqno);
		
	}
	
	static void SqlSession() throws IOException, InterruptedException {
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
		SqlSessionFactory factory =  builder.build(inputStream, "mysql");
//		SqlSessionFactory factory =  builder.build(inputStream, "oracle");
		
		SqlSession session = factory.openSession(false);
		
		IdGeneratorMapper idGeneratorMapper = session.getMapper(IdGeneratorMapper.class);
		MemberMapper memberMapper = session.getMapper(MemberMapper.class);
		
		Map<String, Object> idGen = idGeneratorMapper.selectByName("memberId");
		int nextval = ((BigDecimal)idGen.get("nextval")).intValue();
		int incval = ((BigDecimal)idGen.get("incval")).intValue();
		int seqno = nextval + incval;
		
		Thread.sleep((int)(Math.random()*3000));
		
		idGen.put("nextval", seqno);
		idGeneratorMapper.update(idGen);
		
		Member member = new Member();
		member.setId(seqno);
		member.setEmail("xxx@gen.com" + seqno);
		member.setPassword("1234");
		member.setName("홍길동");
		member.setRegdate(new Date());
		
		memberMapper.insertGenerator(member);
		
		log.info("seqno = " + seqno);
		
		session.commit();
		session.close();
		
	}
	
	static void jdbcTemplate() throws SQLException, InterruptedException {
		
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		
		String sql = "select name, nextval, incval " +
				 	 "  from id_generator " +
				 	 " where name = 'memberId' " +
				 	 "   for update ";
		
//		DataSourceTransactionManager tm = new DataSourceTransactionManager();
//		tm.setDataSource(ctx.getBean(DataSource.class));
		PlatformTransactionManager tm = ctx.getBean(PlatformTransactionManager.class);
		
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		definition.setReadOnly(false);
//		definition.setTimeout(10);
		
		TransactionStatus status = tm.getTransaction(definition);
		
		Map<String, Object> gen = template.queryForMap(sql);
		
		Thread.sleep((int)(Math.random()*3000));
		
		String name = (String) gen.get("name");
		int nextval = ((BigDecimal) gen.get("nextval")).intValue();
		int incval = ((BigDecimal) gen.get("incval")).intValue();
		
		String update = "update id_generator " +
						"   set nextval = ? " +
						" where name = 'memberId'";
		
		template.update(update, nextval + incval);
		
		String insert = "insert into member2 " +
				 		"(id, email, password, name, regdate) " +
				 		"values " +
				 		"(?, ?, 'yyy', 'xxx', '2015/08/11') ";
		
		template.update(insert, nextval + incval, "xxx@" + nextval);
		
		tm.commit(status);
		
		log.info("name = " + name + ", nextval = " + (nextval + incval) + ", incval = " + incval);
		
		
//		String insert = "insert into member " +
//				 		"(id, email, password, name, regdate) " +
//				 		"values " +
//				 		"(?, ?, 'yyy', 'xxx', '2015/08/11') ";
		
//		template.update(insert, seq, "xxx@" + seq);
	}
	
	static void jdbc() throws SQLException {
		
		try {
			Thread.sleep((int)(Math.random()*3000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DataSource ds = ctx.getBean(DataSource.class);
		log.info(((BasicDataSource)ds).getUrl());
		
		Connection con = ds.getConnection();
		con.setAutoCommit(false);
		
		// 1. select member_id_seq.nextval from dual;
		Statement stmt = con.createStatement();
		
		String sql = "select name, nextval, incval " +
					 "  from id_generator " +
				     " where name = 'memberId' " +
					 "   for update ";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		String name = rs.getString("name");
		int nextval = rs.getInt("nextval");
		int incval  = rs.getInt("incval");
		int seq = nextval + incval;
		log.info("seq = " + seq);
		
		String insert = "insert into member2 " +
				 		"(id, email, password, name, regdate) " +
				 		"values " +
				 		"(?, ?, 'yyy', 'xxx', '2015/08/11') ";
		PreparedStatement pstmt = con.prepareStatement(insert);
		pstmt.setInt(1, seq);
		pstmt.setString(2, "xxx@xxx.com" + seq);
		pstmt.executeUpdate();
		
		String update = "update id_generator " +
						"   set nextval = ? " +
						" where name = 'memberId'";
		pstmt = con.prepareStatement(update);
		pstmt.setInt(1, seq);
		pstmt.executeUpdate();
		
		con.commit();
		con.close();
	}

}
