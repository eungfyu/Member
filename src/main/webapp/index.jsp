<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf" %>
<style type="text/css">
	.scale-animation {
		display: inline-block;
		animation: scale 3s infinite alternate;
	}
	.move-animation {
		display: inline-block;
		animation: move 3s infinite alternate;
	}
	.rotate-animation {
		display: inline-block;
		animation: rotate 3s infinite alternate;
	}
	
	@keyframes scale {
		from {
			transform : scale(0.5, 0.5);
		}
		to {
			transform : scale(1.5, 1.5);
		}
	}
	
	@keyframes move {
		from {
			transform : translateX(0px);
		}
		to {
			transform : translateX(200px);
		}
	}
	
	@keyframes rotate {
		from {
			transform : rotateX(0deg);
		}
		to {
			transform : rotateX(360deg);
		}
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		var animations = ["scale-animation","move-animation","rotate-animation"];
		var index = 0;
		
		setInterval(function() {
			index++;
		/*
		 * removeClass
		 */
		$.each(animations, function(index, value) {
			$('#home').removeClass(value);
		});
		 /*
		  * addClass
		  */
		  $('#home').addClass(animations[index%3]);
			
			console.log("index[" + index%3+ "] = "+animations[index%3]);
		}, 5000);
	});//setInterval 일정 시간이 되면 함수 호출. 이벤트.
</script>
</head>
<body>
<h1>index</h1>
<%-- <c:redirect url="/member/regist"/> --%>
<div class="panel panel-primary">
	<h1 id="home" class="panel-head scale-animation">World Employee Home</h1>
</div>
</body>
</html>