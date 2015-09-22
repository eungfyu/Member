package com.webapp.mybatis.xxx;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;

public class FileList {
	String fname;
	Integer flength;
	Date flastModified;

//	byte[] image;
	InputStream image;
//	Blob image;
	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public Integer getFlength() {
		return flength;
	}
	public void setFlength(Integer flength) {
		this.flength = flength;
	}
	public Date getFlastModified() {
		return flastModified;
	}
	public void setFlastModified(Date flastModified) {
		this.flastModified = flastModified;
	}
//	public byte[] getImage() {
//		return image;
//	}
//	public void setImage(byte[] image) {
//		this.image = image;
//	}
	public InputStream getImage() {
		return image;
	}
	public void setImage(InputStream image) {
		this.image = image;
	}
//	public Blob getImage() {
//		return image;
//	}
//	public void setImage(Blob image) {
//		this.image = image;
//	}
	
}
