package com.gura.spring03.cafe.dao;

import java.util.List;

import com.gura.spring03.cafe.dto.CafeDto;

public interface CafeDao {
	public int getCount(CafeDto dto);
	public List<CafeDto> getList(CafeDto dto);
	public void insert(CafeDto dto);
	public CafeDto getData(CafeDto dto);
	public int getCommentCount(int num);
	public void addViewCount(int num);
	public void update(CafeDto dto);
	public void delete(int num);
}
