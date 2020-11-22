<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <script>
        var servletURL = window.location.origin;
    </script>
    <title>First JSP</title>
</head>
<%@ page import="java.util.Date" %>
<body>
<h2>Hello Heroku! I am JSP</h2>
<strong>Current Time is</strong>: <%=new Date() %>

<form action="/search" id="form1">
    <p>Author: <input type="text" name="author" id="author"></p>

    <p>Title: <input type="text" name="title" id="title"></p>

    <p>Year: <input type="text" name="year" id="year"></p>

    <p>Type: <select name="type" id="type">
        <option value="short">short</option>
        <option value="long">long</option>
    </select></p>

    <p>Sorted By: <select name="sort" id="sort">
        <option value="year">year of publication</option>
        <option value="publicationid">id of publication</option>
    </select></p>

    <p>Record Per Page: <input type="number" name="page" id="page"></p>
    <button type="submit" onclick="window.location.assign(servletURL+'/id');">search</button>
</form>

<form action="/id" id="form2">
    <p>Enter Publication ID: <input type="text" name="id" id="id"></p>
    <button type="submit">search</button>
</form>
</body>
</html>
