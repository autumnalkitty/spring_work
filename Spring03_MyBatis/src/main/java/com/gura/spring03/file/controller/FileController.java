package com.gura.spring03.file.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.file.dto.FileDto;
import com.gura.spring03.file.service.FileService;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	@RequestMapping("/file/list")
	public ModelAndView list(ModelAndView mView, @RequestParam(defaultValue="1") int pageNum) {
		fileService.getList(mView, pageNum);
		mView.setViewName("file/list");
		return mView;
	}
	@RequestMapping("/file/upload_form")
	public ModelAndView authUploadForm(HttpServletRequest request) {
		return new ModelAndView("file/upload_form");
	}
	@RequestMapping("/file/upload")
	public ModelAndView authUpload(HttpServletRequest request, @ModelAttribute FileDto dto) {
		fileService.insert(request, dto);
		return new ModelAndView("redirect:/file/list.do");
	}
	@RequestMapping("/file/download")
	public ModelAndView download(ModelAndView mView, @RequestParam int num) {
		fileService.getData(mView, num);
		mView.setViewName("fileDownView");
		return mView;
	}
	@RequestMapping("/file/delete")
	public ModelAndView authDelete(HttpServletRequest request, @RequestParam int num) {
		fileService.delete(request, num);
		return new ModelAndView("redirect:/file/list.do");
	}
}
