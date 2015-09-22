<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="property" required="true"%><%-- 태그의 속성은 attribute라고 표현함. 이름,속성필요함을 지정. 필수속성으로 지정했음 --%>

<%
	out.print("<sitemesh:write property=\""+ property + "\"/>");
%>