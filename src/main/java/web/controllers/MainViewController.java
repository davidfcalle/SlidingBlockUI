package web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainViewController {
	
	@RequestMapping(value="/hi/")
	public String webSocketExample(){
		return "index";
	}	
}
