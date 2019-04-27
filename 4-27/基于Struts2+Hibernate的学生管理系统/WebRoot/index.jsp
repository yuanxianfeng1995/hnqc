<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
   <s:form action="LoginAction" method="post">
  <br> <br> <br> <br> <br> <br>
   
   <table align="center" >
  <h1>登陆界面</h1>             
   <tr>
  <td><s:textfield name="username" label="登录名" size="30"/></td>
   </tr>
   <tr>
  <td><s:password name="password" label="登录密码" size="32"/></td>
   </tr>
 
   <tr>
                                <td colspan="2" align="center">
                                    <input type="submit" value="确定"/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="reset" value="清空"/>
                                </td>
                            </tr>
                            <tr >
                                <td colspan="2" align="center">
                                    <s:a href="zhuce2.jsp">注册</s:a>
                               
                          
   </table>

   </s:form>
  </body>
</html>
