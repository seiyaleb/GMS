package com.seiya.springboot;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SignupController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserAccountService sv;
	

	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public String signup() {
		return "signup";
	}
	
	@RequestMapping(value= "/signup", method = RequestMethod.POST)
	public ModelAndView signup(ModelAndView mav) {
		
		mav.setViewName("signup");
		
		//signupconfirm（）で格納したセッションから取得
		String username = (String)session.getAttribute("username");
		String password = (String)session.getAttribute("password");
		
		mav.addObject("username", username);
		mav.addObject("password", password);
		
		return mav;
	}
	
	
	@RequestMapping(value= "/signupconfirm", method = RequestMethod.POST)
	public ModelAndView signupconfirm(@RequestParam("username") String username, @RequestParam("password")String password,
			ModelAndView mav) {
		
		mav.setViewName("signupconfirm");
		
		//一時的にセッションに格納
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		
		mav.addObject("username", username);
		mav.addObject("password", password);
		
		return mav;
	}
	
	@RequestMapping("/signupresult")
	public ModelAndView signupresult(ModelAndView mav) {
		
		mav.setViewName("signupresult");
		
		//signupconfirm（）で格納したセッションから取得
		String username = (String)session.getAttribute("username");
		String password = (String)session.getAttribute("password");
		
		//セッションから取得したユーザー情報でDBに登録される(今回はADMIN権限で)
		sv.registerAdmin(username,password );
		//sv.registerUser(username,password );
		
		return mav;
	}
}
