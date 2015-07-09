package com.harm.sgc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@RequestMapping(value="")
@Controller
public class WebController {

	private static final Logger logger = LoggerFactory.getLogger(WebController.class);

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menu(Model model) throws Exception {
		return "menu";
	}//END OF FUNCTION
	
	@RequestMapping(value = "/areaForm", method = RequestMethod.GET)
	public String areaForm(Model model) throws Exception {
		return "areaForm";
	}//END OF FUNCTION	

	@RequestMapping(value = "/gateForm", method = RequestMethod.GET)
	public String gateForm(Model model) throws Exception {
		return "gateForm";
	}//END OF FUNCTION
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) throws Exception {
		return "home";
	}//END OF FUNCTION
	
	@RequestMapping(value = "/sampleDraggable01", method = RequestMethod.GET)
	public String sampleDraggable01(Model model) throws Exception {
		return "sampleDraggable01";
	}//END OF FUNCTION
	
	@RequestMapping(value = "/sampleForm", method = RequestMethod.GET)
	public String sampleForm(Model model) throws Exception {
		return "sampleForm";
	}//END OF FUNCTION
	
	@RequestMapping(value = "/sampleMenu", method = RequestMethod.GET)
	public String sampleMenu(Model model) throws Exception {
		return "sampleMenu";
	}//END OF FUNCTION
	
}//END OF CLASS
