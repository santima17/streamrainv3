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
			#tabla2{	border: solid 1px #333;	width: 300px; }
			#tabla2 tbody tr{ background: #999; }
			.fila-base{ display: none; } /* fila base oculta */
			.eliminar{ cursor: pointer; color: #000; }
			/*input[type="text"]{ width: 100px; } /* ancho a los elementos input="text" */*/
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

			$(function() {
			    $('#selectType').change(function() {
			        var x = $(this).val();
			        if (x == 'NONE'){
			        	$('#dateStart').hide();
			        	$('#estimatedDuration').hide();
			        	$('#duration').hide();
			        	return;
			        }
			        if (x == '1'){
			        	$('#dateStart').hide();
			        	$('#estimatedDuration').hide();
			        	$('#duration').show();
			        	return;
			        }
		        	if (x == '2'){
		        		$('#dateStart').hide();
		        		$('#estimatedDuration').hide();
		        		$('#duration').show();
		        		return;
		        	}
		        	if (x == '3'){
		        		$('#duration').hide();
		        		$('#dateStart').show();
		        		$('#estimatedDuration').show();
		        		return;
		        	}
		        	if (x == '4'){
		        		$('#duration').hide();
		        		$('#dateStart').show();
		        		$('#estimatedDuration').show();
		        		return;
		        	}
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
		<form:form method="post" action="${content}" enctype="multipart/form-data" modelAttribute="contentDto">
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
						<td>Type: </td>
						<td>
						<form:select id="selectType" path="type" >
						 <form:option value="NONE" label="--- Select ---"/>
						 <form:options items="${typeList}" />
						</form:select>
						</td>
						<td><form:errors path="type" cssClass="fielderror" /></td>
					</tr>
					<tr id="duration" style="display:none">
						<td>Duration:</td>
						<td><form:input type="number" path="duration" /></td>
						<td><form:errors path="duration" cssClass="fielderror"/></td>
					</tr>
					<tr id="dateStart" style="display:none">
						<td>Date Start:</td>
						<td><form:input type="date" path="dateStart" /></td>
						<td><form:errors path="dateStart" cssClass="fielderror"/></td>
					</tr>
					<tr id="estimatedDuration" style="display:none">
						<td>Estimated Duration:</td>
						<td><form:input type="number" path="estimatedDuraction" /></td>
						<td><form:errors path="estimatedDuraction" cssClass="fielderror"/></td>
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
						<td>Directors:</td>
				        <td> 
					        <table id="tabla">
								<thead>
									<tr>
										<th>First Name</th>
										<th>Last Name</th>
									</tr>
								</thead>
		
								<tbody>
									<c:forEach items="${contentDto.directors}" varStatus="status">
										<tr >
											<td><form:input type="text" class="nombre" path="directors[${status.index}].firstName" value="" /></td>
											<td><form:input type="text" class="apellidos" path="directors[${status.index}].lastName" value="" /></td>
										</tr>
							 		</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>Actors:</td>
				        <td> 
					        <table id="tabla2">
								<thead>
									<tr>
										<th>First Name</th>
										<th>Last Name</th>
									</tr>
								</thead>
		
								<tbody>
									<c:forEach items="${contentDto.actors}" varStatus="status">
										<tr >
											<td><form:input type="text" class="nombre" path="actors[${status.index}].firstName" value="" /></td>
											<td><form:input type="text" class="apellidos" path="actors[${status.index}].lastName" value="" /></td>
										</tr>
							 		</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
			           <td>Pay Per View(PPV):</td>
			           <td><form:checkbox path="isPayPerView"/></td>
			        </tr>        
					<tr>
						<td colspan="3"><input type="submit" value="Create" /></td>
					</tr>
					
				</tbody>
			</table>
		</form:form>    		
	</body>
</html>