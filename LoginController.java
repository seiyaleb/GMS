package com.seiya.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login(Model model) {
		
		model.addAttribute("iserror", false);
		return "toplogin";
	}
	
	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("iserror", true);
		return "toplogin";
	}
}
