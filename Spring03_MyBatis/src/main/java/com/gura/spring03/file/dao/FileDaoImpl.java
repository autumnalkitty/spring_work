package com.gura.spring03.file.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring03.exception.DeleteFailException;
import com.gura.spring03.file.dto.FileDto;
/*
Dao 는 @Repository Annotation 을 붙여서 만든다.
Dao 에서는 SqlSession 객체를 이용해서  DB 에 작업을 하기 때문에 
SQLException 이 발생할 가능성이 있는데,  
Spring framework 는 @Repository Annotation 이 붙어있는  Dao 에서 발생하는 
SQLException 을 catch 해서  DataAccessException type 의 예외를 발생시킨다. 
따라서 ExceptionController 에 있는 DataAccessException 메소드에서 해당 예외를 처리한다. 
*/
@Repository
public class FileDaoImpl implements FileDao {
	@Autowired
	private SqlSession session;
	@Override
	public void delete(int num) {
		int flag=session.delete("file.delete", num);
		if(flag<1) {
			throw new DeleteFailException("파일 삭제 실패!");
		}
	}
	@Override
	public void addDownCount(int num) {
		session.update("file.addDownCount", num);
	}
	@Override
	public FileDto getData(int num) {
		return session.selectOne("file.getData", num);
	}
	@Override
	public void insert(FileDto dto) {
		session.insert("file.insert", dto);
	}
	@Override
	public List<FileDto> getList(FileDto dto) {
		return session.selectList("file.getList", dto);
	}
	@Override
	public int getCount() {
		return session.selectOne("file.getCount");
	}
}
