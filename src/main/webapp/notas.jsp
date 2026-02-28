<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Avaliacao" %>

<!DOCTYPE html>
<html>
<head>
    <title>Minhas Notas</title>

    <style>
        body {
            font-family: Arial;
        }

        table {
            border-collapse: collapse;
            width: 60%;
            margin: 30px auto;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #f2f2f2;
        }

        h2 {
            text-align: center;
        }
    </style>
</head>
<body>

<h2>Minhas Notas</h2>

<%
    List<Avaliacao> notas = (List<Avaliacao>) request.getAttribute("notas");
%>

<table>
    <tr>
        <th>Disciplina</th>
        <th>Nota 1</th>
        <th>Nota 2</th>
    </tr>

    <%
        if (notas != null) {
            for (Avaliacao a : notas) {
    %>
    <tr>
        <td><%= a.getNomeDisciplina() %></td>
        <td><%= a.getN1() %></td>
        <td><%= a.getN2() %></td>
    </tr>
    <%
            }
        }
    %>

</table>

</body>
</html>