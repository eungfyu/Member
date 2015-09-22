package com.webapp.threadlocal;

public class GlobalVariable {
	public static ThreadLocal<Result2> result = new ThreadLocal<Result2>();//스레드 마다 구분하는 역할
}
