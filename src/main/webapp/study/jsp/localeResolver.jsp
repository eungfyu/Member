<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>localeResolver.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf" %>
</head>
<body>
<c:forEach var="a" items="${sessionScope}">
${a.key} = ${a.value} <br>

</c:forEach>
</body>
</html>