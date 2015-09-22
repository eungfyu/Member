package com.webapp.mybatis.xxx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import com.webapp.model.Member;

public class MybatisBlobSelectTest {

	public static void main(String[] args) throws IOException, SQLException {
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		InputStream inputStream = Resources.getResourceAsStream("com/webapp/mybatis/blob/mybatis_config.xml");
		SqlSessionFactory factory = builder.build(inputStream, "mysql");
		SqlSession session =  factory.openSession(false);
		
//		FileList param = new FileList();
		
//		File f = new File("beans.xml");
//		File f = new File("C:\\02_Software\\03_db\\OracleXE112_Win32.zip");
		
//		param.setFname(f.getName() + (int)(Math.random()*1000));
//		param.setFlength((int)f.length());
//		param.setFlastModified(new Date(f.lastModified()));
//		param.setImage(FileCopyUtils.copyToByteArray(f));
//		param.setImage(new FileInputStream(f));
//		param.setImage(blob);
		
//		session.insert("com.webapp.mybatis.blob.FileListDao.insert", param);
		
//		List<Member> list =  session.selectList("com.webapp.mybatis.blob.FileListDao.selectAll");
//
//		for (Member m : list)
//			System.out.println(m.getId() + " " + m.getEmail());
//		
//		
		
		
//		FileList m = session.selectOne("com.webapp.mybatis.blob.FileListDao.selectByName", "OracleXE112_Win32.zip134");
		FileList m = session.selectOne("com.webapp.mybatis.blob.FileListDao.selectByName", "puttytel.exe17");
		session.commit();
		session.close();
		System.out.println("fname = " + m.getFname());
//		File f = new File("OracleXE112_Win32.zip");
		File f = new File("puttytel.exe");
		FileCopyUtils.copy(m.getImage(), new FileOutputStream(f));
		
//		session.select("com.webapp.mybatis.blob.FileListDao.selectByName", "OracleXE112_Win32.zip134", new ResultHandler<FileList>() {
//
//			@Override
//			public void handleResult(
//					ResultContext<? extends FileList> resultContext) {
//				FileList m = resultContext.getResultObject();
//				System.out.println("fname = " + m.getFname());
//				File f = new File("OracleXE112_Win32.zip");
//				try {
//					FileCopyUtils.copy(m.getImage(), new FileOutputStream(f));
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//		});
		
		
		
		
		
		
	}

}
