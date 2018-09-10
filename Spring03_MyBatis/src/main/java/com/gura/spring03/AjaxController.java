package com.gura.spring03;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AjaxController {
	@RequestMapping("/ajax/upload_form")
	public String uploadForm() {
		return "ajax/upload_form";
	}
	@RequestMapping("/ajax/upload")
	@ResponseBody
	public Map<String, Object> upload(@RequestParam MultipartFile file) {
		String orgFileName=file.getOriginalFilename();
		Map<String, Object> map=new HashMap<>();
		map.put("msg", "업로드된 파일명은 "+orgFileName+"입니다.");
		return map;
	}
	@RequestMapping("/ajax/upload2")
	@ResponseBody
	public Map<String, Object> upload2(HttpSession session, @RequestParam MultipartFile file) {
		String realPath=session.getServletContext().getRealPath("/upload");
		String orgFileName=file.getOriginalFilename();
		//저장할 파일의 상세 경로
		String filePath=realPath+File.separator;
		//파일 시스템에 저장할 파일명을 생성한다.
		String saveFileName=System.currentTimeMillis()+orgFileName;
		File uploadFolder=new File(filePath);
		if(!uploadFolder.exists()) {
			uploadFolder.mkdir();
		}
		try {
			file.transferTo(new File(filePath+saveFileName));
		} catch(Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map=new HashMap<>();
		map.put("fileName", saveFileName);
		return map;
	}
}
