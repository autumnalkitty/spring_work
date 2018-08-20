package com.gura.spring01;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping("/home")//.do 생략 가능
	public String gura(HttpServletRequest request) {
		List<String> news=new ArrayList<>();
		news.add("안녕하세요.");
		news.add("오늘 Spring Framework 시작입니다.");
		news.add("웅앵웅");
		news.add("쵸키포");
		//request 에 담기
		request.setAttribute("news", news);
		/*
		view page 정보를 문자열로 return 하기
		prefix: /WHB-INF/views/
		suffix: .jsp
		"/WEB-INF/views/"+"home"+".jsp"
		"/WEB-INF/views/home.jsp" 
		여기에 대한 설정정보는 
		/WEB-INF/spring/appServlet/servlet-context.xml 
		에 있다.
		*/
		return "home";
	}
}
