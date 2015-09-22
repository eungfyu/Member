<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sitemesh" tagdir="/WEB-INF/tags/sitemesh" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><sitemesh:write property="title"/></title>

<sitemesh:write property="head"/>

<style type="text/css">
	#world, #employee {
		width: 150px;
		position: absolute;
	}
	#main{
		margin-left:180px !important;
	}

</style>


<script type="text/javascript">
	var zIndex = 100;
	
	$(document).ready(function(){
		$('nav.w3-topnav > a').on('click', function(){
			//alert("link click...href = "+ $(this).attr('href'));
			var menu = $(this).attr('href');
			
			switch (menu) {
			case '#world':
				$('#world').css('z-index', zIndex++);
				return false;
				break;
			case '#employee':
				$('#employee').css('z-index', zIndex++);
				return false;
				break;
			default:
				break;
			}
			
		});
	});
</script>
</head>
<body>
<c:url value="/index.jsp" var="home"/>
<c:url value="/login" var="login"/>
<c:url value="/logout" var="logout"/>

<nav class="w3-container w3-topnav w3-grey w3-margin w3-card-12">
	<a href="${home}">Home</a>
	<a href="#world">World</a>
	<a href="#employee">Employee</a>
	<c:choose>
		<c:when test="${not empty auth}">
<%-- 		<c:when test="${empty auth}"> --%>
			<a href="${logout}" class="w3-right">logout</a>
			<a href="#" class="w3-right">${auth.name}</a>
		</c:when>
		<c:otherwise>
			<a href="${login}" class="w3-right">Login</a>
		</c:otherwise>
	</c:choose>
	
	
</nav>
<nav id="world" class="w3-container w3-sidenav w3-teal w3-margin">
	<a href="#">World ein</a>
	<a href="#">World zwei</a>
	<a href="#">World tri</a>
	<a href="#">World four</a>
	<a href="#">World five</a>
	<a href="#">World six</a>
</nav>
<c:url value="/member/regist" var="member_regist"/>
<nav id="employee" class="w3-container w3-sidenav w3-red w3-margin">
	<a href="${member_regist}">Resigter</a>
	<a href="#">Employee zwei</a>
	<a href="#">Employee tri</a>
	<a href="#">Employee four</a>
	<a href="#">Employee five</a>
	<a href="#">Employee six</a>
</nav>
<section id="main" class="w3-container w3-margin w3-card-12">
	<sitemesh:write property="body"/>
	
</section>
</body>
</html>