package com.gura.spring03.cafe.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.cafe.dto.CafeDto;

public interface CafeService {
	public void getList(HttpServletRequest request, ModelAndView mView, int pageNum);
	public void insert(HttpServletRequest request, CafeDto dto);
	public void detail(HttpServletRequest request, ModelAndView mView, CafeDto dto);
	public void update(HttpServletRequest request, CafeDto dto);
	public void delete(HttpServletRequest request, int num);
}
