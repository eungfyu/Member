package com.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String getView() {
		return "ajax/view";
	}
	
	@RequestMapping(value="/array", method=RequestMethod.GET)
	public void getArray() {
		
	}
}
