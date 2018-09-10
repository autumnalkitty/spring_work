package com.gura.spring03.cafe.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gura.spring03.cafe.dao.CafeCommentDao;
import com.gura.spring03.cafe.dto.CafeCommentDto;
import com.gura.spring03.exception.ForbiddenException;

@Service
public class CafeCommentServiceImpl implements CafeCommentService {
	@Autowired
	private CafeCommentDao commentDao;
	@Override
	public void insert(HttpServletRequest request, CafeCommentDto dto) {
		int seq=commentDao.getSequence();
		int comment_group=dto.getComment_group();
		dto.setNum(seq);
		if(comment_group==0) {
			dto.setComment_group(seq);
		}
		commentDao.insert(dto);
	}
	@Override
	public void update(HttpServletRequest request, CafeCommentDto dto) {
		int num=Integer.parseInt(request.getParameter("num"));
		String content=request.getParameter("content");
		dto.setNum(num);
		dto.setContent(content);
		commentDao.update(dto);
	}
	@Override
	public void delete(HttpServletRequest request, CafeCommentDto dto) {
		int num=Integer.parseInt(request.getParameter("num"));
		int comment_group=Integer.parseInt(request.getParameter("comment_group"));
		String id=(String)request.getSession().getAttribute("id");
		String writer=dto.getWriter();
		if(!id.equals(writer)) {
			throw new ForbiddenException();
		}
		dto.setNum(num);
		dto.setComment_group(comment_group);
		commentDao.delete(dto);
	}
}
