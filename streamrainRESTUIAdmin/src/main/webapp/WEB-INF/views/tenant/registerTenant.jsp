<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


		
		
		
		
		

 <div align="center">
	<form:form method="post" modelAttribute="tenantForm" action="registerTenant">
            <table border="0">
                <tr>
                    <td colspan="2" align="center"><h2>Tenat Registration</h2></td>
                </tr>
                <tr>
                    <td>ID:</td>
                    <td><form:input path="id" type="text" /></td>
                </tr>
                <tr>
                    <td>Nombre:</td>
                    <td><form:input path="name" type="text" /></td>
                </tr>
                <tr>
                    <td>Host:</td>
                    <td><form:input path="ip" type="text" /></td>
                </tr>
                <tr>
                    <td>Port:</td>
                    <td><form:input path="port" type="text" /></td>
                </tr>
                <tr>
                    <td>User:</td>
                    <td><form:input path="user" type="text" /> </td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><form:input path="password" type="text" /></td>
                </tr>
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Save" /></td>
                </tr>
            </table>
        </form:form>
    </div>
</body>
</html>