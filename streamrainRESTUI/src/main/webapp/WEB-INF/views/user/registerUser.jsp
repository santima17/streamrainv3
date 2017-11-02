<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 
<html xmlns="http://www.w3.org/1999/xhtml">
 
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Register User</title>
		<style type="text/css">
			body {
				font-family: verdana, sans-serif;			
			}
			
			span.fielderror {
				color: red;
			}
			
		</style>	
	</head>
	 
	<body>			
		<h1>Register User</h1>
		<c:if test="${not empty message}">
			<td style="font-style: italic; color: green;">${message}</td>
		</c:if>
		<c:if test="${not empty error}">
			<td style="font-style: italic; color: red;">${error}</td>
		</c:if>
		<c:url value="registerProcess" var="destino"/>			
		<form:form method="post" action="${destino}" commandName="userDto">
			<table>
				<tbody>
					<tr>
						<td>Email:</td>
						<td><form:input path="email" /></td>
						<td><form:errors path="email" cssClass="fielderror"/></td>
					</tr>
					<tr>
						<td>Nickname:</td>
						<td><form:input path="nickname" /></td>
						<td><form:errors path="nickname" cssClass="fielderror"/></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><form:password path="password" /></td>
						<td><form:errors path="password" cssClass="fielderror"/></td>
					</tr>
					<tr>
						<td>Confirm Password:</td>
						<td><form:password path="confirmPass" /></td>
						<td><form:errors path="confirmPass" cssClass="fielderror"/></td>
					</tr>
					<tr>
						<td>Country: </td>
						<td>
						<form:select path="country" >
						 <form:option value="NONE" label="--- Select ---"/>
						 <form:options items="${countryList}" />
						</form:select>
						</td>
						<td><form:errors path="country" cssClass="fielderror" /></td>
					</tr>
					<tr>
						<td>City:</td>
						<td><form:input path="city" /></td>
						<td><form:errors path="city" cssClass="fielderror"/></td>
					</tr>
					<tr>
						<td colspan="3"><input type="submit" value="Send" /></td>
					</tr>
					<tr>
		                <td align="right">
		                	<a href="login">Login</a>
		                </td>
		             </tr>
				</tbody>
			</table>
		</form:form>    		
	</body>
</html>