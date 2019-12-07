<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%  User item = (User) request.getAttribute("item"); %>
<%!
	private String transformDate(String date) {
		String[] tokens = date.split("/");
		String out = "";
		
		for(int i = tokens.length - 1; i >= 0; i--)
			out += tokens[i] + (i > 0 ? "-" : "");
	
		return out;
	}
%>
<html>
    <head>
        <title>Edit User</title>
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
							<img src="images/user.png">
							<div>
								<h1><%=item == null ? "New User" : item.getName()%> <%=item == null ? "" : item.getSurnames() %></h1>
								<h2><%=item == null ? " " : item.getEmail() %></h2>
							</div>
						</div>
						<div id="stats">
							<div>
								<span>Products</span>
								<span><%=item == null ? "" : item.getProducts().size() %></span>
							</div>
							<div>
								<span>Sales</span>
								<span><%=item == null ? "" : item.getOrders().size() %></span>
							</div>
						</div>
					</div>
					<hr>
					<form action="userSave" method="POST" id="mainForm">
						<input type="hidden" name="action" value="<%=item == null ? "create" : "edit" %>">
						<div id="mainUserData">
							<div>
								<div class="dataHeaderBar">
									<h4>Personal Data</h4>
									<span class="hidden">&#9660;</span>
									<span>&#9660;</span>
								</div>
								<div class="dataContent">
									<table>
										<tr>
											<th><label for="name">Name</label></th>
											<td><input type="text" name="name" id="name" value="<%=item == null ? "" : item.getName() %>" /></td>
										</tr>
										<tr>
											<th><label for="surnames">Surname(s)</label></th>
											<td><input type="text" name="surnames" id="surnames" value="<%=item == null ? "" : item.getSurnames() %>" /></td>
										</tr>
										<tr>
											<th><label for="phone-number">Phone</label></th>
											<td><input type="tel" name="phone-number" id="phone-number" value="<%=item == null ? "" : item.getPhone() %>" /></td>
										</tr>
										<tr>
											<th><label for="postal-code">Postal Code</label></th>
											<td><input type="number" name="postal-code" id="postal-code" value="<%=item == null ? "" : item.getPostalCode() %>" /></td>
										</tr>
										<tr>
											<th><label for="address">Address</label></th>
											<td><input type="text" name="address" id="address" value="<%=item == null ? "" : item.getAddress() %>" /></td>
										</tr>
										<tr>
											<th><label for="city">City</label></th>
											<td><input type="text" name="city" id="city" value="<%=item == null ? "" : item.getCity() %>" /></td>
										</tr>
										<tr>
											<th><label for="country">Country</label></th>
											<td><input type="text" name="country" id="country" value="<%=item == null ? "" : item.getCountry() %>" /></td>
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
											<th><label for="card-number">Card Number</label></th>
											<td><input type="number" name="card-number" id="card-number" value="<%=item == null ? "" : item.getCreditCard() %>" /></td>
										</tr>
										<tr>
											<th><label for="cvv">Card CVV</label></th>
											<td><input type="number" name="cvv" id="cvv" value="<%=item == null ? "" : item.getCredit_card_CVV() %>" /></td>
										</tr>
										<tr>
											<th><label for="card-expires">Card Expiration</label></th>
											<td><input type="date" name="card-expires" id="card-expires" value="<%=item == null ? "" : transformDate(item.getCreditCardExpiration()) %>" /></td>
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
											<th><label for="role">Profile</label></th>
											<td>
												<select name="role" id="role">
													<option <%=item == null ? "" : (item.getType() == 0 ? "selected=\"selected\"" : "") %>value="seller">Seller</option>
													<option <%=item == null ? "" : (item.getType() == 1 ? "selected=\"selected\"" : "") %>value="buyer">Buyer</option>
													<option <%=item == null ? "" : (item.getType() == 2 ? "selected=\"selected\"" : "") %>value="admin">Admin</option>
												</select>
											</td>
										</tr>
										<tr>
											<th><label for="email">Email</label></th>
											<td><input type="email" name="email" id="email" value="<%=item == null ? "" : item.getEmail() %>" /></td>
										</tr>
										<tr>
											<th><label for="password">New Password</label></th>
											<td><input type="password" name="password" id="password" /></td>
										</tr>
										<tr>
											<th><label for="password-check">Confirm Password</label></th>
											<td><input type="password" name="password-check" id="password-check" /></td>
										</tr>
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
