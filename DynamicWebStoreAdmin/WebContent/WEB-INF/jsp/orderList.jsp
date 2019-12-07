<%@page import="es.tiwadmin.model.OrdersHasProduct"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="es.tiwadmin.model.Order" %>

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
						<button id="mass-send-message" class="mass-operation">Send Message</button>
					</div>
				</div>
		
				<div id="lyt-right">
					<h1>Order List</h1>
					<table>
						<thead>
							<tr>
								<th><input type="checkbox"></th>
								<th>ID</th> 
								<th>Buyer</th>
								<th>Date</th> 
								<th>Product Count</th>
								<th>Product ID</th>
								<th>Product Name</th>
								<th>Product Price</th>
								<th>Total</th>
							</tr>
						</thead>
						<tbody>
						<%
							@SuppressWarnings("unchecked")
							List<Order> items = (List<Order>) request.getAttribute("items");
							for(Order item : items) {
								List<OrdersHasProduct> ohps = item.getOrdersHasProducts();
						%>
							<tr>
								<td><input type="checkbox"></td>
								<td><%= item.getId() %></td>
								<td><%= item.getUserBean() == null ? "[DELETED]" : ((User) item.getUserBean()).getEmail() %></td>
								<td><%= item.getDate() %></td>
								<td><%= item.getOrdersHasProducts().size() %></td>
								<td>
									<% for(OrdersHasProduct ohp : ohps) { %>
										<%=ohp.getProductBean().getId() %>
										<br>
									<% } %>
								</td>
								<td>
									<% for(OrdersHasProduct ohp : ohps) { %>
										<%=ohp.getProductBean().getName() %>
										<br>
									<% } %>
								</td>
								<td>
									<% for(OrdersHasProduct ohp : ohps) { %>
										<%=ohp.getProductPrice() %> €
										<br>
									<% } %>
								</td>
								<td>
									<% 
										BigDecimal price = new BigDecimal(0);
										for(OrdersHasProduct ohp : ohps)
											price = price.add(ohp.getProductPrice());
									%>
									<%=price%> €
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
