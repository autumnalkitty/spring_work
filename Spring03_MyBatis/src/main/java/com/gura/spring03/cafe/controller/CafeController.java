package com.gura.spring03.cafe.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.cafe.dto.CafeCommentDto;
import com.gura.spring03.cafe.dto.CafeDto;
import com.gura.spring03.cafe.service.CafeCommentService;
import com.gura.spring03.cafe.service.CafeService;

@Controller
public class CafeController {
	@Autowired
	private CafeService cafeService;
	@Autowired
	private CafeCommentService commentService;
	@RequestMapping("/cafe/list")
	public ModelAndView list(HttpServletRequest request, ModelAndView mView, @RequestParam(defaultValue="1") int pageNum) {
		cafeService.getList(request, mView, pageNum);
		mView.setViewName("cafe/list");
		return mView;
	}
	@RequestMapping("/cafe/insert_form")
	public ModelAndView authInsertForm(HttpServletRequest request) {
		return new ModelAndView("cafe/insert_form");
	}
	@RequestMapping("/cafe/insert")
	public ModelAndView authInsert(HttpServletRequest request, @ModelAttribute CafeDto dto) {
		cafeService.insert(request, dto);
		return new ModelAndView("redirect:/cafe/list.do");
	}
	@RequestMapping("/cafe/detail")
	public ModelAndView detail(HttpServletRequest request, ModelAndView mView, CafeDto dto) {
		cafeService.detail(request, mView, dto);
		mView.setViewName("cafe/detail");
		return mView;
	}
	@RequestMapping("/cafe/update_form")
	public ModelAndView authUpdateForm(HttpServletRequest request, ModelAndView mView, CafeDto dto) {
		cafeService.detail(request, mView, dto);
		mView.setViewName("cafe/update_form");
		return mView;
	}
	@RequestMapping("/cafe/update")
	public ModelAndView authUpdate(HttpServletRequest request, CafeDto dto) {
		cafeService.update(request, dto);
		return new ModelAndView("redirect:/cafe/detail.do?num="+dto.getNum()+"&condition="+request.getParameter("condition")+"&keyword="+request.getParameter("keyword"));
	}
	@RequestMapping("/cafe/delete")
	public ModelAndView authDelete(HttpServletRequest request, @RequestParam int num) {
		cafeService.delete(request, num);
		return new ModelAndView("redirect:/cafe/list.do");
	}
	@RequestMapping("/cafe/comment_insert")
	public ModelAndView authCommentInsert(HttpServletRequest request, @ModelAttribute CafeCommentDto dto) {
		commentService.insert(request, dto);
		return new ModelAndView("redirect:/cafe/detail.do?num="+dto.getRef_group());
	}
	@RequestMapping("/cafe/comment_update")
	public ModelAndView authCommentUpdate(HttpServletRequest request, CafeCommentDto dto, @RequestParam int ref_group) {
		commentService.update(request, dto);
		return new ModelAndView("redirect:/cafe/detail.do?num="+ref_group);
	}
	@RequestMapping("/cafe/comment_delete")
	public ModelAndView authCommentDelete(HttpServletRequest request, CafeCommentDto dto, @RequestParam int ref_group) {
		commentService.delete(request, dto);
		return new ModelAndView("redirect:/cafe/detail.do?num="+ref_group);
	}
}
