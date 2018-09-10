package com.gura.spring03.users.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.users.dto.UsersDto;
import com.gura.spring03.users.service.UsersService;

@Controller
public class UsersController {
	//서비스 DI
	@Autowired
	private UsersService service;
	//회원가입양식 요청 처리
	@RequestMapping("/users/signup_form")
	public String signup_form() {
		return "users/signup_form";
	}
	//id 중복확인 ajax 요청에 대한 응답(JSON 응답)
	@RequestMapping("/users/checkid")
	@ResponseBody
	public Map<String, Object> checkId(@RequestParam String inputId) {
		//서비스객체를 이용해서 사용가능 여부를 boolean type 으로 리턴받는다.
		boolean canUse=service.canUseId(inputId);
		//Map 에 담는다.
		Map<String, Object> map=new HashMap<>();
		map.put("canUse", canUse);
		return map;
	}
	//회원가입 요청 처리
	@RequestMapping("/users/signup")
	public ModelAndView signup(@ModelAttribute UsersDto dto) {
		ModelAndView mView=new ModelAndView();
		service.signUp(mView, dto);
		mView.setViewName("users/signup");
		return mView;
	}
	//로그인양식 요청 처리
	@RequestMapping("/users/signin_form")
	public ModelAndView signin_form(@RequestParam(defaultValue="") String url, HttpServletRequest request) {
		if(url.equals("")) {
			url=request.getContextPath()+"/";
		}
		ModelAndView mView=new ModelAndView();
		mView.addObject("url", url);
		mView.setViewName("users/signin_form");
		return mView;
	}
	//로그인 요청 처리
	@RequestMapping("/users/signin")
	public ModelAndView signin(@ModelAttribute UsersDto dto, @RequestParam String url, HttpSession session) {
		ModelAndView mView=new ModelAndView();
		service.signIn(mView, dto, session);
		mView.addObject("url", url);
		mView.setViewName("users/signin");
		return mView;
	}
	//로그아웃 요청 처리
	@RequestMapping("/users/signout")
	public String signout(HttpSession session) {
		session.invalidate();
		return "users/signout";
	}
	//가입정보보기 요청 처리
	@RequestMapping("/users/info")
	public ModelAndView authInfo(HttpServletRequest request, HttpSession session) {
		ModelAndView mView=new ModelAndView();
		service.info(mView, session);
		mView.setViewName("users/info");
		return mView;
	}
	//가입정보수정 양식 요청 처리
	@RequestMapping("/users/update_form")
	public ModelAndView authUpdateForm(HttpServletRequest request, HttpSession session) {
		ModelAndView mView=new ModelAndView();
		service.updateForm(mView, session);
		mView.setViewName("users/update_form");
		return mView;
	}
	//가입정보수정 요청 처리
	@RequestMapping("/users/update")
	public ModelAndView authUpdate(HttpServletRequest request, @ModelAttribute UsersDto dto) {
		service.update(dto);
		//ModelAndView mView=new ModelAndView();
		//mView.setViewName("redirect:/users/info.do");
		return new ModelAndView("redirect:/users/info.do");
		//return mView;
	}
	//프로필 이미지 수정 요청 처리
	@RequestMapping("/users/profile_update")
	@ResponseBody
	public Map<String, Object> authProfileupdate(HttpServletRequest request, @RequestParam MultipartFile file) {
		String fileName=service.profileUpdate(request, file);
		Map<String, Object> map=new HashMap<>();
		map.put("fileName", fileName);
		return map;
	}
	//비밀번호수정양식 요청 처리
	@RequestMapping("/users/pw_change_form")
	public ModelAndView authPwdUpdateForm(HttpServletRequest request) {
		return new ModelAndView("users/pw_change_form");
	}
	//비밀번호 확인 요청 처리
	@RequestMapping("/users/pw_check")
	@ResponseBody
	public Map<String, Object> pwdCheck(@RequestParam String inputPwd, HttpSession session) {
		boolean isValid=service.isValidPwd(inputPwd, session);
		Map<String, Object> map=new HashMap<>();
		map.put("isValid", isValid);
		return map;
	}
	//비밀번호수정 요청 처리
	@RequestMapping("/users/pw_change")
	public ModelAndView authPwdChange(HttpServletRequest request, @RequestParam String pwd, HttpSession session) {
		service.changePwd(pwd, session);
		return new ModelAndView("redirect:/users/info.do");
	}
	//회원탈퇴 요청 처리
	@RequestMapping("/users/delete")
	public ModelAndView authDelete(HttpServletRequest request, ModelAndView mView) {
		service.delete(mView, request.getSession());
		mView.setViewName("users/delete");
		return mView;
	}
}
