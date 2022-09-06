<%--
  Created by IntelliJ IDEA.
  User: serezk4
  Date: 01.09.2022
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Game</title>
    <link rel="stylesheet" href="css/game.css">
</head>
<body style="background: whitesmoke;
            margin: 0;
            padding: 0;">

<%-- INIT DATA --%>
<%
    if (!Objects.equals(request.getParameter("name"), null)) session.setAttribute("name", request.getParameter("name"));
    String name = (String) session.getAttribute("name");

    String prevAnswer = request.getParameter("answer") == null ? "" : request.getParameter("answer");
%>



<%-- import classes --%>
<%@ page import="com.serezka.web.game.*" %>
<%@ page import="com.serezka.web.game.engine.objects.dialogs.Dialog" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>

<%-- set session attributes --%>
<%
    if (session.getAttribute("id") != null && !GameCoordinator.containsUser((long) session.getAttribute("id"))) {
        GameCoordinator.addUser((long) session.getAttribute("id"), name);
    }

    if (session.getAttribute("count") == null) session.setAttribute("count", 0);
    session.setAttribute("name", name);

    if (prevAnswer.equals("restart")) {
        prevAnswer = "new";
        session.setAttribute("count", (int) (session.getAttribute("count")) + 1);
    }
%>

<%--name--%>
<div style="display: flex; padding: 15px 15px 15px 15px;">
    <h1>Your name:</h1>
    <h1 style="font-weight: bold; padding-left: 10px;"><%= name %>
    </h1>
</div>

<%-- game --%>
<div style="display: flex; padding: 15px 15px 15px 15px; height: 400px;">

    <%
        if (session.getAttribute("id") == null) {
            out.print(String.format("<a style=\"font-size: 50px; font-family: 'Roboto', sans-serif; font-weight: bold; color: red; \" href=\"index\">%s</a>", "Authorization >>>"));
            return;
        }

        Map.Entry<String, List<Dialog>> e = GameCoordinator.getNextDialog(GameCoordinator.getUserById((long) session.getAttribute("id")), prevAnswer, true);
        StringBuilder sb = new StringBuilder();

        String q = e.getKey();

        if (e.getValue().isEmpty())
            sb.append("<option style=\"font-size: 20px; font-family: 'Roboto', sans-serif; font-weight: normal;\" selected value=\"restart\">New game</option>");

        for (int i = 0; i < e.getValue().size(); i++)
            sb.append(String.format("<option style=\"font-size: 20px; font-family: 'Roboto', sans-serif; font-weight: normal;\" %s value=\"%s\">%s</option>", i == 0 ? "selected" : "", e.getValue().get(i).getQuestion(), e.getValue().get(i).getQuestion()));

    %>

    <FORM ACTION="game" METHOD="POST" style="height: available; width: 500px">
        <p><select size="4" name="answer">
            <option style="font-size: 30px; font-family: 'Roboto', sans-serif; font-weight: bold; color: black; "
                    disabled><%= q %>
            </option>
            <%= sb.toString() %>
        </select></p>
        <button style="font-size: 30px;" type="submit">Submit</button>
    </FORM>
</div>

<%-- stats --%>

<div style="padding: 15px 15px 15px 15px;">
    <code class="code_stats"
          style="font-size: large; alignment: bottom; background: cornsilk; padding: 0px 0px 0px 0px;">
        Games: <%= session.getAttribute("count")%>
        <br/>
        !> IP: <strong><%=  request.getRemoteAddr() %>
    </strong>
        <br/>
        !> ID: <strong><%= session.getAttribute("id") %>
    </strong>
        <br/>
    </code>
    <br/>
    <code style="font-size: large; alignment: bottom; background: cornsilk; box-shadow: #111111; ; padding: 0px 0px 0px 0px;">
        Active players:
        <br/>
        <%= GameCoordinator.getUsers() %>
    </code>
</div>

</body>
</html>
