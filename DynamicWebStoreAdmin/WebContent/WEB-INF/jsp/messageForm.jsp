<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% String receivers = (String) request.getAttribute("receivers"); %>

<html>
    <head>
        <title>Edit Message</title>
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
						<button id="confirm">Send</button>
						<button id="cancel">Cancel</button>
					</div>
				</div>
				<div id="lyt-right">
					<div id="userHeader">
						<div id="userProfileData">
							<img src="images/mail.png">
							<div>
								<h1>New Message</h1>
								<h2>
									<% if(receivers != null) { %>
									Sending message to 
									<% int receiversCount = receivers.split(" ").length;
										if(receiversCount == 1) { %>
									<%=receivers %>
									<% 	} else { %>
									<%= receiversCount %> receivers
									<% }} %>
								</h2>
							</div>
						</div>
					</div>
					<hr>
					<form action="massOperation" method="POST" name="mass-operation" id="mainForm">
						<input type="hidden" name="operation" value="mass-send-message">
						<input type="hidden" name="keys" value="<%=receivers == null ? "" : receivers %>">
						<div id="mainUserData">
							<div>
								<div class="dataHeaderBar">
									<h4>Message Content</h4>
									<span class="hidden">&#9660;</span>
									<span>&#9650;</span>
								</div>
								<div class="dataContent">
									<table>
										<tr>
											<th><label for="messageBody">Message</label></th>
											<td>
												<textarea name="messageBody" id="messageBody"></textarea>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						<input type="submit" value="Send" />
					</form>
				</div>
            </div>
            <%@ include file="../jspf/footer.jspf" %>
			<script src="js/entityDetail.js"></script>
		 </div>
    </body>
</html>