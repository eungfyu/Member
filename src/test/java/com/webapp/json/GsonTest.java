package com.webapp.json;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webapp.model.Member;

public class GsonTest {

	public static void main(String[] args) {

		Gson g = new GsonBuilder().setPrettyPrinting().create();//출력 이쁘게
		/*
		 * Primitive
		 */
		String json = g.toJson("Hello");
		System.out.println(json);
		/*
		 * Array
		 */
//		json = g.toJson(new String[]{"Hello", "Java", "C++"});
		json = g.toJson(new Object[]{"Hello", 1234, "C++"});
		System.out.println(json);
		/*
		 * Object
		 */
		Member m = new Member();
		m.setId(100);
		m.setEmail("xxx@webapp.com");
		m.setName("죽먹자");
		m.setPassword("1234");
		m.setRegdate(new Date());
		
		json = g.toJson(m);
		System.out.println(json);
		
		
	}

}
