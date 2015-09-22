package com.webapp.mybatis.xxx;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;

import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class DataBaseProduct {
	
	static Log log = LogFactory.getLog(DataBaseProduct.class);

	public static void main(String[] args) throws SQLException, IOException {
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle");
//		ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.load("classpath:spring/beans.xml");
		ctx.refresh();
		
		BasicDataSource ds = ctx.getBean(BasicDataSource.class);
		log.info(ds.getUrl());
		
		Connection con = ds.getConnection();

		DatabaseMetaData md = con.getMetaData();
		log.info(md.getDatabaseProductName());
		log.info(md.getDefaultTransactionIsolation());
		log.info(md.getDatabaseMajorVersion());
		log.info(md.getDatabaseMinorVersion());
		
		con.close();
		
	}

}
