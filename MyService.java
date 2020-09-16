package com.seiya.springboot;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.seiya.springboot.repositories.AccountRepository;
import com.seiya.springboot.repositories.memberRepository;

@Service
public class MyService {

	@Autowired
	AccountRepository ur;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	memberRepository mr;
	
	//ログインしたユーザーアカウントのユーザー名を取得し、それをもとにAccountインスタンスをセッションに格納するメソッドを実行
	public void AccountToSession() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserAccount)principal).getUsername();
		Account account = ur.findByUsername(username);
		session.setAttribute("account", account);
	}
	
	//性別・学年・支払いの有無・Accountインスタンスの値をmemberインスタンスにセットし、データベースに保存or更新するメソッドを実行
	public void settingAndSave(member mem, String gender, int grade, int payFlag) {
		
		mem.setGender(gender);
		mem.setGrade(grade);
		mem.setPayFlag(payFlag);
		
		Account account =(Account)session.getAttribute("account");
		mem.setAccount(account);
		
		mr.saveAndFlush(mem);
	}
	
	//あだ名or性別or役職or学年or支払いの有無を検索するメソッド
	public void getMembername(String membername, Account account, ModelAndView mav) {
		
		List<member> list = mr.findByMembernameLikeAndAccount("%" + membername + "%", account);
		mav.addObject("list", list);
	}
	
	public void getGender(String gender, Account account, ModelAndView mav) {
		
		List<member> list = mr.findByGenderAndAccountOrderByGradeDesc(gender, account);
		mav.addObject("list", list);
	}
	
	public void getPosition(String position, Account account, ModelAndView mav) {
			
		List<member> list = mr.findByPositionLikeAndAccountOrderByMemberidAsc("%" + position + "%", account);
		mav.addObject("list", list);
	}
	
	public void getGrade(int grade, Account account, ModelAndView mav) {
				
		List<member> list = mr.findByGradeAndAccountOrderByMembernameAsc(grade, account);
		mav.addObject("list", list);
	}
	
	public void getPayFlag(int payFlag, Account account, ModelAndView mav) {
					
		List<member> list = mr.findByPayFlagAndAccountOrderByGradeDesc(payFlag, account);
		mav.addObject("list", list);
	}
	
	//学年×性別×支払いの有無の検索
	public void getGGP(int grade, String gender, int payFlag, Account account, ModelAndView mav) {
		List<member> list = mr.selectGradeAndGenderAndPay_flag(grade, gender, payFlag,account);
		mav.addObject("list", list);
	}
	
	//指定学年以上の検索
	public void getGradeUp(int grade2, Account account, ModelAndView mav) {
		List<member> list = mr.selectGradeUp(grade2,account);
		mav.addObject("list", list);
	}
	
	//指定学年以下の検索
	public void getGradeDown(int grade3, Account account, ModelAndView mav) {
		List<member> list = mr.selectGradeDown(grade3,account);
		mav.addObject("list", list);
	}
	
	//学年間の検索
	public void getGradeBetween(int grade4, int grade5, Account account, ModelAndView mav) {
		List<member> list = mr.selectGradeBetween(grade4, grade5,account);
		mav.addObject("list", list);
	}
		
}
