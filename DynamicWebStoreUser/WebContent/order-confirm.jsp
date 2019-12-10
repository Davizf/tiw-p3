<%@page import="controllers.CategoryController"%>
<%@page import="controllers.UserController"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="model.Category"%>
<%@page import="model.User"%>
<%@page import="model.Message"%>
<%@page import="model.ProductInCart" %>
<%@page import="controllers.ShoppingCart" %>
<%@page import="model.CategoryLevel"%>
<%@page import="model.HierarchicalCategories"%>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

	<title>Order confirm</title>

	<!-- Google font -->
	<link href="https://fonts.googleapis.com/css?family=Hind:400,700" rel="stylesheet">

	<!-- Bootstrap -->
	<link type="text/css" rel="stylesheet" href="/tiw-p1/theme/bootstrap.min.css" />

	<!-- Slick -->
	<link type="text/css" rel="stylesheet" href="/tiw-p1/theme/slick.css" />
	<link type="text/css" rel="stylesheet" href="/tiw-p1/theme/slick-theme.css" />

	<!-- nouislider -->
	<link type="text/css" rel="stylesheet" href="/tiw-p1/theme/nouislider.min.css" />

	<!-- Font Awesome Icon -->
	<link rel="stylesheet" href="/tiw-p1/theme/font-awesome.min.css">

	<!-- Custom stlylesheet -->
	<link type="text/css" rel="stylesheet" href="/tiw-p1/theme/style.css" />

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->

</head>

