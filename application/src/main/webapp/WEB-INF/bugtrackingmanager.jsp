<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Insert title here</title>
  </head>
  <body>
    <% String email = "something@email.com"; String role = "Project Manager";
    String date = "2000-09-23"; String projects[] = {"Hello", "World"}; %>
    <div>
      <label>Email address:- </label>
      <label> <%= email %></label>
    </div>
    <div>
      <label>Role:- </label>
      <label> <%= role %></label>
    </div>
    <div>
      <label>Date:- </label>
      <label> <%= date %></label>
    </div>
    <a href="/newproject.jsp">Create a new project</a>
    <div>
      <label>Projects:- </label>
      <ol>
        <% for(String p: projects){%>
        <li><%=p%></li>
        <% } %>
      </ol>
    </div>
  </body>
</html>
