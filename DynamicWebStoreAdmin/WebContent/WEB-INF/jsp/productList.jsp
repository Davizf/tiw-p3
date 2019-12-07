<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List" %>
<%@ page import="es.tiwadmin.model.User" %>
<%@ page import="es.tiwadmin.model.Product" %>
<%@ page import="es.tiwadmin.model.Category" %>

<% 	
	@SuppressWarnings("unchecked")
	List<Product> items = (List<Product>) request.getAttribute("items");
%>

<html>
    <head>
        <title>Produt List</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
		<link rel="stylesheet" href="style/common.css">
		<link rel="stylesheet" href="style/entityList.css">
    </head>
    <body>
        <div id="page">
            <%@ include file="../jspf/header.jspf" %>
            <div id="content">
                <div id="lyt-left">
					<div>
						<h2>Actions</h2>
						<a href="productAdd">Add</a>
						<button id="mass-remove-products" class="mass-operation">Remove</button>
					</div>
				</div>
		
				<div id="lyt-right">
					<h1>Product List</h1>
					<table>
						<thead>
							<tr>
								<th><input type="checkbox"></th>
								<th>ID</th> 
								<th>Name</th> 
								<th>Description</th> 
								<th>Seller</th> 
								<th>Price</th> 
								<th>Stock</th> 
								<th>Category</th> 
								<th>See</th> 
								<th>Edit</th>
							</tr>
						</thead>
						<tbody>
						<% for(Product item : items) { %>
							<tr>
								<td><input type="checkbox"></td>
								<td><%= item.getId() %></td>
								<td><%= item.getName() %></td>
								<td><%= item.getShortDescription() %></td>
								<td><%= item.getUserBean() == null ? "[DELETED]" : ((User) item.getUserBean()).getEmail() %></td>
								<td><%= item.getShipPrice() %></td>
								<td><%= item.getStock() %></td>
								<td><%= ((Category) item.getCategoryBean()).getName() %></td>
								<td>
									<form action="productDetail" method="POST">
										<button name="itemID" value="<%=item.getId() %>"><img src="images/see.png"></button>
									</form>
								</td>
								<td>
									<form action="productEdit" method="POST">
										<button name="itemID" value="<%=item.getId() %>"><img src="images/edit.png"></button>
									</form>
								</td>
							</tr>
						<% } %>
						</tbody>
					</table>
				</div>
            </div>
       		<%@ include file="../jspf/footer.jspf" %>
			<form action="massOperation" method="POST" name="mass-operation">
				<input type="text" name="operation" value="">
				<input type="text" name="keys" value="">
			</form>
			<script src="js/entityList.js"></script>
		 </div>
    </body>
</html>