<body>
<%
String user=(String)session.getAttribute("user");
ArrayList<Category> categories=CategoryController.getCategories();
HierarchicalCategories hc=null;
if (categories!=null) {
	hc=new HierarchicalCategories(categories, "&nbsp;&nbsp;&nbsp;>", "&nbsp;&nbsp;&nbsp;&nbsp;");
	categories=hc.getCategoriesOrdered();
}
User userBean=null;
if (user!=null) {
	userBean=UserController.getUser(user);
}
ArrayList<ProductInCart> list = (ArrayList<ProductInCart>)request.getAttribute("cartList");
%>

	<!-- HEADER -->
	<header>
		<!-- top Header -->
		<div id="top-header">
			<div class="container">
				<div class="pull-left">
					<span>Welcome to E-shop!</span>
				</div>
				<div class="pull-right">
					<ul class="header-top-links">
						<li><a href="#">Store</a></li>
						<li><a href="#">Newsletter</a></li>
						<li><a href="#">FAQ</a></li>
						<li class="dropdown default-dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">ENG <i class="fa fa-caret-down"></i></a>
							<ul class="custom-menu">
								<li><a href="#">English (ENG)</a></li>
								<li><a href="#">Russian (Ru)</a></li>
								<li><a href="#">French (FR)</a></li>
								<li><a href="#">Spanish (Es)</a></li>
							</ul>
						</li>
						<li class="dropdown default-dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">USD <i class="fa fa-caret-down"></i></a>
							<ul class="custom-menu">
								<li><a href="#">USD ($)</a></li>
								<li><a href="#">EUR (€)</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- /top Header -->

		<!-- header -->
		<div id="header">
			<div class="container">
				<div class="pull-left">
					<!-- Logo -->
					<div class="header-logo">
						<a class="logo" href="index.jsp">
							<img src="/tiw-p1/images/logo.png" alt="">
						</a>
					</div>
					<!-- /Logo -->

					<!-- Search -->
					<div class="header-search">
						<form action="ProductServlet" method="post">							
							<input  type="hidden" value="search" name="op">
							<input  type="text" placeholder="Search the product" name="query">
							<button type="submit"><i class="fa fa-search"></i></button>
						</form>
					</div>
					<!-- /Search -->
				</div>
				<div class="pull-right">
					<ul class="header-btns">
						<!-- Account -->
						<li class="header-account dropdown default-dropdown">
							<div class="dropdown-toggle" role="button" data-toggle="dropdown" aria-expanded="true">
								<div class="header-btns-icon">
									<i class="fa fa-user-o"></i>
								</div>
								<%if(user != null) { %>
									<strong class="text-uppercase">Hi, <%=((String)session.getAttribute("username")) %>! <i class="fa fa-caret-down"></i></strong>
								<%}else{ %>
									<strong class="text-uppercase">My Account <i class="fa fa-caret-down"></i></strong>
								<%}%>
							</div>
							
							<%if(user != null) { %>
								<a class="text-camelcase" href="profile.jsp">My profile</a>
							<%} else{ %>
								<a href="login-page.jsp" class="text-uppercase">Login</a> / <a href="register-page.jsp" class="text-uppercase">Join</a>
							<%} %>
							
							<ul class="custom-menu">
								<%if(user != null) { %>
									<%if (userBean != null && userBean.getType() != UserController.USER_TYPE_SELLER){ %>
										<li><a href="Order?type=my-orders"><i class="fa fa-check"></i> My orders</a></li>
										<li><a href="wish-list.jsp"><i class="fa fa-heart-o"></i> My wish list</a></li>
									<%} %>
									<li><a href="/tiw-p1/MessageServlet?op=2&correlationId=<%=user%>"><i class="fa fa-comment-o"></i> My messages</a></li>
									<%if (userBean != null && userBean.getType() == UserController.USER_TYPE_SELLER){ %>
										<li><a href="catalogue.jsp"><i class="fa fa-user-o"></i> My Catalogue</a></li>
										<li><a href="seller-send-message.jsp"><i class="fa fa-comment-o"></i> Send a Offer</a></li>
									<%} %>
									<li><a href="UserServlet?operation=log_out"><i class="fa fa-user"></i> Log out</a></li>
									<li><a href="delete-account.jsp"><i class="fa fa-user-times"></i> Delete my account</a></li>
								<%}else{ %>
									<li><a href="register-page.jsp"><i class="fa fa-user-plus"></i> Create an account</a></li>
									<li><a href="login-page.jsp"><i class="fa fa-unlock-alt"></i> Login</a></li>
								<%}%>
								
							</ul>
						</li>
						<!-- /Account -->
						<%if(user != null) { %>
							<!-- Cart -->
							<%
							ArrayList<ProductInCart> productsInCart = (ArrayList<ProductInCart>)session.getAttribute("cartList");
							double cartTotal=0;
							int cartNumber=0;
							if (productsInCart!=null) {
								cartNumber=productsInCart.size();
								for (int i=0; i<productsInCart.size(); i++)
									cartTotal+=productsInCart.get(i).getCost();
							}
							%>
							<li class="header-cart dropdown default-dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
									<div class="header-btns-icon">
										<i class="fa fa-shopping-cart"></i>
										<span class="qty"><%=cartNumber %></span>
									</div>
									<strong class="text-uppercase">My Cart:</strong>
									<br>
									<span>$<%=cartTotal %></span>
								</a>
								<div class="custom-menu">
									<div id="shopping-cart">
										<div class="shopping-cart-list">
											<%
											if (productsInCart!=null) {
												for (int i=0; i<productsInCart.size(); i++) {
											%>
											<div class="product product-widget">
												<div class="product-thumb">
													<img src="<%=productsInCart.get(i).getProduct().getImagePath() %>" alt="">
												</div>
												<div class="product-body">
													<h3 class="product-price">$<%=productsInCart.get(i).getProduct().getSalePrice().doubleValue() %> <span class="qty">x<%=productsInCart.get(i).getQuantity() %></span></h3>
													<h2 class="product-name"><a href="/tiw-p1/product-page.jsp?id=<%=productsInCart.get(i).getProduct().getId() %>"><%=productsInCart.get(i).getProduct().getName() %></a></h2>
												</div>
											</div>
											<%} 
												} %>
										</div>
										
										<form action="ShoppingCart" method="get">
											<div class="shopping-cart-btns">
												<input type="submit" class="primary-btn add-to-cart" value="View Cart" />
											</div>
										</form>
										
									</div>
								</div>
							</li>
						<%} %>
						<!-- /Cart -->
						<!-- Mobile nav toggle-->
						<li class="nav-toggle">
							<button class="nav-toggle-btn main-btn icon-btn"><i class="fa fa-bars"></i></button>
						</li>
						<!-- / Mobile nav toggle -->
					</ul>
				</div>
			</div>
			<!-- header -->
		</div>
		<!-- container -->
	</header>
	<!-- /HEADER -->

	<!-- NAVIGATION -->
	<div id="navigation">
		<!-- container -->
		<div class="container">
			<div id="responsive-nav">
				<!-- category nav -->
				<div class="category-nav show-on-click">
					<span class="category-header">Categories <i class="fa fa-list"></i></span>
					<form action="ProductServlet" method="post" id="form_category" hidden>
						<input type="hidden" name="op" value="category">
						<input type="hidden" name="category" value="" id="form_category_input">
					</form>
					<ul class="category-list">
						<%if(categories != null) {%>
							<%for(CategoryLevel category : hc.getCategories()) {
								if (category.getDepth()==0) {
									if (category.getChilds().size() == 0) {%>
										<li><a href="#" onclick="document.getElementById('form_category_input').value='<%=HierarchicalCategories.getIdChildsStr(category) %>';document.getElementById('form_category').submit();"><%=category.getName() %></a></li>
									<%} else {%>
										<li class="dropdown side-dropdown">
											<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><%=category.getName() %><i class="fa fa-angle-right"></i></a>
											<div class="custom-menu">
												<div class="row">
													<div class="col-md-6">
														<ul class="list-links">
															<li>
																<h3 class="list-links"><a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"
																onclick="document.getElementById('form_category_input').value='<%=HierarchicalCategories.getIdChildsStr(category) %>';document.getElementById('form_category').submit();"><%=category.getName() %></a></h3>
															</li><br>
															<%for (CategoryLevel categorySon : category.getChilds()) {%>
																<li><h3 class="list-links-title">
																	<a href="#" onclick="document.getElementById('form_category_input').value='<%=HierarchicalCategories.getIdChildsStr(categorySon) %>';document.getElementById('form_category').submit();"
																	class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><%=categorySon.getName() %></a></h3>
																</li>
																	<%for (CategoryLevel categoryGrandChild : categorySon.getChilds()) {%>
																		<li><a href="#" onclick="document.getElementById('form_category_input').value='<%=HierarchicalCategories.getIdChildsStr(categoryGrandChild) %>';document.getElementById('form_category').submit();"
																			class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><%=categoryGrandChild.getName() %></a>
																		</li>
																	<%} %>
																<hr>
															<%} %>
														</ul>
													</div>
												</div>
											</div>
										</li>
									<%}
								}
							}%>
						<%} %>
						<li><a href="ProductServlet?op=view">View all</a></li>
					</ul>
				</div>
				<!-- /category nav -->
			</div>
		</div>
		<!-- /container -->
	</div>
	<!-- /NAVIGATION -->

	<!-- BREADCRUMB -->
	<div id="breadcrumb">
		<div class="container">
			<ul class="breadcrumb">
				<li><a href="index.jsp">Home</a></li>
				<li><a href="#">Checkout</a></li>
				<li class="active">Order confirmation</li>
			</ul>
		</div>
	</div>
	<!-- /BREADCRUMB -->
	
	
	<!-- section -->
	<div class="section">
		<!-- container -->
		<div class="container">
		
		<form action="Order" method="post" class="clearfix">
			<!-- row -->
			<div class="row">
			
				<div class="col-md-6">
				
				<% if(user != null) {
					User user_info = UserController.getUser(user); 
				%>	
					<div class="section-title">
						<h4 class="title">Order confirmation</h4>
					</div>
					
					
					<br>
					<h4>1. Shipping address:</h4>
					<hr>
					
					<p>First name:</p>	
						<div class="form-group">
							<input class="input" type="text" name="firstName" value="<%=user_info.getName()%>" required>
						</div>
					<p>Last name:</p>	
						<div class="form-group">
							<input class="input" type="text" name="lastName" value="<%=user_info.getSurnames()%>" required>
						</div>
					<p>Email </p>	
						<div class="form-group">
							<input class="input" type="email" name="email" value="<%=user_info.getEmail()%>" required readonly>
						</div>
					<p>Address: </p>	
						<div class="form-group">
							<input class="input" type="text" name="address" value="<%=user_info.getAddress()%>" required>
						</div>
					<p>City: </p>	
						<div class="form-group">
							<input class="input" type="text" name="city" value="<%=user_info.getCity()%>" required>
						</div>
					<p>Country: </p>	
						<div class="form-group">
							<input class="input" type="text" name="country" value="<%=user_info.getCountry()%>" required>
						</div>
					<p>Postal code:</p>	
						<div class="form-group">
							<input class="input" type="number" name="zipCode" value="<%=user_info.getPostalCode()%>" required>
						</div>
					<p>Phone: </p>	
						<div class="form-group">
							<input class="input" type="tel" name="tel" value="<%=user_info.getPhone()%>" required>
						</div>
					
					
					<br>
					
					<h4>2. Payment method:</h4>
					
					<hr>
					
					<p>Credit card: </p>
						<div class="form-group">
							<input class="input" type="number" name="card" value="<%=user_info.getCreditCard()%>" required>
						</div>
					<p>Expiration date: </p>	
						<div class="form-group">
							<input class="input" type="text" name="cardExpire" value="<%=user_info.getCreditCardExpiration()%>" required>
						</div>
					<p>CVV: </p>	
						<div class="form-group">
							<input class="input" type="text" name="cvv" value="<%=user_info.getCredit_card_CVV()%>" maxlength="3" pattern="\d{3}" required>
						</div>	
					
					<br>
					
					
				</div>
				
				<% } %>
				
			</div>
			<!-- /row -->
			
			
			<h4>3. Check the products and shipping:</h4>
			<hr>
				
					
				<div class="col-md-12">
					<div class="order-summary clearfix">
						
						<table class="shopping-cart-table table">
							<thead>
								<tr>
									<th>Product</th>
									<th></th>
									<th class="text-center">Price</th>
									<th class="text-center">Quantity</th>
									<th class="text-center">Total</th>
									<th class="text-right"></th>
								</tr>
							</thead>
							<tbody>
								
								
							<%
							double total=0;
							double shippingCost=0;
							for(ProductInCart product : list ){
								total+=product.getCost();
								shippingCost += product.getProduct().getShipPrice().doubleValue();
							%>
								<tr>
									<td class="thumb"><img src= "<%= product.getProduct().getImagePath() %>" alt=""></td>
									<td class="details">
										<a href="product-page.jsp?id=<%=product.getProduct().getId() %>"><%= product.getProduct().getName() %></a>
										<td class="price text-center"><del class="font-weak"><small>$<%=product.getProduct().getPrice().doubleValue() %></small></del><br><strong>$<%=product.getProduct().getSalePrice().doubleValue() %></strong></td>
										<td class="qty text-center"><strong><%=product.getQuantity() %></strong></td>
										<td class="total text-center"><strong class="primary-color">$<%=product.getCost() %></strong></td>
				
								</tr>
								
							<% } %>	
								
									
									
							</tbody>
							<tfoot>
							<tr>
									<th class="empty" colspan="3"></th>
									<th>SUBTOTAL</th>
									<th colspan="2" class="sub-total">$<%=total%></th>
								</tr>
								<tr>
									<th class="empty" colspan="3"></th>
									<th>SHIPING</th>
									<td colspan="2">$<%=shippingCost%></td>
								</tr>
								<tr>
									<th class="empty" colspan="3"></th>
									<th>TOTAL</th>
									<th colspan="2" class="total">$<%=total+shippingCost%></th>
								</tr>
							</tfoot>
						</table>
						
					</div>
					
				</div>	
				<hr>
					
				<%
				User user_info = UserController.getUser(user); 
				%>
				<div class="pull-right">
					<input type="hidden" name="total-price" value= <%=total+shippingCost%> >
					<input type="hidden" name="products" value= <%=list%> >
					<input type="hidden" name="expiry" value="<%=user_info.getCreditCardExpiration()%>">
					<input type="hidden" name="card" value="<%=user_info.getCreditCard()%>">
					<input type="hidden" name="cvv" value="<%=user_info.getCredit_card_CVV()%>">
					<input type="hidden" name="type" value= "confirm-checkout" >
					<input type="submit" name="button" class="btn btn-success" value="Confirm my order" />
				</div>
		</form>			
		
		</div>
		<!-- /container -->
	</div>
	<!-- /section -->

