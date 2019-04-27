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
    
    <title>My JSP 'upStudent2.jsp' starting page</title>
    
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
   <s:form action="UpAction2" method="post">
  <table align="center"  width="669" height="231" cellspacing="0" cellpadding="0">
  <tr>
  <th>修改学生信息</th>
  </tr>
  <%
 ArrayList list=(ArrayList)session.getAttribute("studens");
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
 <tr>
 <td><s:text name="id"></s:text></td>
                     <td>
                         <input type="hidden" name="id" value="<%=a.getId()%>"/>
                     </td>
               </tr>
 <tr>
 <td><s:text name="学号"></s:text></td>
                     <td>
                         <input type="text" name="xuehao" value="<%=a.getXuehao()%>"/>
                     </td>
               </tr>
               <tr>
                   <td><s:text name="姓名"></s:text></td>
                     <td>
                         <input type="text" name="name" value="<%=a.getName()%>"/>
                     </td>
               </tr>
               <tr>
                   <td><s:text name="性别"></s:text></td>
                     <td>
                         <input type="text" name="sex" value="<%=a.getSex()%>"/>
                     </td>
               </tr>
               <tr>
                   <td><s:text name="年龄"></s:text></td>
                     <td>
                        <input type="text" name="age" value="<%=a.getAge()%>"/>
                     </td>
               </tr>
               <tr>
                   <td><s:text name="体重"></s:text></td>
                     <td>
                         <input type="text" name="weight" value="<%=a.getWeight()%>"/>
                     </td>
               </tr>
               <tr>
                 <td colspan="2" align="center">
                     <input type="submit" value="确 定" size="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <input type="reset" value="清 除" size="12">
                 </td>
               </tr>
  <%
  
  }
  
  } %>
  </table>
  </s:form>
  </body>
</html>
