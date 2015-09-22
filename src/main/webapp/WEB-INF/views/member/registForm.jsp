<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>registForm.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf" %>
<style type="text/css">


	form {
		width: 400px;
		border-top: 1px double orange;
		border-bottom: 1px double orange;
	}
	
	.center {
		text-align: center;
		text-shadow: 5px 5px 1px olive; 
	}
	
	#member {
		margin: auto auto;
	}
	
	label[for^=gender], label[for^=hobby] {/*나열연산했음 */
		padding-left: 5px;
		padding-right: 10px;
		color: maroon;
	}

</style>

<!--이 위치에서의 코딩은 맨 위부터의 코드를 읽어오는데 아래 코딩은 아직 안 읽은 상태기 때문에 여기서의 자바스크립트 수행(코딩)은 의미가 없다.-->

<script type="text/javascript">
//$(document) 준비핸들러 혹은 ready핸들러. head안에서 쓸때는 반드시 이 준비핸들러를 쓴다. 이제 이 핸들러 안에서는 $('form') 명령이 먹는다.
$(document).ready(function() {
	$('form').slideToggle().slideToggle(1000)
			 .fadeOut(500).fadeIn(1000, function(){
				$('label[for^=gender]').css('backgroundColor','pink')
									   .fadeOut(1000).fadeIn(1000);
	});
	
});

</script>

</head>
<body>

<h1 class="center"><a href="regist"><spring:message code="member.regist.title"/></a></h1>
 <form:form commandName="member" action="regist" method="post">

<!-- global error가 있으면 여기서 찍는다 -->
	<form:errors element="div"/>

<!-- Email -->
	<div class="form-group">
		<label for="email"><spring:message code="member.regist.email"/></label>
		<form:input path="email" cssClass="form-control"/>
		<form:errors path="email"/>
	</div> 
<!-- password  -->
	<div class="form-group">
		<label for="password"><spring:message code="member.regist.password"/></label>
		<form:input path="password" cssClass="form-control"/>
		<form:errors path="password"/>
	</div> 
<!-- name -->
	<div class="form-group">
		<label for="name"><spring:message code="member.regist.name"/></label>
		<form:input path="name" cssClass="form-control"/>
		<form:errors path="name"/>
	</div> 
<!-- gender -->
	<div class="form-group">
		<div><spring:message code="member.regist.gender"/></div>
		<form:radiobuttons path="gender" items="${gender}" cssStyle="margin:0px 5px 5px;"/>
		<form:errors path="gender"/>
	</div>
<!-- hobby -->
	<div class="form-group">
	<div><spring:message code="member.regist.hobby"/></div>
	<form:checkboxes items="${hobby}" path="hobby" itemLabel="label" itemValue="code"/>
	<form:errors path="hobby"/>
	</div>	
<!-- comment -->
	<div class="form-group">
		<div><spring:message code="member.regist.comment"/></div>
		<form:textarea path="comment" cssClass="form-control" rows="10"/>
		<form:errors path="comment"/>
	</div>
<!-- 	Email Reception true/false -->
	<div class="form-group">
		<label for="reception1"><spring:message code="member.regist.reception"/></label>
		<form:checkbox path="reception"/>
		<form:errors path="reception"/>
	</div>


 <input type="submit" value="회원가입"/>
 </form:form>
 
<!-- 이 위치는 form이 만들어진 이후이기 때문에 아래 $('form') 명령이 수행된다. --> 
<!--  <script type="text/javascript"> -->
<!--  $('form').slideToggle().slideToggle(1000); -->
<!-- </script> -->

</body>
</html>