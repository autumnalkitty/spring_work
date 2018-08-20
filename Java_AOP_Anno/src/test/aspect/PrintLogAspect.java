package test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
/*
횡단 관심사(Cross Concern)를 Aspect 로 작성 하기 
*/
/*
Aspectj Expression
1. execution(public * *(..)): 접근 지정자가 public 인 method 가 point cut
2. execution(* test.service.*.*(..)): test.service 패키지의 모든 method 가 point cut
3. execution(public void insert*(..)): 접근 지정자는 public 이고 return type 은 void 이고
									method 이름이 insert 로 시작하는 모든 method 가 point cut
4. execution(* delete*(*)): method 명이 delete 로 시작하고 인자로 1개를 전달받는 method 가 point cut
5. execution(* delete*(*, *)): method 명이 delete 로 시작하고 인자로 2개를 전달받는 method 가 point cut 
*/
@Aspect//aspect 가 되기 위한 설정
@Component//bean 으로 만들기 위한 설정
public class PrintLogAspect {
	@Around("execution(public void insert())")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("--비즈니스 로직 수행 전입니다.");
		/*
		joinPoint 객체의 proceed() method 를 호출하는 시점이 AOP 가 적용된 method 가 수행되는 시점
		.proceed() method 의 return 객체가 AOP 가 적용된 method 의 return 객체
		만일 AOP 가 적용된 method 의 return type 이 void 면 obj 는 null
		*/
		Object obj=joinPoint.proceed();
		System.out.println("--비즈니스 로직 수행 후입니다.");
		return obj;
	}
}
