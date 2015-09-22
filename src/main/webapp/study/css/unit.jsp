<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>unit.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf" %>
<style type="text/css">
	.box {
		margin: 10pt;
		background-color: olive;
		height: 20pt;
		display: block;
	}
	.p {
		font-size: 10pt;
		
	}
</style>

</head>
<body>
<h1>unit</h1>
<h2>
% : 							<br>
px : dpi(density per inch) 1px	<br>
inch : 1 inch = 2.54cm = 25.4mm <br>
cm : 1 cm = 10 mm				<br>
mm : 							<br>
pt : 1 pt = 1/72 inch			<br>
pc : 1 pc = 12pt				<br>
em : 1 em = 현재폰트의 1배수			<br>
ex : 1 ex = 현재폰트의 0.5배			<br>
</h2>
<box class="box" style="width: 1in">box1</box>
<box class="box" style="width: 2.54cm">box2</box>
<box class="box" style="width: 25.4mm">box3</box>
<box class="box" style="width: 72pt">box4</box>
<box class="box" style="width: 96px">box5</box>

<p>Hello Units none</p>
<p style="font-size: 1em;">Hello Units 1em</p>
<p style="font-size: 2em;">Hello Units 2em</p>
<p style="font-size: 1ex;">Hello Units 1ex</p>
<p style="font-size: 2ex;">Hello Units 2ex</p>



</body>
</html>