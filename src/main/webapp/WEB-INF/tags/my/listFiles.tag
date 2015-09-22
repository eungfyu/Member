<%@tag import="java.io.File"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty"%>
<%@ attribute name="var" required="true" rtexprvalue="false"%><%--rtexprvalue: 이 변수에는 EL과 익스프레션이 올수가 없다. --%>
<%@ attribute name="path" required="true"%>
<%@ variable alias="filevar" 
			 name-from-attribute="var" 
			 variable-class="java.io.File[]" 
			 scope="AT_END"
%> 

<%
	File[] files = new File(path).listFiles();
	jspContext.setAttribute("filevar", files);
%>