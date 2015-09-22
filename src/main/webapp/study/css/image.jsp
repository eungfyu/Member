<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>image.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf" %>
<style type="text/css">
	span, img {
		border: 1px solid orange;
		height: 150px;
		width: 150px;
		float: right;		
	}	

</style>

</head>
<body>
<h1>display : none | inline | block | inline-block</h1>

<span>display span inline</span>
<img style="vertical-align: bottom;"  alt="100x100" src="http://www.placehold.it/100x100">
<p>
9월 09, 2015 9:23:17 오전 org.apache.coyote.AbstractProtocol start
정보: Starting ProtocolHandler ["ajp-nio-8089"]
9월 09, 2015 9:23:17 오전 org.apache.catalina.startup.Catalina start
정보: Server startup in 15621 ms
9월 09, 2015 9:23:17 오전 org.apache.catalina.core.ApplicationContext log
정보: default: DefaultServlet.serveResource:  Serving resource '/' headers and data
9월 09, 2015 9:45:24 오전 org.apache.catalina.core.ApplicationContext log
정보: default: DefaultServlet.serveResource:  Serving resource '/favicon.ico' headers and data
9월 09, 2015 9:45:24 오전 org.apache.catalina.core.ApplicationContext log
정보: default: DefaultServlet.serveResource:  Serving resource '/favicon.ico' headers and data

</p>
</body>
</html>