<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/update_form.jsp</title>
<style>
	#profileImage {
		width: 50px;
		height: 50px;
		background-color: yellow;
		border-radius: 50%;
	}
	#profileForm {
		display: none;
	}
</style>
</head>
<body>
<h3>회원정보 수정 페이지 입니다.</h3>
<c:choose>
	<c:when test="${empty dto.profileImage }">
		<img src="${pageContext.request.contextPath }/resources/images/re.gif" id="profileImage" alt=""/>
	</c:when>
	<c:otherwise>
		<img src="${pageContext.request.contextPath }/upload/${dto.profileImage }" id="profileImage" alt=""/>
	</c:otherwise>
</c:choose>
<form action="update.do" method="post" id="updateForm">
	<input type="hidden" name="id" value="${id }"/>
	<label for="id">아이디</label>
	<input type="text" id="id" value="${id }" disabled="disabled"/><br/>
	<label for="email">이메일 주소</label>
	<input type="text" name="email" id="email" value="${dto.email }"/><br/>
	<button type="submit">수정 확인</button>
</form>
<a href="pw_change_form.do">비밀번호 변경</a>
<form action="profile_update.do" method="post" enctype="multipart/form-data" id="profileForm">
	<input type="file" name="file" id="file"/>
</form>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.3.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/jquery.form.min.js"></script>
<script>
	$("#profileImage").click(function() {
		alert("프로필 이미지를 선택하세요.");
		$("#file").click();
	});
	$("#profileForm").ajaxForm(function(responseData) {
		var fileName=responseData.fileName;
		var imagePath="${pageContext.request.contextPath }/upload/"+fileName;
		$("#profileImage").attr("src", imagePath);
	});
	$("#file").on("input", function() {
		$("#profileForm").submit(); 
	});
</script>
</body>
</html>