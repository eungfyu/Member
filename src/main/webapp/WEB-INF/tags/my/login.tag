<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty"%><%--이 로그인 커스텀 태그는 바디가 없다. 그래서 body-content에 디폴트인 scriptless 말고 empty  --%>
<%@ attribute name="test" required="true" type="java.lang.Boolean"%> 
<%@ attribute name="cls"%><%--속성 하나 더 만듬 --%>

<%
	if (test) {
%>
	<button class="${cls}"> my: My Log Out</button>
<%
	} else {
%>
	<button class="${cls} btn btn-primary"> my: My Log In</button>
<%
	}
%>

<%--  <%@ attribute name="class"%> 이거 추가 전의 코드
<%
	if (test) {
%>
	<button class="btn btn-default"> my: My Log Out</button>
<%
	} else {
%>
	<button class="btn btn-primary"> my: My Log In</button>
<%
	}
%>
--%>