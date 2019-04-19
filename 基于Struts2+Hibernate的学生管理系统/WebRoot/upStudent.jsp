<%@ page language="java" import="java.util.*,javabean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'upStudent.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
 <body bgcolor="#CCCCFF"> 
  <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
          <tr>
              <td width="25%">
                  <s:text name="修改学生信息 "></s:text>
              </td>
            <td width="25%">
                  
                  <s:a href="zz.jsp">查看学生信息 </s:a>
              </td>
              <td width="25%">
                  <s:a href="tjStudent.jsp">添加学生信息</s:a>
              </td>
               <td width="25%">
                  <s:a href="dateStudent.jsp">删除学生信息</s:a>
              </td>
          </tr>
      </table>
   <s:form action="UpAction" method="post">
  <table align="center"  width="669" height="231" cellspacing="0" cellpadding="0">
   <tr>
  
  <td>请选择要修改的学号</td>
  <td>
 
<select name="xuehao" >
 <%
 ArrayList list=(ArrayList)session.getAttribute("list");
  if(list.size()==0)
		{
%>
<tr>
<td >list为空</td>  
</tr>
<%

}else

{for(int i=0;i<list.size();i++)
		{
			
			Student a=new Student();
		a=(Student) list.get(i);
 %>

<option value="<%=a.getXuehao()%>"><%=a.getXuehao() %></option>
<%
}
} %>
</select>
</td>
<td>
<s:submit value="确定"/>
</td>
</tr>
  </table>
  </s:form>
  </body>
  </body>
</html>
