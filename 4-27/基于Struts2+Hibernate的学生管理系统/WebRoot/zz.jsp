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
    
    <title>My JSP 'zz.jsp' starting page</title>
    
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
                  <s:a href="upStudent.jsp">修改学生信息 </s:a>
              </td>
            <td width="25%">
                  <s:text name="查看学生信息"></s:text>
              </td>
              <td width="25%">
                  <s:a href="tjStudent.jsp">添加学生信息</s:a>
              </td>
               <td width="25%">
                  <s:a href="dateStudent.jsp">删除学生信息</s:a>
              </td>
          </tr>
      </table>
  <table align="center"  width="669" height="231" cellspacing="0" cellpadding="0">
  <tr>
  <th>记录条数</th>
  <th>学号</th>
  <th>姓名</th>
  <th>性别</th>
  <th>年龄</th>
  <th>体重</th>
  <tr>
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

<tr align="center">
<td ><%=a.getId() %>
<td ><%=a.getXuehao() %>
<td ><%=a.getName()%>
<td ><%=a.getSex()%>
<td ><%=a.getAge()%>
<td ><%=a.getWeight()%>
</td>
</tr>
<%
}
} %>
  </table>
  </body>
</html>
