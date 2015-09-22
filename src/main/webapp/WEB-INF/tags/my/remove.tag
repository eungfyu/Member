<%@tag import="java.security.InvalidParameterException"%>
<%@tag import="javax.naming.directory.InvalidAttributesException"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty"%>
<%@ attribute name="var" required="true" rtexprvalue="false"%> 
<%@ attribute name="scope"%>
<%@ variable name-from-attribute="var"
			 scope="AT_BEGIN" 
			 alias="avar"
			 variable-class="java.lang.String"
%>
<%
	if(scope==null){
	jspContext.removeAttribute("avar");//pageContext에서 지우는 것
	//jspContext.setAttribute("avar",	"xxxxyyyy");//jsp의 xxx변수를 xxxxyyyy로 복사.
	
	request.removeAttribute(var);//jspContext에서만 삭제했다. pageContext에서는 아직 살아있음?
	session.removeAttribute(var);
	application.removeAttribute(var);
	} else {
		jspContext.setAttribute("avar",	null);
		switch (scope){
		case "page" :
			jspContext.removeAttribute("avar");
			break;
		case "request" :
			request.removeAttribute(var);
			break;
		case "session" :
			session.removeAttribute(var);
			break;
		case "application" :
			application.removeAttribute(var);
			break;
		default:
			throw new InvalidParameterException("scope error");
		}
	 
	}
%>