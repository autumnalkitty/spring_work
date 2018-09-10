package com.gura.spring03.member.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*
회원정보에 관련된 비즈니스 로직을 수행하는 서비스
*/
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.member.dao.MemberDao;
import com.gura.spring03.member.dto.MemberDto;

@Service
public class MemberServiceImpl implements MemberService {
	//의존객체 주입
	@Autowired
	private MemberDao dao;
	@Override
	public void list(ModelAndView mView) {
		List<MemberDto> list=dao.getList();
		mView.addObject("list", list);
	}
	@Override
	public void insert(MemberDto dto) {
		//MemberDto 객체를 이용해서 DB 에 회원정보 저장하기
		dao.insert(dto);
	}
	@Override
	public void update(MemberDto dto) {
		dao.update(dto);
	}
	@Override
	public void delete(int num) {
		dao.delete(num);
	}
	@Override
	public void getData(ModelAndView mView, int num) {
		MemberDto dto=dao.getData(num);
		mView.addObject("dto", dto);
	}
}
