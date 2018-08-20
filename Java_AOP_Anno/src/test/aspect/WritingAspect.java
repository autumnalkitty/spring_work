package test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WritingAspect {
	/*
	접근 지정자: public
	return type: void
	메소드명: write 로 시작하는 메소드
	메소드에 전달되는 인자: 없다 
	위와 같은 모양의 메소드가 실행되기 이전에 적용되는 Advice
	*/
	@Before("execution(public void write*())")
	public void preparePen() {
		System.out.println("[글을 쓰기 위해 펜을 준비해요.]");
	}
	/*
	접근 지정자: 상관없음
	return type: 상관없음
	메소드명: write 로 시작
	메소드에 전달되는 인자: 없다
	위와 같은 메소드가 실행된 이후에 적용되는 Advice
	*/
	@After("execution(* write*())")
	public void endPen() {
		System.out.println("[글을 다 작성하고 펜을 닫아요.]");
	}
	@Around("execution(* write*(String))")
	public void aroundWrite(ProceedingJoinPoint joinPoint) throws Throwable {
		//AOP 가 적용된 메소드에 전달된 인자를 Object[] 로 얻어내기
		Object[] args=joinPoint.getArgs();
		//반복문 돌면서 하나씩 참조해서
		for(Object tmp:args) {
			//���� ã�� Ÿ��(String type)�̸�
			if(tmp instanceof String) {
				//만일 우리가 찾는 type 이면 
				System.out.println("AOP 에서 미리 조사");
				System.out.println("전달된 name: "+tmp);
			}
		}
		System.out.println("[준비 작업을 해요.]");
		//AOP 가 적용된 메소드 수행하기
		joinPoint.proceed();
		System.out.println("[마무리 작업을 해요.]");
	}
	@Around("execution(String write*(int))")
	public Object aroundWrite2(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args=joinPoint.getArgs();
		//전달된 인자가 1개고 int type 이 확실
		int num=(int)args[0];
		System.out.println("인자로 전달된 숫자: "+num);
		//AOP 가 적용된 메소드를 호출하고 그 메소드의 return 객체를 Object type 으로 받기
		Object obj=joinPoint.proceed();
		//return type 이 String 이므로 casting
		String result=(String)obj;
		System.out.println("return 된 문자열: "+result);
		//원하다면 다른 정보를 리턴해줄 수도 있다.
		result="에이콘";
		return result;
	}
}
