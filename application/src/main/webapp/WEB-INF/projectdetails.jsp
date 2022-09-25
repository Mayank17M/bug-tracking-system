<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Insert title here</title>
  </head>
  <% String name = "hello"; String date = "2020-02-02"; String projectManager =
  "asd"; String team[] = {"hello","world"}; %>
  <body>
    <div>
      <label>Project Name:- </label>
      <label> <%= name %></label>
    </div>
    <div>
      <label>Start Date:- </label>
      <label> <%= date %></label>
    </div>
    <div>
      <label>Project Manager:- </label>
      <label> <%= projectManager %></label>
    </div>
    <div>
      <label>Projects:- </label>
      <ol>
        <% for(String t: team){%>
        <li><%=t%></li>
        <% } %>
      </ol>
    </div>
  </body>
</html>
