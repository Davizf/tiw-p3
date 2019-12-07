<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List" %>
<%@ page import="es.tiwadmin.model.MessageCollection" %>

<html>
    <head>
        <title>Message List</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
		<link rel="stylesheet" href="style/common.css">
		<link rel="stylesheet" href="style/entityList.css">
		<link rel="stylesheet" href="style/messageList.css">
    </head>
    <body>
        <div id="page">
           	<%@ include file="../jspf/header.jspf" %>
            <div id="content">
                <div id="lyt-left">
					<div>
						<h2>Actions</h2>
						<button id="mass-remove-messages" class="mass-operation">Delete Selected</button>
						<button id="mass-send-message" class="mass-operation">Reply Selected</button>
					</div>
				</div>
		
				<div id="lyt-right">
					<h1>Message List</h1>
					<table>
						<thead>
							<tr>
								<th><input type="checkbox"></th>
								<th>Sender</th> 
								<th>Message</th>
								<th>Reply</th> 
							</tr>
						</thead>
						<tbody>
						<%
							@SuppressWarnings("unchecked")
							List<MessageCollection> items = (List<MessageCollection>) session.getAttribute("messages");
							if(items != null) {
								for(MessageCollection item : items) {
									int unreadMessages = item.getUnreadMessages();
						%>
							<tr>
								<td><input type="checkbox"></td>
								<td>
									<% if(unreadMessages > 0) { %><b><% } %>
									<%= item.getSender() %>
									<% if(unreadMessages > 0) { %></b><% } %>
								</td>
								<td>
									<% for(int i = item.getMsgs().size() - 1; i >= 0; --i) { %>
										<% if(unreadMessages > 0) { %><b><% } %>
										<%= item.getMsgs().get(i)%>
										<% if(unreadMessages > 0) { %></b><% } %> 
										<br>
									<% unreadMessages--;} %>
								</td>
								<td>
									<form action="messageWrite" method="POST">
										<button name="itemPK" value="<%=item.getSender() %>"><img src="images/mail.png"></button>
									</form>
								</td>
								<% item.setUnreadMessages(0);} %>
						<% } %>
						</tr>
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
