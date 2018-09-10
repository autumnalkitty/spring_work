<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/pw_change_form.jsp</title>
</head>
<body>
<h3>비밀번호 변경 페이지입니다.</h3>
<form action="pw_change.do" method="post" id="pwdForm">
	<label for="currentPwd">기존 비밀번호</label>
	<input type="password" name="currentPwd" id="currentPwd"/>
	<span id="currentCheck"></span>
	<br />
	<label for="pwd">새 비밀번호</label>
	<input type="password" name="pwd" id="pwd" />
	<br />
	<label for="pwd2">새 비밀번호 확인</label>
	<input type="password" name="pwd2" id="pwd2"/>
	<span id="pwdCheck"></span>
	<br />
	<button type="submit">확인</button>
	<button type="reset">취소</button>
</form>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.3.1.js"></script>
<script>
	var isCurrentPwdValid=false;
	var isNewPwdValid=false;
	//기존 비밀번호 입력란의 포커스가 사라지면 실행할 함수 등록
	$("#currentPwd").on("blur", function(){
		var inputPwd=$(this).val();
		$.ajax({
			url: "pw_check.do", 
			method: "post", 
			data: {inputPwd:inputPwd}, 
			success: function(responseData) {
				if(responseData.isValid) {
					$("#currentCheck").text("일치").css("color", "green");
					isCurrentPwdValid=true;
				} else {
					$("#currentCheck").text("불일치").css("color", "red");
					isCurrentPwdValid=false;
				}
			}
		});
	});
	$("#pwd2").on("input", function(){
		//입력한 비밀번호를 읽어와서 
		var pwd1=$("#pwd").val();
		var pwd2=$("#pwd2").val();
		if(pwd1==pwd2) {
			$("#pwdCheck").text("");
			isNewPwdValid=true;
		} else {
			$("#pwdCheck").text("위와 같게 입력해주세요.").css("color", "red");
			isNewPwdValid=false;
		}
	});
	$("#pwdForm").on("submit", function() {
		if(!isCurrentPwdValid||!isNewPwdValid) {
			return false;
		}
	});
</script>
</body>
</html>