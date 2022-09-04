<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Start</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>

<%@ page import="com.serezka.web.game.GameCoordinator" %>

<%
    if (session.getAttribute("id") != null) response.sendRedirect("game");

    if (session.getAttribute("id") == null) {
        long userId = (long) (Math.random() * 100000);
        session.setAttribute("id", userId);
    }
%>

<div>
    <FORM ACTION="game" METHOD="POST">
        <div>
            <h1>Select name:</h1>
            <input type="text" value="BiBi" name="name" maxlength="13" pattern="[a-zA-Z]{1,13}">
            <button class="glow-on-hover" type="submit">Играть</button>
        </div>

        <br/>
        <br/>
        <h1><%= GameCoordinator.getBackstory() %>
        </h1>
    </FORM>
</div>
</body>
</html>