<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>animation.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf" %>
<style type="text/css">
	.box {
		width: 100px;
		height: 100px;
		background-color: orange;
		display: inline-block;
		vertical-align: top;
	}
	.box3 {
		width: 100px;
		height: 100px;
		background-color: orange;
		display: inline-block;
		vertical-align: top;
		position: relative;
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
	
	.box1_animation {
		animation: box1 2s infinite;
	}
	.box2_animation {
		animation: box2 3s infinite;
	}
	.box3_animation {
		animation: box3 3s infinite;
	}
	@keyframes box1 {
		0% {
			width: 10px;
			transform: scale(0.5, 0.5) rotateY(90deg);
			background-color: red;
		}
		50% {
			width: 100px;
			transform: scale(1, 1) rotateY(180deg);
			background-color: yellow;
		}
		100% {
			width: 200px;
			transform: scale(2, 2) rotateY(360deg);
			background-color: pink;
		}
	}
	@keyframes box2 {
		0%   {background-color:red; left:0px; top:0px;}
	    25%  {background-color:yellow; left:200px; top:0px;}
	    50%  {background-color:blue; left:200px; top:200px;}
	    75%  {background-color:green; left:0px; top:200px;}
	    100% {background-color:red; left:0px; top:0px;}
	}
	@keyframes box3 {
	    0%   {background-color:red; left:0px; top:0px;}
	    25%  {background-color:yellow; left:200px; top:0px;}
	    50%  {background-color:blue; left:200px; top:200px;}
	    75%  {background-color:green; left:0px; top:200px;}
	    100% {background-color:red; left:0px; top:0px;}
	}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$('div').eq(0).on('mouseover',function(){
		//alert('xxx');
		$(this).addClass('box2_animation');
	});
	$('div').eq(0).on('mouseout',function(){
		//alert('yyy');
		$(this).removeClass('box2_animation');
	});
});
$(document).ready(function(){
	$('div').eq(1).on('mouseover',function(){
		//alert('xxx');
		$(this).addClass('box2_animation');
	});
	$('div').eq(1).on('mouseout',function(){
		//alert('yyy');
		$(this).removeClass('box2_animation');
	});
});
$(document).ready(function(){
	$('div').eq(2).on('mouseover',function(){
		//alert('xxx');
		$(this).addClass('box3_animation');
	});
	$('div').eq(2).on('mouseout',function(){
		//alert('yyy');
		$(this).removeClass('box3_animation');
	});
});
</script>
</head>
<body>
<h1>animation</h1>
<div class="box border padding">box1</div>
<div class="box">box2</div>
<div class="box3 border padding boxsizing">box3</div>

</body>
</html>