package com.gura.spring03.cafe.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring03.cafe.dto.CafeCommentDto;

@Repository
public class CafeCommentDaoImpl implements CafeCommentDao {
	@Autowired
	private SqlSession session;
	@Override
	public int getSequence() {
		return session.selectOne("cafeComment.getSequence");
	}
	@Override
	public void insert(CafeCommentDto dto) {
		session.insert("cafeComment.insert", dto);
	}
	@Override
	public List<CafeCommentDto> getList(int ref_group) {
		return session.selectList("cafeComment.getList", ref_group);
	}
	@Override
	public void update(CafeCommentDto dto) {
		session.update("cafeComment.update", dto);
	}
	@Override
	public void delete(CafeCommentDto dto) {
		if(dto.getComment_group()==dto.getNum()) {
			session.delete("cafeComment.deleteO", dto);
		} else {
			session.delete("cafeComment.deleteR", dto);
		}
	}
}
