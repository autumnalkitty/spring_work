package com.gura.spring03.shop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.shop.dao.OrderDao;
import com.gura.spring03.shop.dao.ShopDao;
import com.gura.spring03.shop.dto.OrderDto;
import com.gura.spring03.shop.dto.ShopDto;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private OrderDao orderDao;
	@Override
	public void getList(ModelAndView mView) {
		List<ShopDto> list=shopDao.getList();
		mView.addObject("list", list);
	}
	/*
	Ʈ트랜잭션(Transaction) 설정방법]
	1. pom.xml 에 spring-tx dependency 추가
	2. servlet-context.xml 에 transaction 설정 추가
	3. 트랜잭션을 적용할 서비스의 메소드에 @Transactional Annotation 추가
	*/
	@Transactional
	@Override
	public void buy(HttpServletRequest request, ModelAndView mView) {
		String id=(String)request.getSession().getAttribute("id");
		int num=Integer.parseInt(request.getParameter("num"));
		int price=shopDao.getPrice(num);
		ShopDto dto=new ShopDto();
		dto.setId(id);
		dto.setNum(num);
		dto.setPrice(price);
		shopDao.minusMoney(dto);
		shopDao.plusPoint(dto);
		shopDao.minusStock(num);
		OrderDto orderDto=new OrderDto();
		orderDto.setId(id);
		orderDto.setCode(num);
		orderDto.setAddr("노량진");
		orderDao.addOrder(orderDto);
	}
}
