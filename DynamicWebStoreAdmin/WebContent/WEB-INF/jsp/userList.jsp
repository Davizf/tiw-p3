<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List" %>
<%@ page import="es.tiwadmin.model.User" %>
<%@ page import="es.tiwadmin.model.Product" %>
<%@ page import="es.tiwadmin.model.Category" %>

<html>
    <head>
        <title>User List</title>
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
						<a href="userAdd">Add</a>
						<button id="mass-remove-users" class="mass-operation">Remove</button>
						<button id="mass-send-message" class="mass-operation">Send Message</button>
					</div>
				</div>
		
				<div id="lyt-right">
					<h1>User List</h1>
					<table>
						<thead>
							<tr>
								<th><input type="checkbox"></th>
								<th>Email</th> 
								<th>Name</th> 
								<th>Surname(s)</th> 
								<th>Phone</th> 
								<th>Role</th> 
								<th>Products</th> 
								<th>Send</th> 
								<th>See</th> 
								<th>Edit</th>
							</tr>
						</thead>
						<tbody>
						<%!
							private String translateSeller(int sellerVal) {
								switch(sellerVal) {
									case 0:
										return "Buyer";
									case 1:
										return "Seller";
									case 2:
										return "Admin";
								}
								return "";
							}
						%>
						
						<%
							@SuppressWarnings("unchecked")
							List<User> items = (List<User>) request.getAttribute("items");
							for(User item : items) {
						%>
							<tr>
								<td><input type="checkbox"></td>
								<td><%= item.getEmail() %></td>
								<td><%= item.getName() %></td>
								<td><%= item.getSurnames() %></td>
								<td><%= item.getPhone() %></td>
								<td><%= translateSeller(item.getType()) %></td>
								<td><%= item.getProducts() == null ? 0 : item.getProducts().size() %></td>

								<input type="hidden" name="itemPK" value="<%= item.getEmail()%>">
								<td>
									<form action="messageWrite" method="POST">
										<button name="itemPK" value="<%=item.getEmail() %>"><img src="images/mail.png"></button>
									</form>
								</td>
								<td>
									<form action="userDetail" method="POST">
										<button name="itemPK" value="<%=item.getEmail() %>"><img src="images/see.png"></button>
									</form>
								</td>
								<td>
									<form action="userEdit" method="POST">
										<button name="itemPK" value="<%=item.getEmail() %>"><img src="images/edit.png"></button>
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
