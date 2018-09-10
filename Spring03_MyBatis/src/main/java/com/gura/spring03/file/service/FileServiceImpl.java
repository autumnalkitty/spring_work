package com.gura.spring03.file.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.exception.ForbiddenException;
import com.gura.spring03.file.dao.FileDao;
import com.gura.spring03.file.dto.FileDto;

@Service
public class FileServiceImpl implements FileService {
	//한 페이지에 나타낼 row 의 개수
	private static final int PAGE_ROW_COUNT=5;
	//하단 디스플레이의 페이지 개수
	private static final int PAGE_DISPLAY_COUNT=3;
	@Autowired
	private FileDao dao;
	@Override
	public void getList(ModelAndView mView, int pageNum) {
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		//전체 row 의 개수를 읽어온다.
		int totalRow=dao.getCount();
		//전체 페이지 개수 구하기
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//시작 페이지 번호
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못됐다면
		if(totalPageCount < endPageNum) {
			endPageNum=totalPageCount; //보정해준다.
		}
		// startRowNum 과 endRowNum 을 FileDto 에 담은 다음
		FileDto dto=new FileDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		//1. FileDto 객체를 전달해서 파일목록을 불러온다.
		List<FileDto> list=dao.getList(dto);
		//2. ModelAndView 에 담는다.
		mView.addObject("list", list);
		mView.addObject("pageNum", pageNum);
		mView.addObject("startPageNum", startPageNum);
		mView.addObject("endPageNum", endPageNum);
		mView.addObject("totalPageCount", totalPageCount);
	}
	@Override
	public void insert(HttpServletRequest request, FileDto dto) {
		//파일을 저장할 절대경로를 얻어온다.
		String realPath=request.getSession().getServletContext().getRealPath("/upload");
		//FileDto 에 담긴 MultipartFile 객체의 참조값을 얻어온다.
		MultipartFile mFile=dto.getFile();
		//원본 파일명
		String orgFileName=mFile.getOriginalFilename();
		//파일 사이즈
		long fileSize=mFile.getSize();
		//저장할 파일의 상세 경로
		String filePath=realPath+File.separator;
		//디렉토리를 만들 파일 객체 생성
		File file=new File(filePath);
		if(!file.exists()){//디렉토리가 존재하지 않는다면
			file.mkdir(); //디렉토리를 만든다.
		}
		//파일 시스템에 저장할 파일명을 만든다. (겹치지 않게)
		String saveFileName=System.currentTimeMillis()+orgFileName;
		try {
			//upload 폴더에 파일을 저장한다.
			mFile.transferTo(new File(filePath+saveFileName));
		} catch(Exception e) {
			e.printStackTrace();
		}
		//FileDto 객체에 추가정보를 담는다.
		dto.setOrgFileName(orgFileName);
		dto.setSaveFileName(saveFileName);
		dto.setFileSize(fileSize);
		//세션에서 작성자 정보를 얻어온다.
		String id=(String)request.getSession().getAttribute("id");
		//FileDto 객체에 작성자 정보를 담는다.
		dto.setWriter(id);
		//FileDao 객체를 이용해서 DB 에 저장하기
		dao.insert(dto);
	}
	@Override
	public void getData(ModelAndView mView, int num) {
		FileDto dto=dao.getData(num);
		mView.addObject("dto", dto);
	}
	@Override
	public void delete(HttpServletRequest request, int num) {
		String id=(String)request.getSession().getAttribute("id");
		FileDto dto=dao.getData(num);
		if(!id.equals(dto.getWriter())) {
			throw new ForbiddenException();
		}
		String path=request.getServletContext().getRealPath("/upload")+File.separator+dto.getSaveFileName();
		try { new File(path).delete();
		} catch(Exception e) {}
		dao.delete(num);
	}
}
