package com.gura.spring03.cafe.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.cafe.dao.CafeCommentDao;
import com.gura.spring03.cafe.dao.CafeDao;
import com.gura.spring03.cafe.dto.CafeCommentDto;
import com.gura.spring03.cafe.dto.CafeDto;
import com.gura.spring03.exception.ForbiddenException;

@Service
public class CafeServiceImpl implements CafeService {
	@Autowired
	private CafeDao cafeDao;
	@Autowired
	private CafeCommentDao commentDao;
	//한 페이지에서 나타낼 row 의 개수
	private static final int PAGE_ROW_COUNT=5;
	//하단 디스플레이의 페이지 개수
	private static final int PAGE_DISPLAY_COUNT=3;
	@Override
	public void getList(HttpServletRequest request, ModelAndView mView, int pageNum) {
		//검색과 관련된 파라미터를 읽어온다.
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		CafeDto dto=new CafeDto();
		if(keyword!=null) {//�˻�� ���޵� ���
			if(condition.equals("titlecontent")) {
				dto.setTitle(keyword);
				dto.setContent(keyword);
			} else if(condition.equals("title")) {
				dto.setTitle(keyword);
			} else if(condition.equals("content")) {
				dto.setContent(keyword);
			} else if(condition.equals("writer")) {
				dto.setWriter(keyword);
			}
			mView.addObject("keyword", keyword);
			mView.addObject("condition", condition);
		}
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		//전체 row 의 개수를 읽어온다.
		int totalRow=cafeDao.getCount(dto);
		//전체 페이지의 개수 구하기
		int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//시작페이지 번호
		int startPageNum=1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝페이지 번호가 잘못됐을 경우
		if(totalPageCount < endPageNum) {
			endPageNum=totalPageCount; //보정해준다.
		}
		//startRowNum 과 endRowNum 을 CafeDto 객체에 담는다.
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		//1. CafeDto 객체를 전달해서 파일목록을 불러온다.
		List<CafeDto> list=cafeDao.getList(dto);
		for(CafeDto tmp:list) {
			int num=tmp.getNum();
			int commentCount=cafeDao.getCommentCount(num);
			tmp.setCommentCount(commentCount);
		}
		//2. ModelAndView 에 담고
		mView.addObject("list", list);
		mView.addObject("pageNum", pageNum);
		mView.addObject("startPageNum", startPageNum);
		mView.addObject("endPageNum", endPageNum);
		mView.addObject("totalPageCount", totalPageCount);
		mView.addObject("totalRow", totalRow);
	}
	@Override
	public void insert(HttpServletRequest request, CafeDto dto) {
		//작성자
		String writer=(String)request.getSession().getAttribute("id");
		//제목
		String title=request.getParameter("title");
		//내용
		String content=request.getParameter("content");
		//CafeDto 에 담기
		dto.setWriter(writer);
		dto.setTitle(title);
		dto.setContent(content);
		//DB 에 저장
		cafeDao.insert(dto);
	}
	@Override
	public void detail(HttpServletRequest request, ModelAndView mView, CafeDto dto) {
		int num=Integer.parseInt(request.getParameter("num"));
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		if(keyword!=null) {//�˻�� ���޵� ���
			if(condition.equals("titlecontent")) {
				dto.setTitle(keyword);
				dto.setContent(keyword);
			} else if(condition.equals("title")) {
				dto.setTitle(keyword);
			} else if(condition.equals("content")) {
				dto.setContent(keyword);
			} else if(condition.equals("writer")) {
				dto.setWriter(keyword);
			}
			mView.addObject("keyword", keyword);
			mView.addObject("condition", condition);
		}
		dto.setNum(num);
		CafeDto resultDto=cafeDao.getData(dto);
		cafeDao.addViewCount(num);
		mView.addObject("dto", resultDto);
		//댓글 목록
		int ref_group=Integer.parseInt(request.getParameter("num"));
		List<CafeCommentDto> commentList=commentDao.getList(ref_group);
		mView.addObject("commentList", commentList);
	}
	@Override
	public void update(HttpServletRequest request, CafeDto dto) {
		int num=Integer.parseInt(request.getParameter("num"));
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		dto.setNum(num);
		dto.setTitle(title);
		dto.setContent(content);
		cafeDao.update(dto);
	}
	@Override
	public void delete(HttpServletRequest request, int num) {
		String id=(String)request.getSession().getAttribute("id");
		CafeDto dto=new CafeDto();
		dto.setNum(num);
		String writer=cafeDao.getData(dto).getWriter();
		if(!id.equals(writer)) {
			throw new ForbiddenException();
		}
		cafeDao.delete(num);
	}
}