<!-- FOOTER -->
	<footer id="footer" class="section section-grey">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<!-- footer widget -->
				<div class="col-md-6 col-sm-6 col-xs-6">
					<div class="footer">
						<!-- footer logo -->
						<div class="footer-logo">
							<a class="logo" href="#">
		            <img src="/tiw-p1/images/logo.png" alt="">
		          </a>
						</div>
						<!-- /footer logo -->

						<p>Our shop is a new shop which have so good quality products and prices that make it unique</p>

						<!-- footer social -->
						<ul class="footer-social">
							<li><a href="#"><i class="fa fa-facebook"></i></a></li>
							<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							<li><a href="#"><i class="fa fa-instagram"></i></a></li>
							<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
							<li><a href="#"><i class="fa fa-pinterest"></i></a></li>
						</ul>
						<!-- /footer social -->
					</div>
				</div>
				<!-- /footer widget -->

				<div class="clearfix visible-sm visible-xs"></div>

				<!-- footer widget -->
				<div class="col-md-3 col-sm-6 col-xs-6">
					<div class="footer">
						<h3 class="footer-header">Customer Service</h3>
						<ul class="list-links">
							<li><a href="#">About Us</a></li>
							<li><a href="#">Shiping & Return</a></li>
							<li><a href="#">Shiping Guide</a></li>
							<li><a href="#">FAQ</a></li>
						</ul>
					</div>
				</div>
				<!-- /footer widget -->

				<!-- footer subscribe -->
				<div class="col-md-3 col-sm-6 col-xs-6">
					<div class="footer">
						<h3 class="footer-header">Stay Connected</h3>
						<p>Subscribe to our newsletter and get amazing offers</p>
						<form>
							<div class="form-group">
								<input class="input" placeholder="Enter Email Address">
							</div>
							<button class="primary-btn">Join Newslatter</button>
						</form>
					</div>
				</div>
				<!-- /footer subscribe -->
			</div>
			<!-- /row -->
			<hr>
			<!-- row -->
			<div class="row">
				<div class="col-md-8 col-md-offset-2 text-center">
					<!-- footer copyright -->
					<div class="footer-copyright">
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</div>
					<!-- /footer copyright -->
				</div>
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</footer>
	<!-- /FOOTER -->

	<!-- jQuery Plugins -->
	<script src="/tiw-p1/animation/jquery.min.js"></script>
	<script src="/tiw-p1/animation/bootstrap.min.js"></script>
	<script src="/tiw-p1/animation/slick.min.js"></script>
	<script src="/tiw-p1/animation/nouislider.min.js"></script>
	<script src="/tiw-p1/animation/jquery.zoom.min.js"></script>
	<script src="/tiw-p1/animation/main.js"></script>

</body>

</html>

