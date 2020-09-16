package com.seiya.springboot;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.seiya.springboot.repositories.memberRepository;

@Controller
public class MainController {
	
	@Autowired
	memberRepository mr;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	UserAccountService sv;
	
	@Autowired
	MyService ms;
	
	@PostConstruct
	public void init() {
		sv.registerAdmin("seiya","demo");
		sv.registerAdmin("you","demo");
	}
	
	@RequestMapping("/")
	public String topselect() {
		return "redirect:/topselect";
	}
	
	@RequestMapping("/topselect")
	public String topselect2() {
		
		//ログインしたユーザーのAccountインスタンスをセッションに格納
		ms.AccountToSession();
		
		return "topselect";
	}
	
	@RequestMapping(value="/insertallsearch", method = RequestMethod.GET)
	public ModelAndView insertallsearch(@ModelAttribute("formModel")member mem, ModelAndView mav) {
		
		mav.setViewName("insertallsearch");
		
		mav.addObject("formModel", mem);
		
		//セッションから取得したAccountインスタンスをもとに、そのユーザにおける全検索し、リストへ
		Account account =(Account)session.getAttribute("account");
		List<member> list = mr.findByAccount(account);
		mav.addObject("list", list);
		
		return mav;
	}
	
	@RequestMapping(value= "/insertallsearch", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView insertallsearch2(@RequestParam("gender") String gender, @RequestParam("grade") int grade, @RequestParam("payFlag") int payFlag,
			@ModelAttribute("formModel")@Validated member mem, BindingResult result, ModelAndView mav) {
		
		//バリデーションの結果で条件分岐
		if(!result.hasErrors()) {
		
			//性別・学年・支払いの有無・Accountインスタンスの値をmemberインスタンスにセットし、それをデータベースに保存する
			ms.settingAndSave(mem, gender, grade, payFlag);
		
			return new ModelAndView("redirect:/insertallsearch");
		
		} else {
			mav.setViewName("insertallsearch");
			mav.addObject("msg", "入力エラーです。");
			
			Account account =(Account)session.getAttribute("account");
			List<member> list = mr.findByAccount(account);
			mav.addObject("list", list);
			
			return mav;
		}
	}
	
	@RequestMapping("/update")
	public ModelAndView update(@RequestParam("id")Long id, ModelAndView mav) {
		
		mav.setViewName("update");
		
		Optional<member> data =  mr.findByMemberid(id);
		mav.addObject("formModel", data.get());
		
		return mav;
	}
	
	@RequestMapping(value= "/update2", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update2(@RequestParam("gender") String gender, @RequestParam("grade") int grade, @RequestParam("payFlag") int payFlag,
			@ModelAttribute("formModel") member mem, ModelAndView mav) {
		
		//性別・学年・支払いの有無・Accountインスタンスの値をmemberインスタンスにセットし、それをデータベースに更新
		ms.settingAndSave(mem, gender, grade, payFlag);
		
		return new ModelAndView("redirect:/insertallsearch");
	}
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam("id")Long id, ModelAndView mav) {
		
		mav.setViewName("delete");
		Optional<member> data =  mr.findById(id);
		mav.addObject("formModel", data.get());
		return mav;
	}
	
	@RequestMapping(value= "/delete2", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView delete2(@RequestParam("id")Long id, ModelAndView mav) {
		
		mr.deleteById(id);
		return new ModelAndView("redirect:/insertallsearch");
	}
	
	@RequestMapping("/eachsearch")
	public String eachsearch() {
		return "eachsearch";
	}
	
	@RequestMapping(value="/eachsearchresult", method = RequestMethod.POST)
	public ModelAndView eachsearchresult(@RequestParam(value="membername",required = false)String membername, @RequestParam(value="gender",required = false)String gender,
			@RequestParam(value="position",required = false)String position, @RequestParam(value="grade",required = false)Integer grade,
			@RequestParam(value="payFlag",required = false)Integer payFlag, @RequestParam("hiddendata")String hiddendata, ModelAndView mav) {
		
		mav.setViewName("eachsearchresult");
		
		Account account =(Account)session.getAttribute("account");
		
		//検索する条件によって分岐
		if(hiddendata.equals("membername")) {
			ms.getMembername(membername, account, mav);
			
		} else if(hiddendata.equals("gender")) {
			ms.getGender(gender, account, mav);
			
		} else if(hiddendata.equals("position")) {
			ms.getPosition(position, account, mav);
		
		} else if(hiddendata.equals("grade")) {
			ms.getGrade(grade, account, mav);
			
		}else if(hiddendata.equals("payFlag")) {
			ms.getPayFlag(payFlag, account, mav);
		}
		
		return mav;
	}
	
	@RequestMapping("/detailsearch")
	public String detailsearch() {
		return "detailsearch";
	}
	
	@RequestMapping(value="/detailsearchresult", method = RequestMethod.POST)
	public ModelAndView detailsearchresult(@RequestParam(value="gender",required = false)String gender,@RequestParam(value="grade",required = false)Integer grade,
			@RequestParam(value="grade2",required = false)Integer grade2, @RequestParam(value="grade3",required = false)Integer grade3,
			@RequestParam(value="grade4",required = false)Integer grade4, @RequestParam(value="grade5",required = false)Integer grade5,
			@RequestParam(value="payFlag",required = false)Integer payFlag, @RequestParam("hiddendata")String hiddendata, ModelAndView mav) {
		
		mav.setViewName("detailsearchresult");
		
		Account account =(Account)session.getAttribute("account");
		
		//検索する条件によって分岐
		if(hiddendata.equals("ggp")) {
			ms.getGGP(grade, gender, payFlag, account, mav);
			
		} else if(hiddendata.equals("gradeup")) {
			ms.getGradeUp(grade2, account, mav);
			
		} else if(hiddendata.equals("gradedown")) {
			ms.getGradeDown(grade3, account, mav);
		
		} else if(hiddendata.equals("gradebetween")) {
			ms.getGradeBetween(grade4, grade5, account, mav);
		}
		
		return mav;
	}
}
