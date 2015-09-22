package com.webapp.mybatis;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class SqlBuilderTest {

	public static void main(String[] args) {
		 BEGIN();
		 UPDATE("member");
if (true)   SET("email = ?");
if (false)  SET("password = ?");
if (true)   SET("name = ?");
if (false)  SET("regdate = ?");
if (true)WHERE("id = ? ");
if (true)WHERE("email = ?");
           OR();
if (true)WHERE("email = ?");           
		
		System.out.println(SQL());
		
	}
}
