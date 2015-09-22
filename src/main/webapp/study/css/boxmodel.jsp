<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boxmodel.jsp</title>
<%-- <%@ include file="/WEB-INF/views/common.jspf" %> --%>
<style type="text/css">
	.box {
		width: 100px;
		height: 100px;
		background-color: orange;
		display: inline-block;
		vertical-align: top;
	}
	.border {
		border: 10px solid red;
	}
	
	.padding {
		padding: 10px;
	}
	
	.boxsizing {
		 box-sizing: border-box;
	}
</style>

</head>
<body>
<h1>boxmodel</h1>
<div class="box border padding">box1</div>
<div class="box">box2</div>
<div class="box border padding boxsizing">box3</div>

</body>
</html>