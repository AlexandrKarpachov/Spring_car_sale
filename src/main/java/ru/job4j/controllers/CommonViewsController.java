package ru.job4j.controllers;


import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class CommonViewsController {

	@GetMapping("/main")
	public String main(Principal principal, Model model) {
		return "main";
	}
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, Model model){
		return "login";
	}
	
	@RequestMapping(value="/denied")
	public String denied(){
		return "denied";
	}
	
}
