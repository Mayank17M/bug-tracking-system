<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Insert title here</title>
  </head>
   <% 
  	String username = "";
  	String email = "";
  	String message = "";
  	String projects[] = {"hello", "world"};
  %>
  
  <body>
    <div>
      <label>Username:- </label>
      <label> <%= username %></label>
    </div>
    <div>
      <label>Email:- </label>
      <label> <%= email %></label>
    </div>
    <div>
    	<label>Projects:- </label>
    	<% if( project == false){%>
    		<p><%= message %></p>
    	<% }else{ %>
    		<ol>
    		<% for(String p: projects){%>
    			<li> <%=p%> </li>
    		<% } %>
    		</ol>
    	<% } %>
    </div>
    
  </body>
</html>