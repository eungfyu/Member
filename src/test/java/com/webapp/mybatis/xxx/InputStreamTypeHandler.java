package com.webapp.mybatis.xxx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.util.FileCopyUtils;

@MappedJdbcTypes(JdbcType.BLOB)
public class InputStreamTypeHandler extends BaseTypeHandler<InputStream>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			InputStream parameter, JdbcType jdbcType) throws SQLException {
		System.out.println("void setNonNullParameter(PreparedStatement ps, int i,");
		ps.setBinaryStream(i, parameter);
	}

	@Override
	public InputStream getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		System.out.println("InputStream getNullableResult(ResultSet rs, String columnName");
		InputStream in = rs.getBinaryStream(columnName);
		
//		File f = new File("OracleXE112_Win32.zip");
//		try {
//			FileCopyUtils.copy(in, new FileOutputStream(f));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return in;
	}

	@Override
	public InputStream getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		System.out.println("InputStream getNullableResult(ResultSet rs, int columnIndex)");
		InputStream in = rs.getBinaryStream(columnIndex);
		
//		File f = new File("OracleXE112_Win32.zip");
//		try {
//			FileCopyUtils.copy(in, new FileOutputStream(f));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return in;
	}

	@Override
	public InputStream getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Blob b = cs.getBlob(columnIndex);
		return b.getBinaryStream();
	}

}
