<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>

<html>
    <head>
        <title>Wrong Params</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
		<link rel="stylesheet" href="style/common.css">
		<link rel="stylesheet" href="style/errorPage.css">
    </head>
    <body>
        <div id="page">
            <%@ include file="../jspf/header.jspf" %>
            <div id="content" class="error">
				<h1>Wrong Parameters</h1>
				<span>There are some problems with your inputs. Please go back and fix them:</span>
				<%
					@SuppressWarnings("unchecked")
					List<String> errors = (List<String>) request.getAttribute("errors");
					for(String error : errors) {
				%>
					<p><%=error%></p>
				<% 	} %>
				<button id="btn-back">Back</button>
				<span></span>
            </div>
            <%@ include file="../jspf/footer.jspf" %>
		 </div>
		 <script src="js/wrongParams.js"></script>
    </body>
</html>
