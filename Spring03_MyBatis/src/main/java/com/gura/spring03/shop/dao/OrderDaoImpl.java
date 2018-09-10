package com.gura.spring03.shop.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import com.gura.spring03.exception.NoDeliveryException;
import com.gura.spring03.shop.dto.OrderDto;

@Repository
public class OrderDaoImpl implements OrderDao {
	@Autowired
	private SqlSession session;
	@Override
	public void addOrder(OrderDto dto) {
		session.insert("shop.addOrder", dto);
		/*
		특정 조건에서 Exception 을 발생시켜야 하는 경우
		DataAccessException 클래스를 상속받은 Exception 을 발생시킨다	.
		*/
		/*
		NoDeliveryException nde=new NoDeliveryException("해당 지역은 배송 불가 지역");
		throw nde;
		*/
	}
}
