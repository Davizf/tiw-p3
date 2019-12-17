<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="es.tiwadmin.model.MyMessage" %>


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
							List<MyMessage> items = (List<MyMessage>) request.getAttribute("messages");
							if(items != null) {
								ArrayList<String> users = new ArrayList<String>();
								for(MyMessage msg : items)
									if(!users.contains(msg.getSender()))
										users.add(msg.getSender());
								
								for(String sender : users) {
									List<MyMessage> toPrint = new ArrayList<MyMessage>();
									for(MyMessage elem : items)
										if(elem.getSender().equals(sender))
											toPrint.add(elem);
									
									//items.stream().filter(elem -> elem.getSender().equals(sender)).collect(Collectors.toList());
									Collections.reverse(toPrint);
						%>
							<tr>
								<td><input type="checkbox"></td>
								<td>
									<%= sender %>
								</td>
								<td>
									<% for(MyMessage msg : toPrint) { %>
										<%= msg.getMessage() %>
										<br>
									<% } %>
								</td>
								<td>
									<form action="messageWrite" method="POST">
										<button name="itemPK" value="<%=sender %>"><img src="images/mail.png"></button>
									</form>
								</td>
								<% } %>
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
