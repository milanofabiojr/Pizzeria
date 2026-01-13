<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.fabio.model.*" %>

<%
    Utente u = (Utente) session.getAttribute("user");
    List<Impasto> impasti = (List<Impasto>) request.getAttribute("impasti");
    List<Ingrediente> ingredienti = (List<Ingrediente>) request.getAttribute("ingredienti");
    List<Pizza> pizze = (List<Pizza>) request.getAttribute("pizze");
%>

<html>
<head>
    <title>Dashboard</title>
</head>
<body>

<h2>Benvenuto <%= u.getUsername() %></h2>

<form action="<%= request.getContextPath() %>/dashboard" method="post">

<!-- ================= IMPASTI (RADIO) ================= -->
<h3>IMPASTI</h3>
<%
    for (Impasto i : impasti) {
%>
    <input type="radio" name="impasto" value="<%= i.getId() %>">
    <%= i.getNome() %><br>
<%
    }
%>

<br>

<!-- ================= INGREDIENTI (CHECKBOX) ================= -->
<h3>INGREDIENTI</h3>
<%
    for (Ingrediente ing : ingredienti) {
%>
    <input type="checkbox" name="ingredienti" value="<%= ing.getId() %>">
    <%= ing.getNome() %><br>
<%
    }
%>

<br>

Nome Pizza:<br>
<input type="text" name="nomePizza"><br><br>

<input type="submit" value="CREA PIZZA">

</form>

<hr>

<h3>LE TUE PIZZE</h3>

<table border="1">
<tr>
    <th>Nome</th>
    <th>Impasto</th>
    <th>Ingredienti</th>
    <th>Azioni</th>
</tr>

<%
    for (Pizza p : pizze) {
%>
<tr>
    <td><%= p.getNome() %></td>
    <td><%= p.getImpasto().getNome() %></td>
    <td>
        <%
            for (Ingrediente i : p.getIngredienti()) {
                out.print(i.getNome() + " ");
            }
        %>
    </td>
    <td>
        <form action="<%= request.getContextPath() %>/dashboard" method="post">
            <input type="hidden" name="deleteId" value="<%= p.getId() %>">
            <input type="submit" value="ELIMINA">
        </form>
    </td>
</tr>
<%
    }
%>

</table>

</body>
</html>