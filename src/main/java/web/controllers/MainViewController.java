package web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author David Suarez
 *
 */

@Controller
public class MainViewController
{


	@RequestMapping(value = { "/taquin", "/taquin/" , "/"} )
	public String webSocketExample()
	{
		return "index";
	}

	@RequestMapping(value = { "/clienteJS", "/clientejs/" } )
	public String createClientJS()
	{
		return "client1";
	}
}
