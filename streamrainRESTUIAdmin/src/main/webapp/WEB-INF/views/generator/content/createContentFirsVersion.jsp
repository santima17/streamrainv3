<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 
<html xmlns="http://www.w3.org/1999/xhtml">
 
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
		<title>Create Content</title>
		<style type="text/css">
			body {
				font-family: verdana, sans-serif;			
			}
			
			span.fielderror {
				color: red;
			}
			
			#tabla{	border: solid 1px #333;	width: 300px; }
			#tabla tbody tr{ background: #999; }
			.fila-base{ display: none; } /* fila base oculta */
			.eliminar{ cursor: pointer; color: #000; }
			input[type="text"]{ width: 80px; } /* ancho a los elementos input="text" */
		</style>	
						
		<script type="text/javascript" 
			src="https://code.jquery.com/jquery-1.11.3.js">		 
		</script>
		<script type="text/javascript">
 
			$(function(){
				// Clona la fila oculta que tiene los campos base, y la agrega al final de la tabla
				$("#agregar").on('click', function(){
					$("#tabla tbody tr:eq(0)").clone().removeClass('fila-base').appendTo("#tabla tbody");
				});
			 
				// Evento que selecciona la fila y la elimina 
				$(document).on("click",".eliminar",function(){
					var parent = $(this).parents().get(0);
					$(parent).remove();
				});
			});
		 
		</script>
	</head>
	 
	<body>		
		<h1>Create Content</h1>
		<c:if test="${not empty message}">
			<td style="font-style: italic; color: green;">${message}</td>
		</c:if>
		<c:if test="${not empty error}">
			<td style="font-style: italic; color: red;">${error}</td>
		</c:if>
		
		<c:url value="createContentProcess" var="content"/>			
		<form:form method="post" action="${content}" enctype="multipart/form-data" commandName="contentDto">
			<table >
				<tbody>
					<tr>
						<td>Name:</td>
						<td><form:input path="name" /></td>
						<td><form:errors path="name" cssClass="fielderror"/></td>
					</tr>
					<tr>
						<td>Description:</td>
						<td><form:textarea path="description" /></td>
						<td><form:errors path="description" cssClass="fielderror"/></td>
					</tr>
					<tr>
						<td>Type:</td>
						<td><form:input path="type" /></td>
						<td><form:errors path="type" cssClass="fielderror"/></td>
					</tr>
					<tr>
			           <td>Picture:</td>
			           <td><form:input type="file" path="picture"/></td>
			        </tr>
			        <tr>
			           <td>Video:</td>
			           <td><form:input type="file" path="video"/></td>
			        </tr>
			        <tr>
						<td>Actors:</td>
					</tr>
			        <table id="tabla">
					<!-- Cabecera de la tabla -->
						<thead>
							<tr>
								<th>First Name</th>
								<th>Last Name</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
					 
						<!-- Cuerpo de la tabla con los campos -->
						<tbody>
					 
							<!-- fila base para clonar y agregar al final -->
							<tr class="fila-base">
								<td><input type="text" class="nombre" /></td>
								<td><input type="text" class="apellidos" /></td>
								<td class="eliminar">Remove</td>
							</tr>
							<!-- fin de código: fila base -->
					 
							<!-- Fila de ejemplo -->
							<tr>
								<td><input type="text" class="nombre" /></td>
								<td><input type="text" class="apellidos" /></td>
								<td class="eliminar">Eliminar</td>
							</tr>
							<!-- fin de código: fila de ejemplo -->
					 
						</tbody>
					</table>
					<!-- Botón para agregar filas -->
					<input type="button" id="agregar" value="Add" />
			        
					<tr>
						<td colspan="3"><input type="submit" value="Create" /></td>
					</tr>
					
				</tbody>
			</table>
		</form:form>    		
	</body>
</html>