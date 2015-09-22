<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="test" required="true" type="java.lang.Boolean"%> 

<%
	if(test) {
%>
	<jsp:doBody/>
<%
	}
%>

<%--test 
out.println("%%%%<br>");
%>
	<jsp:doBody/><!--바디를 수행한 결과가 나옴
	test = ${test }<br>
<%
	out.println(test+"<br>");
	out.println("%%%%<br>");
%>
 --%>