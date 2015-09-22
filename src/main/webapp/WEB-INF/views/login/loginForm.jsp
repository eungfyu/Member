<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf" %>
<style type="text/css">

</style>

</head>
<body>
<h1>Login</h1>
<form:form commandName="login" action="login" method="post" cssClass="w3-container">

	<form:errors element="div"/>
<!-- Email -->
	<div class="w3-group">
		<form:input path="email" cssClass="w3-input" required="required" />
		<label for="email" class="w3-label">Email</label>
<!-- 		<script type="text/javascript"> -->
 			<!-- $('#email').attr("required", "required"); -->
<!-- 		</script> -->
	</div>
<!-- password -->
	<div class="w3-group">
		<form:password path="password" cssClass="w3-input" required="required"/>
		<label for="password" class="w3-label">Password</label>
	</div>
<!-- Remember -->
	<div>
		<label for="remember" class="w3-checkbox">
		<c:choose>
			<c:when test="${login.remember eq true }">
				<input id="remember" name="remember" type="checkbox" value="true" checked="checked"/>
			</c:when>
			<c:otherwise>
				<input id="remember" name="remember" type="checkbox" value="true"/>
			</c:otherwise>
		</c:choose>
		<span class="w3-checkmark"></span>Remember
		</label>
	</div>
	
	<input class="e3-btn" type="submit" value="login"/>
</form:form>
</body>
</html>