<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="es.tiwadmin.model.Product" %>
<%@ page import="es.tiwadmin.model.Category" %>
<%@ page import="java.util.List" %>

<% Product item = (Product) request.getAttribute("item"); %>

<html>
    <head>
        <title>Edit Product</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
		<link rel="stylesheet" href="style/common.css">
		<link rel="stylesheet" href="style/entityDetail.css">
    </head>
    <body>
        <div id="page">
            <%@ include file="../jspf/header.jspf" %>
            <div id="content">
                <div id="lyt-left">
					<div>
						<h2>Actions</h2>
						<button id="confirm">Confirm</button>
						<button id="cancel">Cancel</button>
					</div>
				</div>
				<div id="lyt-right">
					<div id="userHeader">
						<div id="userProfileData">
							<img src="<%= item == null ? "images/box.png" : item.getImagePath() %>">
							<div>
								<h1><%= item == null ? "New Product" : item.getName() %></h1>
								<h2><%= item == null ? "" : item.getShortDescription() %></h2>
							</div>
						</div>
						<div id="stats">
							<div>
								<span>Price</span>
								<span><%= item == null ? "" : item.getShipPrice() + " €"%></span>
							</div>
							<div>
								<span>Stock</span>
								<span><%= item == null ? "" : item.getStock() %></span>
							</div>
							<!-- div>
								<span>Sales</span>
								<span><%-- =item == null ? "" : item.getOrdersHasProducts().size() --%>???</span>
							</div -->
						</div>
					</div>
					<hr>
					<!-- form action="productSave" method="POST" enctype="multipart/form-data" id="mainForm" -->
					<form action="productSave" method="POST" id="mainForm">
						<div id="mainUserData">
							<div>
								<div class="dataHeaderBar">
									<h4>Product Info</h4>
									<span class="hidden">&#9660;</span>
									<span>&#9650;</span>
								</div>
								<div class="dataContent">
									<table>
										<tr>
											<th><label for="name">Name</label></th>
											<td><input type="text" name="name" id="name" value="<%= item == null ? "" : item.getName() %>" /></td>
										</tr>
										<tr>
											<th><label for="short-description">Short Description</label></th>
											<td>
												<textarea name="short-description" id="short-description"><%= item == null ? "" : item.getShortDescription() %></textarea>
											</td>
										</tr>
										<tr>
											<th><label for="long-description">Long Description</label></th>
											<td>
												<textarea name="long-description" id="long-description"><%= item == null ? "" : item.getDescription() %></textarea>
											</td>
										</tr>
										<tr>
											<th><label for="category">Category</label></th>
											<td>
												<select name="category" id="category">
													<option value="placeholder">--Select--</option>
												<%
													@SuppressWarnings("unchecked")
													List<Category> cats = (List<Category>) request.getAttribute("categoryList");
													for(Category cat : cats) {
												%>
													<option <%= item == null ? "" : (item.getCategoryBean().getName().equals(cat.getName()) ? "selected=\"selected\"" : "") %>
													value="<%=cat.getId() %>"><%=cat.getName() %></option>
												<% } %>
												</select>
											</td>
										</tr>
										<tr>
											<th><label for="price">Price</label></th>
											<td><input type="number" name="price" id="price" value="<%= item == null ? "" : item.getShipPrice() %>"/>
												<span id="currency">€</span>
											</td>
										</tr>
										<tr>
											<th><label for="stock">Stock</label></th>
											<td><input type="number" name="stock" id="stock" value="<%= item == null ? "" : item.getStock() %>"></td>
										</tr>
										<tr>
											<th><label for="imagePath">Image</label></th>
											<!-- td><label for="image">Select...</label><input type="file" name="image" id="image"/></td -->
											<td><input type="text" name="imagePath" id="imagePath" value="<%=item == null ? "" : item.getImagePath() %>"></td>
										</tr>
									</table>
								</div>
							</div>
							<div>
								<div class="dataHeaderBar">
									<h4>Static Data</h4>
									<span class="hidden">&#9660;</span>
									<span>&#9650;</span>
								</div>
								<div class="dataContent">
									<input type="hidden" name="productId" value="<%=item == null ? "" : item.getId() %>">
									<table>
									<% if(item != null) {%>
										<tr>
											<th><label>ID</label></th>
											<td><%=item.getId() %></td>
										</tr>
									<% } %>
										<tr>
											<th><label for="<%= item == null ? "" : "seller" %>">Seller</label></th>
											<td><%=item == null ? "" : (item.getUserBean() == null ? "[DELETED]" : ((User) item.getUserBean()).getEmail()) %>
											<input type="<%=item == null ? "email" : "hidden" %>" name="seller" id="seller" 
											value="<%=item == null ? "" : (item.getUserBean() == null ? "[DELETED]" : ((User) item.getUserBean()).getEmail()) %>" ></td>
										</tr>
									<% if(item != null) {%>
										<tr>
											<th><label>Image Path</label></th>
											<td><%=item.getImagePath() %></td>
										</tr>
										<!-- tr>
											<th>Stock</th>
											<td><%=item.getStock() %></td>
										</tr -->
										<!-- tr>
											<th><label>Sells</label></th>
											<td><%-- =item == null ? "" : item.getOrdersHasProducts().size() --%></td>
										</tr -->
									<% } %>
									</table>
								</div>
							</div>
						</div>
						<input type="submit" value="Confirm" />
					</form>
				</div>
            </div>
            <%@ include file="../jspf/footer.jspf" %>
			<script src="js/entityDetail.js"></script>
		 </div>
    </body>
</html>