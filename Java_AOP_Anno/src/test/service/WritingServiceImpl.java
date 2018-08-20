package test.service;

import org.springframework.stereotype.Component;

@Component //component scan 했을 때 bean 으로 만들기 위한 annotation
public class WritingServiceImpl implements WritingService {
	@Override
	public void write() {
		System.out.println("글쓰기 작업 중");
		try {
			Thread.sleep(5000);
		} catch(Exception e) {};
	}
	@Override
	public void writeToFriend() {
		System.out.println("친구에게 글쓰기 작업 중");
		try {
			Thread.sleep(5000);
		} catch(Exception e) {};
	}
	@Override
	public void writeToTeacher(String name) {
		System.out.println(name+" 선생님에게 글쓰기 작업 중");
		try {
			Thread.sleep(5000);
		} catch(Exception e) {};
	}
	@Override
	public String writeAndGet(int num) {
		System.out.println("글쓰기 작업 후 문자열을 돌려줍니다.");
		return "Acorn";
	}
}
