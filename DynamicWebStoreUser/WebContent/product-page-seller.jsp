<%@page import="model.Category"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="controllers.CategoryController"%>
<%@page import="controllers.ProductController"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="model.Product"%>
<%@page import="model.ProductInCart"%>
<%@page import="controllers.UserController"%>
<%@page import="model.CategoryLevel"%>
<%@page import="model.HierarchicalCategories"%>
<%@page import="model.User"%>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

	<title>Product page</title>

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
							
							<%
							if (userBean != null && userBean.getType() != UserController.USER_TYPE_SELLER){
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
													<h3 class="product-price">$<%=productsInCart.get(i).getProduct().getPrice().doubleValue() %> <span class="qty">x<%=productsInCart.get(i).getQuantity() %></span></h3>
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
	
	<%
	String operation=(String)request.getAttribute("operation");
	boolean modify=(operation != null && !operation.equals("") && !operation.equals("null") && operation.equals("modify"));
	boolean add=(operation != null && !operation.equals("") && !operation.equals("null") && operation.equals("add"));
	
	Product product=(Product)request.getAttribute("product");
	Product p=new Product();
	if (add) {
		p.setId(-1);
		p.setName("Insert name for the new product");
		model.Category p_category=new model.Category();
		p_category.setName("");
		p.setCategoryBean(p_category);
		p.setPrice(new BigDecimal("0"));
		p.setShortDescription("Insert short description for the new product");
		p.setDescription("Insert description for the new product");
		p.setImagePath("/tiw-p1/images/default-img.png");
		p.setStock(0);
		p.setSalePrice(new BigDecimal("0"));
		p.setShipPrice(new BigDecimal("0"));
	}
	if (modify) {
		p.setId(product.getId());
		p.setName(product.getName());
		p.setPrice(product.getPrice());
		p.setShortDescription(product.getShortDescription());
		p.setDescription(product.getDescription());
		p.setImagePath(product.getImagePath());
		p.setStock(product.getStock());
		p.setSalePrice(product.getSalePrice());
		p.setShipPrice(product.getShipPrice());
		p.setCategoryBean(product.getCategoryBean());
	}
	%>
	
	<!-- BREADCRUMB -->
	<div id="breadcrumb">
		<div class="container">
			<form action="products.jsp" method="post" id="form_category" hidden>
				<input type="hidden" name="category" value="" id="form_category_input">
			</form>
			<ul class="breadcrumb">
				<li><a href="index.jsp">Home</a></li>
				<li><a href="catalogue.jsp">Catalogue</a></li>
				<%if (add) { %>
				<li class="active">Add product</li>
				<%} %>
				<%if (modify) { %>
				<li class="active">Modify product</li>
				<%} %>
			</ul>
		</div>
	</div>
	<!-- /BREADCRUMB -->
	
	<!-- section -->
	<div class="section">
		<form action="Catalogue" method="post" enctype="multipart/form-data">
		<input class="input" type="hidden" name="product_user" value="<%=((String)request.getAttribute("seller_user")) %>" required>
		<input class="input" type="hidden" name="product_id" value="<%=p.getId() %>" required>
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<!--  Product Details -->
				<div class="product product-details clearfix">
					<div class="col-md-6">
						<div id="product-main-view">
							<div class="product-view">
								<img id="product_image_tag" src="<%=p.getImagePath() %>" alt="">
								<input type="hidden" name="product_image_path" value="<%=p.getImagePath() %>">
							</div>
						</div>
						<input type="file" id="input_files_image" name="product_image_file">
						<script type="text/javascript">
							if (window.File && window.FileReader && window.FileList && window.Blob) {
								// Great success! All the File APIs are supported
							} else {
								document.getElementById("product_image_tag").style.display = "none";
								alert('The File APIs are not fully supported in this browser.');
							}
							function handleFileSelect(evt) {
								var files = evt.target.files;

								for (var i = 0, f; f = files[i]; i++) {
									// Only process image files.
									if (!f.type.match('image.*')) {
										alert("Only images, please");
										continue;
									}

									var reader = new FileReader();
									// Put the image to img tag
									reader.onload = (function(theFile) {
										return function(e) {
											var name=escape(theFile.name), path=e.target.result;
											//alert("name="+name+", path"+path);
											document.getElementById("product_image_tag").src=path;
										};
									})(f);
									// Read in the image file as a data URL.
									reader.readAsDataURL(f);
								}
							}
							document.getElementById('input_files_image').addEventListener('change', handleFileSelect, false);
						</script>
					</div>
					<div class="col-md-6">
						<div class="product-body">
							<label for="product_name">Name</label>
							<input class="input" name="product_name" placeholder="<%=p.getName() %>" required <%if (modify) {%>value="<%=p.getName() %>"<%} %>>
							<%if(categories != null) { %>
								<br></br>
								<label for="product_category">Category</label>
								<select class="input search-categories" name="product_category">
								<option value="">Select a category for the new product</option>
								<% for(Category category : categories) { %>
									<%if ( p.getCategoryBean().getName().equals(category.getName()) ) {%>
										<option value="<%=category.getName() %>" selected><%=hc.getLineOfId(category.getId()) %></option>
									<%} else {%>
										<option value="<%=category.getName() %>"><%=hc.getLineOfId(category.getId()) %></option>
									<%} %>
								<%} %>
								</select>
							<%} %>
							<br></br>
							<label for="product_price">Price</label>
							<input class="input" type="text" name="product_price" pattern="^\d*(\.\d{0,2})?$" required placeholder="<%=p.getPrice().doubleValue() %>" <%if (modify) {%>value="<%=p.getPrice().doubleValue() %>"<%} %>>
							<br></br>
							<label for="product_sale_price">Sale price</label>
							<input class="input" type="text" name="product_sale_price" pattern="^\d*(\.\d{0,2})?$" required placeholder="<%=p.getSalePrice().doubleValue() %>" <%if (modify) {%>value="<%=p.getSalePrice().doubleValue() %>"<%} %>>
							<br></br>
							<label for="product_ship_price">Ship price</label>
							<input class="input" type="text" name="product_ship_price" pattern="^\d*(\.\d{0,2})?$" required placeholder="<%=p.getShipPrice().doubleValue() %>" <%if (modify) {%>value="<%=p.getShipPrice().doubleValue() %>"<%} %>>
							<br></br>
							<label for="product_stock">Stock</label>
							<input class="input" type="text" name="product_stock" pattern="^\d*(\.\d{0,2})?$" required placeholder="<%=p.getStock() %>" <%if (modify) {%>value="<%=p.getStock() %>"<%} %>>
							<p hidden><strong>Brand:</strong> E-SHOP</p>
							<br></br>
							<label for="product_short_description">Short description</label>
							<input class="input" name="product_short_description" placeholder="<%=p.getShortDescription() %>" required <%if (modify) {%>value="<%=p.getShortDescription() %>"<%} %>>

						</div>
						
						<%
						String product_operation="", value_button="";
						if (modify) {
							product_operation="modify_product";
							value_button="Modify";
						} else if (add) {
							product_operation="add_product";
							value_button="Add";
						}
						%>
						<br><button type="submit" class="primary-btn" name="type" value="<%=product_operation %>"><%=value_button %></button>
					</div>
					
					<div class="col-md-12">
						<div class="product-tab">
							<ul class="tab-nav">
								<li class="active"><a data-toggle="tab" href="#tab1">Description</a></li>
								<!-- <li><a data-toggle="tab" href="#tab1">Details</a></li>
								<li><a data-toggle="tab" href="#tab2">Reviews (3)</a></li> -->
							</ul>
							<div class="tab-content">
								<div id="tab1" class="tab-pane fade in active">
									<p><textarea name="product_description" required style="width: 100%;height: 200px;" placeholder="<%=p.getDescription() %>" <%if (modify) {%>value="<%=p.getDescription() %>"<%} %>></textarea></p>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!-- /Product Details -->
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
		</form>
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