<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/signup_form.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
</head>
<body ng-app="myApp">
<div class="container">
	<h3>회원 가입 페이지 입니다.</h3>
	<form ng-submit="onSubmit($event)" ng-controller="formCtrl" action="signup.do" method="post" name="signupForm" novalidate>
		<div class="form-group has-feedback" ng-class="{'has-success':signupForm.id.$valid&&canUseId ,'has-error':(signupForm.id.$invalid||!canUseId)&& f.id.$dirty }">
			<label for="id" class="controll-label">아이디</label>
			<input type="text" name="id" class="form-controll" ng-model="id" ng-change="onIdInput()" ng-minlength="4" ng-pattern="/^[a-zA-Z0-9]+$/"/>
			<span class="form-control-feedback glyphicon glyphicon-ok" ng-show="signupForm.id.$valid&&canUseId"></span>
			<span class="form-control-feedback glyphicon glyphicon-remove" ng-show="(signupForm.id.$invalid||!canUseId)&&signupForm.id.$dirty"></span>
			<p class="help-block" ng-show="signupForm.id.$error.required&&signupForm.id.$dirty">반드시 입력하세요</p>
			<p class="help-block" ng-show="!canUseId&&signupForm.id.$dirty">사용할수 없는 아이디 입니다.</p>
			<p class="help-block" ng-show="signupForm.id.$error.minlength&&signupForm.id.$dirty">4글자 이상 입력하세요</p>
			<p class="help-block" ng-show="signupForm.id.$error.pattern&&signupForm.id.$dirty">특수 문자는 허용하지 않습니다.</p>
		</div>
		<div class="form-group has-feedback" ng-class="{'has-success':signupForm.pwd.$valid,'has-error':(signupForm.pwd.$invalid||!isPwdEqual)&&signupForm.pwd.$dirty }">
			<label for="pwd1" class="controll-label">비밀번호</label>
			<input type="password" name="pwd1" class="form-controll" ng-model="pwd1" ng-required="true" ng-change="onPwdInput()" ng-minlength="4" ng-pattern="/^[a-zA-Z0-9]+$/"//>
			<span class="form-control-feedback glyphicon glyphicon-ok" ng-show="signupForm.pwd1.$valid&&isPwdEqual"></span>
			<span class="form-control-feedback glyphicon glyphicon-remove" ng-show="(signupForm.pwd1.$invalid||!isPwdEqual)&&signupForm.pwd1.$dirty"></span>
			<p class="help-block" ng-show="signupForm.pwd1.$error.required&&signupForm.pwd1.$dirty">반드시 입력하세요</p>
			<p class="help-block" ng-show="signupForm.pwd1.$error.minlength&&signupForm.pwd1.$dirty">4글자 이상 입력하세요</p>
			<p class="help-block" ng-show="signupForm.pwd1.$error.pattern&&signupForm.pwd1.$dirty">특수 문자는 허용하지 않습니다.</p>
			<p class="help-block" ng-show="!isPwdEqual&&signupForm.pwd1.$dirty">비밀번호 확인란과 같아야 합니다</p>
		</div>
		<div class="form-grop has-feedback" ng-class="{'has-success':isPwdEqual,'has-error':!isPwdEqual&&signupForm.pwd1.$dirty }">
			<label for="pwd2" class="controll-label">비밀번호 확인</label>
			<input type="password" name="pwd2" class="form-controll" ng-model="pwd2" ng-change="onPwdInput()"/>
		</div>
		<div class="form-group has-feedback" ng-class="{'has-success':signupForm.email.$valid,'has-error':signupForm.email.$invalid&&signupForm.email.$dirty }">
			<label for="email" class="controll-label">이메일</label>
			<input type="text" name="email" class="form-controll" ng-model="email" ng-required="true" ng-pattern="/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/"/>
			<span class="form-control-feedback glyphicon glyphicon-ok" ng-show="signupForm.email.$valid"></span>
			<span class="form-control-feedback glyphicon glyphicon-remove" ng-show="signupForm.email.$invalid&&signupForm.email.$dirty"></span>
			<p class="help-block" ng-show="signupForm.email.$error.required&&signupForm.email.$dirty">반드시 입력하세요</p>
			<p class="help-block" ng-show="signupForm.email.$error.pattern&&signupForm.email.$dirty">이메일 형식을 확인하세요</p>
		</div>
		<div class="form-group">
			<button type="submit" ng-disabled="signupForm.$invalid||!canUseId||!isPwdEqual" class="btn btn-success">가입</button>
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath }/resources/js/angular.min.js"></script>	
<script>
	var app=angular.module("myApp", []);
	app.controller("formCtrl", function($scope, $http) {
		$scope.onIdInput=function() {
			$http({
				url:"checkid.do",
				method:"post",
				data:"inputId="+$scope.id,
				headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"}
			}).success(function(data){
				$scope.canUseId=data.canUse;
			});
			/* 
			$http({
				url:"checkid.do",
				method:"get", 
				params:{inputId:$scope.id}
			}).success(function(data) {
				if(data.canUse) {
					$scope.checkResult="사용가능";
					$scope.checkIdColor="#00ff00";
					$scope.formValid=true;
				} else {
					$scope.checkResult="사용불가";
					$scope.checkIdColor="#ff0000";
					$scope.formValid=false;
				}
			});
			 */
		};
		//두개의 비밀번호를 같게 입력했는지 여부 
		$scope.isPwdEqual=false;
		//비밀번호를 입력했을때 호출되는 함수 
		$scope.onPwdInput=function(){
			if($scope.pwd1==$scope.pwd2){
				$scope.isPwdEqual=true;
			}else{
				$scope.isPwdEqual=false;
			}
		};
		$scope.formValid=false;
		$scope.onSubmit=function(e) {
			if($scope.signupForm.$invalid||!$scope.canUseId||!$scope.isPwdEqual) {
				e.preventDefault();	
			}
		};
	});
	/* jquery
	//폼의 유효성 여부
	var formValid=false;
	$("#signupForm").submit(function(){
		//만일 폼의 유효성 여부가 false 이면 
		if(formValid==false){
			return false;//폼 전송 막기
		}
	});
	//아이디 입력란에 입력했을때 실행할 함수 등록 
	$("#id").on("input", function() {
		//입력한 아이디를 읽어와서
		var inputId=$(this).val();
		//ajax 요청을 이용해서 서버에 보낸다.
		$.ajax({
			url:"checkid.do",
			method:"post",
			data:{"inputId":inputId},
			success:function(responseData) {
				//뭐가 응답되는지 콘솔에 출력해 보기 
				console.log(responseData);
				// responseData 는 object 이다.
				// {canUse:true} 또는 {canUse:false}
				if(responseData.canUse) {
					formValid=true;
					$("#checkResult")
					.text("사용가능")
					.css("color","#00ff00");
				} else {
					formValid=false;
					$("#checkResult")
					.text("사용불가")
					.css("color","#ff0000");
				}
			}
		});
	});
	 */
</script>
</body>
</html>