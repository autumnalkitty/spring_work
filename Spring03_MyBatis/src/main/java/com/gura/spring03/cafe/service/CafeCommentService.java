package com.gura.spring03.cafe.service;

import javax.servlet.http.HttpServletRequest;

import com.gura.spring03.cafe.dto.CafeCommentDto;

public interface CafeCommentService {
	public void insert(HttpServletRequest request, CafeCommentDto dto);
	public void update(HttpServletRequest request, CafeCommentDto dto);
	public void delete(HttpServletRequest request, CafeCommentDto dto);
}
