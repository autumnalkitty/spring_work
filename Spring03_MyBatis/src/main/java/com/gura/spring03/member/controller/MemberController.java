package com.gura.spring03.member.controller;

//import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.member.dto.MemberDto;
import com.gura.spring03.member.service.MemberService;

@Controller
public class MemberController {
	//의존객체
	@Autowired
	private MemberService memberService;
	//회원 목록 요청 처리
	@RequestMapping("/member/list")
	public ModelAndView list() {
		ModelAndView mView=new ModelAndView();
		//MemberService 객체를 이용해서 비즈니스 로직 처리
		memberService.list(mView);
		//view 페이지 정보 설정
		mView.setViewName("member/list"); // /WEB-INF/views/member/list.jsp
		return mView;
	}
	@RequestMapping("/member/insertform")
	public String insertForm() {
		return "member/insertform";
	}
	/*
	@RequestMapping("/member/insert")
	public String insert(HttpServletRequest request) {
		String name=request.getParameter("name");
		String addr=request.getParameter("addr");
		MemberDto dto=new MemberDto();
		dto.setName(name);
		dto.setAddr(addr);
		memberService.insert(dto);
		return "redirect:/member/list.do";
	}
	*/
	/*
	@RequestMapping("/member/insert")
	public String insert(@RequestParam String name, @RequestParam String addr) {
		MemberDto dto=new MemberDto();
		dto.setName(name);
		dto.setAddr(addr);
		memberService.insert(dto);
		return "redirect:/member/list.do";
	}
	*/
	@RequestMapping("/member/insert")
	public String insert(@ModelAttribute MemberDto dto) {
		/*
		회원정보가 담긴 MemberDto 객체를 MemberService 객체를 이용해서 DB 에 저장하기
		*/
		memberService.insert(dto);
		return "member/insert";
	}
	@RequestMapping("/member/delete")
	public String delete(@RequestParam int num) {
		memberService.delete(num);
		return "redirect:/member/list.do";
	}
	@RequestMapping("/member/updateform")
	public ModelAndView updateForm(@RequestParam int num) {
		ModelAndView mView=new ModelAndView();
		memberService.getData(mView, num);
		mView.setViewName("member/updateform");
		return mView;
	}
	@RequestMapping("/member/update")
	public String update(@ModelAttribute MemberDto dto) {
		memberService.update(dto);
		return "redirect:/member/list.do";
	}
}