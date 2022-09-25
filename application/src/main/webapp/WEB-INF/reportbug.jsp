<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <link rel="stylesheet" href="http://localhost:8080/jsonapp/style.css" />
    <title>Report Bug<title>
  </head>
  <body>
    <div class="main-div">
      <form action="">
        <div class="input-div">
          <label for="name">Project Name</label>
          <input type="text" name="name" />
        </div>
        <div class="input-div">
          <label for="">Title</label>
          <input type="text" name="title"/>
        </div>
        <div class="input-div">
          <label for="">Description</label>
          <input type="text" name="description"/>
        </div>
        <div class="input-div">
          <label for="">Severity Level </label>
          <input type="text" name="severity"/>
        </div>
      </form>
    </div>
  </body>
</html>