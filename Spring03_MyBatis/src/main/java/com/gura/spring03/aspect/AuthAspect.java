package com.gura.spring03.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Aspect
@Component
public class AuthAspect {
	/*
	컨트롤러의 특정 메소드에 AOP 를 적용해서 로그인 여부를 검사하는 메소드
	*/
	@Around("execution(org.springframework.web.servlet.ModelAndView auth*(..))")
	public Object signinCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		//AOP 가 적용된 메소드에 전달된 인자 얻어오기
		Object[] args=joinPoint.getArgs();
		//반복문 돌면서 하나씩 추출해서
		for(Object tmp:args){
			//만약 객체가 HttpServletRequest type 이면
			if(tmp instanceof HttpServletRequest) {
				//원래 type 으로 casting 해서
				HttpServletRequest request=(HttpServletRequest)tmp;
				//로그인 정보가 있는지 확인
				String id=(String)request.getSession().getAttribute("id");
				if(id==null) {
					//로그인 정보가 없다면 여기가 수행된다.
					ModelAndView mView=new ModelAndView();
					//query 문자열 읽어오기
					// a=xxx&b=xxx&c=xxx
					String query=request.getQueryString();
					//원래 가야할 요청명
					String url=null;
					if(query==null) {
						url=request.getRequestURI();
					} else {
						url=request.getRequestURI()+"?"+query;
					}
					mView.setViewName("redirect:/users/signin_form.do?url="+url);
					// Spring Framework 에 ModelAndView 객체를 바로 리턴
					return mView;
				}
			}
		}
		//정상적으로 수행하기
		return joinPoint.proceed();
	}
}
