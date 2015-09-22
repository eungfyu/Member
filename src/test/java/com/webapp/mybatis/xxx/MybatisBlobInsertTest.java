package com.webapp.mybatis.xxx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import com.webapp.model.Member;

public class MybatisBlobInsertTest {

	public static void main(String[] args) throws IOException, SQLException {
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		InputStream inputStream = Resources.getResourceAsStream("com/webapp/mybatis/blob/mybatis_config.xml");
		
		SqlSessionFactory factory = builder.build(inputStream, "mysql");
		
		SqlSession session =  factory.openSession(false);

		Blob blob = session.getConnection().createBlob();
		
		FileList param = new FileList();
		
//		File f = new File("beans.xml");
//		File f = new File("C:\\02_Software\\03_db\\OracleXE112_Win32.zip");
		File f = new File("C:/02_Software/99.util/puttytel.exe");
		
		param.setFname(f.getName() + (int)(Math.random()*1000));
		param.setFlength((int)f.length());
//		param.setFlastModified(new Date(f.lastModified()));
//		param.setImage(FileCopyUtils.copyToByteArray(f));
		param.setImage(new FileInputStream(f));
//		param.setImage(blob);
		
		session.insert("com.webapp.mybatis.blob.FileListDao.insert", param);
		
//		List<Member> list =  session.selectList("com.webapp.mybatis.blob.FileListDao.selectAll");
//
//		for (Member m : list)
//			System.out.println(m.getId() + " " + m.getEmail());
//		
//		
//		Member m = session.selectOne("com.webapp.mybatis.blob.FileListDao.selectById", 1001);
//		System.out.println(m.getId() + " " + m.getEmail());
		
		session.commit();
		session.close();
		
		
	}

}
