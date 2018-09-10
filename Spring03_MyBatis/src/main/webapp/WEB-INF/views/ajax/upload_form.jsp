<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/ajax/upload_form.jsp</title>
<style>
	img {
		width: 200px;
	}
	#profileImg {
		width: 100px;
		height: 100px;
		background-color: yellow;
		cursor: pointer;
		border-radius: 50%;
	}
	#fileForm4 {
		display: none;
	}
</style>
</head>
<body>
<h3>ajax 파일 업로드 테스트</h3>
<form action="upload.do" method="post" enctype="multipart/form-date" id="fileForm">
	<label for="file">파일첨부</label>
	<input type="file" name="file" id="file"/>
	<button type="submit">업로드</button>
</form>
<form action="upload2.do" method="post" enctype="multipart/form-date" id="fileForm2">
	<label for="file">파일첨부</label>
	<input type="file" name="file" id="file"/>
	<button type="submit">업로드</button>
</form>
<form action="upload2.do" method="post" enctype="multipart/form-date" id="fileForm3">
	<label for="file">파일첨부</label>
	<input type="file" name="file" id="file"/>
</form>
<div id="result">

</div>
<form action="upload2.do" method="post" enctype="multipart/form-date" id="fileForm4">
	<label for="file">파일첨부</label>
	<input type="file" name="file" id="file"/>
</form>
<img id="profileImg" src="${pageContext.request.contextPath }/resources/images/re.gif" alt="" />
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.3.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/jquery.form.min.js"></script>
<script>
	$("#fileForm").ajaxForm(function(responseData) {
		console.log(responseData);
	});
	$("#fileForm2").ajaxForm(function(responseData) {
		var fileName=responseData.fileName;
		var imagePath="${pageContext.request.contextPath }/upload/"+fileName;
		$("<img>").attr("src", imagePath).appendTo("#result");
	});
	$("#fileForm3").ajaxForm(function(responseData) {
		var fileName=responseData.fileName;
		var imagePath="${pageContext.request.contextPath }/upload/"+fileName;
		$("<img>").attr("src", imagePath).appendTo("#result");
	});
	$("#fileForm3 #file").on("input", function() {
		$("#fileForm3").submit();
	});
	$("#fileForm4").ajaxForm(function(responseData) {
		var fileName=responseData.fileName;
		var imagePath="${pageContext.request.contextPath }/upload/"+fileName;
		$("#profileImg").attr("src", imagePath);
	});
	$("#profileImg").click(function() {
		$("#fileForm4 input[type=file]").click();
	});
	$("#fileForm4 #file").on("input", function() {
		$("#fileForm4").submit();
	});
</script>
</body>
</html>