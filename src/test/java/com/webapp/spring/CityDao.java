package com.webapp.spring;

public class CityDao {
	static CityDao citydao;
	
	private CityDao() {
	
	}
	
	public static CityDao newInstance() {
		if (citydao == null) {
			citydao = new CityDao();
		}
		return citydao;
	}
	
	void print() {
		System.out.println("CityDao...");
	}
	
}
