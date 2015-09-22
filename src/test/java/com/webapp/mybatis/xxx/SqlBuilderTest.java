package com.webapp.mybatis.xxx;

import org.apache.ibatis.jdbc.SqlBuilder;
import org.springframework.util.Assert;


public class SqlBuilderTest {

	public static void main(String[] args) {

		Assert.isTrue(true);
		SqlBuilder.BEGIN();
		SqlBuilder.UPDATE("member");
		SqlBuilder.SET("name = ?");
		SqlBuilder.SET("password = ?");
		SqlBuilder.WHERE("id = ?");
		SqlBuilder.WHERE("email = ?");
		SqlBuilder.OR();
		SqlBuilder.WHERE("email = ?");
		System.out.println(SqlBuilder.SQL());
		
	}

}
