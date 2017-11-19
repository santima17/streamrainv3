<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Welcome</title>
</head>
<body>
	<a href="<c:url value="logout" />">Logout</a>
	<br/>
	<br/>
    <table>
        <tr>
            <td>Welcome ${firstname}</td>
        </tr>
        
        <tr>
            <td><a href="portal/createContent">Create Content</a>
            </td>
        </tr>
    </table>
</body>
</html>