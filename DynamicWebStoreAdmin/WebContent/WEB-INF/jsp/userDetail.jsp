<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<% User item = (User) request.getAttribute("item"); %>
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
<html>
    <head>
        <title><%=item.getName()%></title>
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
						<form action="userEdit" method="POST">
							<button name="itemPK" value="<%=item.getEmail() %>">Edit</button>
						</form>
						<form action="userRemove" method="POST">
							<button name="itemPK" value="<%=item.getEmail() %>">Remove</button>
						</form>
						<form action="productList" method="POST">
							<button name="itemPK" value="<%=item.getEmail() %>">Show Products</button>
						</form>
					</div>
				</div>
				<div id="lyt-right">
					<div id="userHeader">
						<div id="userProfileData">
							<img src="images/user.png">
							<div>
								<h1><%=item.getName() %> <%=item.getSurnames() %></h1>
								<h2><%=item.getEmail() %></h2>
							</div>
						</div>
						<div id="stats">
							<div>
								<span>Products</span>
								<span><%=item.getProducts().size() %></span>
							</div>
							<div>
								<span>Sales</span>
								<span><%=item.getOrders().size() %></span>
							</div>
						</div>
					</div>
					<hr>
					<div id="mainUserData">
						<div>
							<div class="dataHeaderBar">
								<h4>Personal Data</h4>
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
										<th>Surname(s)</th>
										<td><%=item.getSurnames() %></td>
									</tr>
									<tr>
										<th>Phone</th>
										<td><%=item.getPhone() %></td>
									</tr>
									<tr>
										<th>Postal Code</th>
										<td><%=item.getPostalCode() %></td>
									</tr>
									<tr>
										<th>Address</th>
										<td><%=item.getAddress() %></td>
									</tr>
									<tr>
										<th>City</th>
										<td><%=item.getCity() %></td>
									</tr>
									<tr>
										<th>Country</th>
										<td><%=item.getCountry() %></td>
									</tr>
								</table>
							</div>
						</div>
						<div>
							<div class="dataHeaderBar">
								<h4>Facturation Data</h4>
								<span class="hidden">&#9660;</span>
								<span>&#9650;</span>
							</div>
							<div class="dataContent">
								<table>
									<tr>
										<th>Card Number</th>
										<td><%=item.getCreditCard() %></td>
									</tr>
									<tr>
										<th>Card CVV</th>
										<td><%=item.getCredit_card_CVV() %></td>
									</tr>
									<tr>
										<th>Card Expiration</th>
										<td><%=item.getCreditCardExpiration() %></td>
									</tr>
								</table>
							</div>
						</div>
						<div>
							<div class="dataHeaderBar">
								<h4>Account Data</h4>
								<span class="hidden">&#9660;</span>
								<span>&#9650;</span>
							</div>
							<div class="dataContent">
								<table>
									<tr>
										<th>Profile</th>
										<td><%=translateSeller(item.getType()) %></td>
									</tr>
									<tr>
										<th>Email</th>
										<td><%=item.getEmail() %></td>
									</tr>
									<tr>
										<th>Password</th>
										<td>************</td>
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
