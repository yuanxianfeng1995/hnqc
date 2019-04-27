<%@ page language="java" import="java.util.*,javabean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body bgcolor="#CCCCFF"> 
  <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
          <tr>
              <td width="25%">
                  <s:a href="upStudent.jsp">修改学生信息 </s:a>
              </td>
            <td width="25%">
            <s:a href="zz.jsp">查看学生信息</s:a>
                  
              </td>
              <td width="25%">
                  <s:a href="tjStudent.jsp">添加学生信息</s:a>
              </td>
               <td width="25%">
              <s:text name="删除学生信息"></s:text>
              </td>
          </tr>
      </table>
      <s:form action="dateAction" method="post">
  <table align="center"  width="669" height="231" cellspacing="0" cellpadding="0">
  <tr>
  
  <td>请选择要删除的学号</td>
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
</html>
