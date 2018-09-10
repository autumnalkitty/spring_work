<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/insertform.jsp</title>
</head>
<body>
<h3>회원정보 추가 양식입니다</h3>
<form action="insert.do" method="post">
	<label for="name">이름</label>
	<input type="text" name="name" id="name"/>
	<label for="addr">주소</label>
	<input type="text" name="addr" id="addr"/>
	<button type="submit">추가하기</button>
</form>
</body>
</html>