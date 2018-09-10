<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/cafe/list.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
</head>
<body>
<div class="container">
	<c:choose>
		<c:when test="${not empty id }">
			<p><strong>${id }</strong>님 로그인 중</p>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath }/users/signin_form.do">로그인</a>
		</c:otherwise>
	</c:choose>
	<a href="insert_form.do">새 글 업로드</a>
	<h3>카페글 목록입니다.</h3>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>등록일</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tmp" items="${list }">
			<tr>
				<td>${tmp.num }</td>
				<td>
					<a href="detail.do?num=${tmp.num }&condition=${condition }&keyword=${keyword }">${tmp.title }</a><c:if test="${tmp.commentCount ne 0 }"><strong> [${tmp.commentCount }]</strong></c:if>
				</td>
				<td>${tmp.writer }</td>
				<td>${tmp.viewCount }</td>				
				<td>${tmp.regdate }</td>
				<td>
				<c:if test="${tmp.writer eq id }">
					<a href="javascript:deleteConfirm(${tmp.num })">삭제</a>
				</c:if>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 페이징 처리 -->
	<ul class="pagination">
		<c:choose>
			<c:when test="${startPageNum ne 1 }">
				<li>
					<a href="list.do?pageNum=${startPageNum-1 }&condition=${condition }&keyword=${keyword }">&laquo;</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="disabled">
					<a href="javascript:">&laquo;</a>
				</li>
			</c:otherwise>
		</c:choose>
		<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }">
			<c:choose>
				<c:when test="${i eq pageNum }">
					<li class="active">
						<a href="list.do?pageNum=${i }&condition=${condition }&keyword=${keyword }">${i }</a>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<a href="list.do?pageNum=${i }&condition=${condition }&keyword=${keyword }">${i }</a>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:choose>
			<c:when test="${endPageNum lt totalPageCount }">
				<li>
					<a href="list.do?pageNum=${endPageNum+1 }&condition=${condition }&keyword=${keyword }">&raquo;</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="disabled">
					<a href="javasctipt:">&raquo;</a>
				</li>
			</c:otherwise>
		</c:choose>
	</ul>
	<!-- 키워드 검색어 폼 -->
	<form action="list.do" method="post">
		<label for="condition">검색조건</label>
		<select name="condition" id="condition">
			<option value="titlecontent">제목+내용</option>
			<option value="title">제목</option>
			<option value="content">내용</option>
			<option value="writer">작성자</option>
		</select>
		<input type="text" name="keyword" placeholder="검색어"/>
		<button type="submit">검색</button>
	</form>
	<c:choose>
		<c:when test="${not empty keyword }">
			<p><strong>${keyword }</strong> 검색어로 검색된 <strong>${totalRow }</strong>개의 글이 있습니다.</p>
		</c:when>
		<c:otherwise>
			<p><strong>${totalRow }</strong>개의 글이 있습니다.</p>
		</c:otherwise>
	</c:choose>
</div>
<script>
	function deleteConfirm(num) {
		var isDelete=confirm(num+"번 파일을 삭제 하시겠습니까?")
		if(isDelete) {
			location.href="delete.do?num="+num;
		}
	}
</script>
</body>
</html>