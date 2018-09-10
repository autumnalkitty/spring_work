<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/cafe/detail.jsp</title>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.3.1.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<style>
	.content {
		width: 766px;
		box-shadow: 10px 10px 5px #888;
	}
	.content img {
		max-width: 100%;
	}
	.comment {
		position: relative;
		width: 768px;
	}
	.comment form {
		display: none;
	}
	.comment .reply_icon {
		width: 8px;
		height: 8px;
		position: absolute;
		top: 0px;
		left: -12px;
	}
	.insert {
		position: relative;
	}
	.insert .reply_icon {
		width: 8px;
		height: 8px;
		position: absolute;
		top: 0px;
		left: -12px;
	}
	.muted {
		color: #888;
	}
</style>
</head>
<body>
<c:if test="${not empty keyword}">
	<p>검색어: <strong>${keyword }</strong>에 대한 자세히 보기</p>
</c:if>
<h3>글 자세히 보기 페이지</h3>
<div class="container">
	<table>
		<tr>
			<th>글 번호</th>
			<td>${dto.num }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${dto.writer }</td>
		</tr>
		<tr>
			<th>글 제목</th>
			<td>${dto.title }</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${dto.regdate }</td>
		</tr>
	</table>
	<div class="content">${dto.content }</div>
	<c:if test="${sessionScope.id eq dto.writer }">
		<a href="update_form.do?num=${dto.num }">수정</a>
		<a href="javascript:deleteConfirm()">삭제</a>
	</c:if>
	<a href="list.do?condition=${condition }&keyword=${keyword }">목록 보기</a>
	<div>
		<c:if test="${dto.prevNum ne 0 }">
			<a href="detail.do?num=${dto.prevNum }&condition=${condition }&keyword=${keyword }">이전 글</a>
		</c:if>
		<c:if test="${dto.nextNum ne 0 }">
			<a href="detail.do?num=${dto.nextNum }&condition=${condition }&keyword=${keyword }">다음 글</a>
		</c:if>
	</div>
	<!-- 댓글에 관련된 UI -->
	<div class="comments">
		<div class="comment_form">
			<form action="comment_insert.do" method="post">
				<input type="hidden" name="writer" value="${id }"/>
				<input type="hidden" name="ref_group" value="${dto.num }"/>
				<input type="hidden" name="target_id" value="${dto.writer }"/>
				<textarea name="content" cols="100%" rows="3"></textarea>
				<button type="submit">등록</button>
			</form>
		</div>
		<c:forEach var="tmp" items="${commentList }">
			<c:choose>
				<c:when test="${tmp.deleted eq 'Y' }">
					<div class="comment">
						<pre>${tmp.content }</pre>
					</div>
				</c:when>
				<c:otherwise>
					<div class="comment" <c:if test="${tmp.num ne tmp.comment_group }">style="margin-left: 30px;"</c:if>>
						<c:if test="${tmp.num ne tmp.comment_group }">
							<img src="${pageContext.request.contextPath }/resources/images/re.gif" alt="" class="reply_icon"/>
							<i class="muted">${tmp.target_id }</i>
						</c:if>
						<img src="${pageContext.request.contextPath }/resources/images/user_image.gif" alt=""/>
						<span>${tmp.writer }</span>
						<span>${tmp.regdate }</span>
						<a href="javascript:" class="reply_link">답글</a>
						<c:choose>
							<c:when test="${sessionScope.id eq tmp.writer }">
								<a href="javascript:" class="reply_update">수정</a>
								<a href="javascript:deleteConfirmR(${tmp.num }, ${tmp.comment_group })">삭제</a>
							</c:when>
							<c:otherwise>
								<a href="">신고</a>
							</c:otherwise>
						</c:choose>
						<pre>${tmp.content }</pre>
						<form action="comment_insert.do" method="post" class="insert" style="margin-left: 30px;">
							<img src="${pageContext.request.contextPath }/resources/images/re.gif" alt="" class="reply_icon"/>
							<!-- 덧글 작성자 -->
							<input type="hidden" name="writer" value="${id }"/>
							<!-- 덧글 그룹 -->
							<input type="hidden" name="ref_group" value="${dto.num }"/>
							<!-- 덧글 대상 -->
							<input type="hidden" name="target_id" value="${tmp.writer }"/>
							<input type="hidden" name="comment_group" value="${tmp.comment_group }"/>
							<textarea name="content" cols="100%" rows="3"></textarea>
							<button type="submit">등록</button>
						</form>
						<form action="comment_update.do" method="post" class="update">
							<input type="hidden" name="num" value="${tmp.num }"/>
							<input type="hidden" name="ref_group" value="${dto.num }"/>
							<input type="hidden" name="target_id" value="${tmp.writer }"/>
							<input type="hidden" name="comment_group" value="${tmp.comment_group }"/>
							<textarea name="content" cols="100%" rows="3">${tmp.content }</textarea>
							<button type="submit">수정</button>
						</form>
					</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
</div>
<script>
	function deleteConfirm() {
		var isDelete=confirm("글을 삭제하시겠습니까?");
		if(isDelete) {
			location.href="delete.do?num=${dto.num }";
		}
	}
	function deleteConfirmR(num, commentGroup) {
		var isDelete=confirm("댓글을 삭제하시겠습니까?");
		if(isDelete) {
			location.href="comment_delete.do?num="+num+"&comment_group="+commentGroup+"&ref_group=${dto.num }"
		}
	}
	/* 
	var isSignin=${isSignin};
	$(".comment_form > form, .comment > form").submit(function() {
		if(!isSignin) {
			var isGoSignin=confirm("로그인이 필요합니다.");
			if(isGoSignin) {
				location.href="${pageContext.request.contextPath}/users/signinform.do?url=${pageContext.request.contextPath}/cafe/detail.do?num=${dto.num}";
			}
			return false;
		}
	});
	 */
	//답글 링크를 눌렀을 때
	$(".comment .reply_link").click(function() {
		if($(this).text()=="답글") {
			$(this).text("취소");
		} else {
			$(this).text("답글");
		}
	});
	$(".comment .reply_link").click(function() {
		$(this).parent().find(".insert").slideToggle(200);
	});
	$(".comment .reply_update").click(function() {
		if($(this).text()=="수정") {
			$(this).text("취소");
			$(this).parent().find("pre").hide();
		} else {
			$(this).text("수정");
			$(this).parent().find("pre").show();
		}
	});
	$(".comment .reply_update").click(function() {
		$(this).parent().find(".update").toggle();
	});
</script>
</body>
</html>