<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>template.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf" %>
<style type="text/css">
	p {
		transition : transform 1s;
/* 		background-color : olive; */
	}
</style>
<script type="text/javascript">
	// 데이타 전송방법 두가지 XML과 JSON. JSON 많이 씀
	// JSON 자바스크립트의 객체 표기 방법
// 	var member = {
// 			email : "xxx@webapp.com",
// 			password : "1234",
// 			name : "벨라",
// 			genger : "female",
// 			hobby : ["crusio", "avadaKedavra"],
// 			comment : "pureBlood",
// 			reception : true
// 	};
$(document).ready(function() {
	$('button').on("click", function() {
		
		//Ajax
		$.getJSON("member.json", function(member) {
		console.log(member);
		var message = "email = " + member.email + "<br>" +
					  "name = " + member.name + "<br>" +
					  "password = " + member.password + "<br>"+
					  "gender = " + member.gender + "<br>" +
					  "hobby = " + member.hobby + "<br>" +
					  "reception = " + member.reception; 
// 		$('p').text(message);
// 		$('p').html(message);
// 		$('p').append(message);//뒤에 추가됨 
		$('p').prepend(message);//앞에 추가됨
		
// 		.each 향상된 for문 같은거임
		$.each(member.hobby, function(index, value) {
			console.log("hobby["+ index+"] =" + member.hobby[index]);
			console.log("hobby["+ index+"] =" + value); // member.hobby[index] = value
		});
// 		자바스크립트for문임. 위 코드랑 같은 거임
		for (var i=0;i<member.hobby.length;i++){
			console.log("hobby["+ i+"] =" + member.hobby[i]);
		}
		
		});
	});
	
	$('p').on("click", function() {
		$(this).css("transform", "translate(50px,50px)");
	});
});
</script>
</head>
<body>
<h1>template</h1>
<button>member print uno</button>
<button>member print two</button>
<p>
	print uno
</p>
<p>
	print zwei
</p>
</body>
</html>