<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Admin Login</title>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="style/login.css">
	</head>
	<body>
		<img src="images/logo.png">
		<form action="" method="POST">
			<input type="hidden" name="action" value="login">
			<div>
				<label for="email">Email:</label>
				<input name="email" id="email" type="email">
			</div>
			<div>
				<label for="password">Password:</label>
				<input name="password" id="password" type="password">
			</div>
			<div>
				<input type="submit" value="Log In">
			</div>
		</form>
		<script>
			document.querySelector('#email').focus();
		</script>
	</body>
</html>
