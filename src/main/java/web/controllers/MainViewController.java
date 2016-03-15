package web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainViewController 
{
	
	
	@RequestMapping(value="/Taquin")
	public String webSocketExample()
	{
		return "index";
	}	
	
	@RequestMapping(value="/ClienteJS")
	public String createClientJS()
	{
		return "client1";
	}
}
