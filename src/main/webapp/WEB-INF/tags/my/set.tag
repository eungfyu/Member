<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty"%>
<%@ attribute name="var" required="true" rtexprvalue="false"%><%--디폴트로 스트링임. name에는 EL이나 익스프레션이 오면 안됨 --%> 
<%@ attribute name="value" required="true" type="java.lang.Object"%><%--스트링 올수도 다른 거 올수도 있고 뭐든지 담을 수 있으니 오브젝트로 --%>
<%@ variable name-from-attribute="var" 
			 alias="setvar" 
			 variable-class="java.lang.Object" 
			 scope="AT_END"
			 %>
<%--위 attribute name=으로 들어오는 값를 variable의 변수로 만들겠다.variable엔 뭐가 들어올지 모르니 별칭을 준다alias.--%> 
<%--이 EL변수의 타입이 무었인가=>   --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	jspContext.setAttribute("setvar", value); //여기서 만든걸 위 코드의 scope를 써서 jsp파일로 보낸다. 
 %>

<%-- <c:set var="setvar" value="${false}"/> --%>
