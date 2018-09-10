package com.gura.spring03.users.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.users.dao.UsersDao;
import com.gura.spring03.users.dto.UsersDto;

@Service
public class UsersServiceImpl implements UsersService {
	//의존객체 DI
	@Autowired
	private UsersDao dao;
	@Override
	public boolean canUseId(String id) {
		//인자로 전달된 id 의 사용가능 여부를 리턴해준다.
		return dao.canUseId(id);
	}
	@Override
	public void signUp(ModelAndView mView, UsersDto dto) {
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodedPwd=encoder.encode(dto.getPwd());
		dto.setPwd(encodedPwd);
		dao.insert(dto);
		//request 에 담을 내용을 ModelAndView 객체에 담는다.
		mView.addObject("msg", dto.getId()+" 회원님 가입됐습니다.");
	}
	@Override
	public void signIn(ModelAndView mView, UsersDto dto, HttpSession session) {
		UsersDto resultDto=dao.getData(dto.getId());
		boolean isSigninSuccess=false;
		if(resultDto!=null) {
			isSigninSuccess=BCrypt.checkpw(dto.getPwd(), resultDto.getPwd());
		}
		if(isSigninSuccess) {
			session.setAttribute("id", dto.getId());
		}
		mView.addObject("isSigninSuccess", isSigninSuccess);
	}
	@Override
	public void info(ModelAndView mView, HttpSession session) {
		String id=(String)session.getAttribute("id");
		UsersDto dto=dao.getData(id);
		mView.addObject("dto", dto);
	}
	@Override
	public void updateForm(ModelAndView mView, HttpSession session) {
		String id=(String)session.getAttribute("id");
		UsersDto dto=dao.getData(id);
		mView.addObject("dto", dto);
	}
	@Override
	public void update(UsersDto dto) {
		dao.update(dto);
	}
	@Override
	public String profileUpdate(HttpServletRequest request, MultipartFile file) {
		String id=(String)request.getSession().getAttribute("id");
		String realPath=request.getServletContext().getRealPath("/upload");
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
		UsersDto dto=new UsersDto();
		dto.setId(id);
		dto.setProfileImage(saveFileName);
		dao.updateProfile(dto);
		return saveFileName;
	}
	@Override
	public boolean isValidPwd(String inputPwd, HttpSession session) {
		String id=(String)session.getAttribute("id");
		UsersDto dto=dao.getData(id);
		boolean isValid=BCrypt.checkpw(inputPwd, dto.getPwd());
		return isValid;
	}
	@Override
	public void changePwd(String pwd, HttpSession session) {
		String id=(String)session.getAttribute("id");
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodedPwd=encoder.encode(pwd);
		UsersDto dto=new UsersDto();
		dto.setId(id);
		dto.setPwd(encodedPwd);
		dao.changePwd(dto);
	}
	@Override
	public void delete(ModelAndView mView, HttpSession session) {
		String id=(String)session.getAttribute("id");
		dao.delete(id);
		session.invalidate();
		mView.addObject("msg", id+" 회원님 탈퇴됐습니다.");
	}
}
