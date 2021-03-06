package com.gura.spring03.shop.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring03.shop.dto.ShopDto;

@Repository
public class ShopDaoImpl implements ShopDao {
	@Autowired
	private SqlSession session;
	@Override
	public List<ShopDto> getList() {
		return session.selectList("shop.getList");
	}
	@Override
	public void minusStock(int num) {
		session.update("shop.minusStock", num);
	}
	@Override
	public void minusMoney(ShopDto dto) {
		session.update("shop.minusMoney", dto);
	}
	@Override
	public void plusPoint(ShopDto dto) {
		session.update("shop.plusPoint", dto);
	}
	@Override
	public int getPrice(int num) {
		return session.selectOne("shop.getPrice", num);
	}
}
