<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="es.tiwadmin.model.Product" %>
<%@ page import="es.tiwadmin.model.Category" %>

<!DOCTYPE html>
<% Product item = (Product) request.getAttribute("item"); %>
<html>
    <head>
        <title><%=item.getName() %></title>
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
						<form action="productEdit" method="POST">
							<button name="itemID" value="<%=item.getId() %>">Edit</button>
						</form>
						<form action="productRemove" method="POST">
							<button name="itemID" value="<%=item.getId() %>">Remove</button>
						</form>
					<% if(item.getUserBean() != null) { %>
						<form action="userDetail" method="POST">
							<button name="itemPK" value="<%=((User) item.getUserBean()).getEmail() %>">Show User</button>
						</form>
					<% } %>
					</div>
				</div>
				<div id="lyt-right">
					<div id="userHeader">
						<div id="userProfileData">
							<img src="<%=item.getImagePath() %>">
							<div>
								<h1><%=item.getName()%></h1>
								<h2><%=item.getShortDescription() %></h2>
							</div>
						</div>
						<div id="stats">
							<div>
								<span>Price</span>
								<span><%=item.getShipPrice()%> €</span>
							</div>
							<div>
								<span>Stock</span>
								<span><%=item.getStock() %></span>
							</div>
							<div>
								<span>Sales</span>
								<span><%=item.getOrdersHasProducts().size() %></span>
							</div>
						</div>
					</div>
					<hr>
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
										<th>Name</th>
										<td><%=item.getName() %></td>
									</tr>
									<tr>
										<th>Short Description</th>
										<td><%=item.getShortDescription() %></td>
									</tr>
									<tr>
										<th>Long Description</th>
										<td><%=item.getDescription() %></td>
									</tr>
									<tr>
										<th>Category</th>
										<td><%=((Category) item.getCategoryBean()).getName() %></td>
									</tr>
									<tr>
										<th>Price</th>
										<td><%=item.getShipPrice()%> €</td>
									</tr>
									<tr>
										<th>Seller</th>
										<td><%= item.getUserBean() == null ? "[DELETED]" : ((User) item.getUserBean()).getEmail() %></td>
									</tr>
								</table>
							</div>
						</div>
						<div>
							<div class="dataHeaderBar">
								<h4>Internal Data</h4>
								<span class="hidden">&#9660;</span>
								<span>&#9650;</span>
							</div>
							<div class="dataContent">
								<table>
									<tr>
										<th>ID</th>
										<td><%=item.getId() %></td>
									</tr>
									<tr>
										<th>Image Path</th>
										<td><%=item.getImagePath() %></td>
									</tr>
									<tr>
										<th>Stock</th>
										<td><%=item.getStock() %></td>
									</tr>
									<tr>
										<th>Sells</th>
										<td><%=item.getOrdersHasProducts().size() %></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
            </div>
            <%@ include file="../jspf/footer.jspf" %>
			<script src="js/entityDetail.js"></script>
		 </div>
    </body>
</html>
