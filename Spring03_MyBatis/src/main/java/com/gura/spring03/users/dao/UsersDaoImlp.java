package com.gura.spring03.users.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring03.users.dto.UsersDto;

@Repository
public class UsersDaoImlp implements UsersDao {
	@Autowired
	private SqlSession session;
	//의존객체 DI
	@Override
	public void insert(UsersDto dto) {
		session.insert("users.insert", dto);
	}
	//회원 한명의 정보를 id 로 select 해서 리턴하는 메소드
	@Override
	public UsersDto getData(String id) {
		UsersDto dto=session.selectOne("users.getData", id);
		return dto;
	}
	//회원 한명의 정보를 수정하는 메소드
	@Override
	public void update(UsersDto dto) {
		session.update("users.update", dto);
	}
	//회원 한명의 정보를 삭제하는 메소드
	@Override
	public void delete(String id) {
		session.delete("users.delete", id);
	}
	//id 사용가능 여부를 리턴해주는 메소드
	@Override
	public boolean canUseId(String id) {
		String result=session.selectOne("users.isExist", id);
		if(result==null) {
			return true;
		} else {
			return false;
		}
	}
	//비밀번호 수정하는 메소드
	@Override
	public void changePwd(UsersDto dto) {
		session.update("users.changePwd", dto);
	}
}
