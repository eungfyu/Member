package com.webapp.controller;

import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class LocaleController {
	
	@Autowired
	LocaleResolver localeResolver;
	
	@RequestMapping("/locale/{language:[a-z]{2}}")
	public void setLocale(HttpServletRequest request, 
						  HttpServletResponse response, 
						  PrintWriter out, 
						  @PathVariable String language){//로케일 변경하는 메소드. @PathVariable에 있는 {language:[a-z]{2}}를 String language로 바인딩함 
		
		Locale locale = new Locale(language);
		localeResolver.setLocale(request, response, locale);
		out.println("locale changed " + locale.getLanguage());
	}
}
