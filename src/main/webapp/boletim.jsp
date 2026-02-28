<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Avaliacao" %>
<!DOCTYPE html>
<html>
<head>
    <title>Boletim Escolar</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #333;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin: 30px 0;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #2196F3;
            color: white;
        }

        .aprovado {
            color: green;
            font-weight: bold;
        }

        .reprovado {
            color: red;
            font-weight: bold;
        }

        .voltar {
            text-align: center;
            margin-top: 20px;
        }

        .voltar a {
            color: #2196F3;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>📊 Boletim Escolar</h2>

    <table>
        <tr>
            <th>Disciplina</th>
            <th>Nota 1</th>
            <th>Nota 2</th>
            <th>Média</th>
            <th>Situação</th>
        </tr>

        <%
            List<Avaliacao> boletins = (List<Avaliacao>) request.getAttribute("boletins");
            if (boletins != null && !boletins.isEmpty()) {
                for (Avaliacao nota : boletins) {
                    double media = nota.getMediaCalculada();
                    String status = nota.getStatusCalculado();
                    String classe = media >= 7 ? "aprovado" : "reprovado";
        %>
        <tr>
            <td><%= nota.getNomeDisciplina() %></td>
            <td><%= nota.getN1() %></td>
            <td><%= nota.getN2() %></td>
            <td><%= String.format("%.1f", media) %></td>
            <td class="<%= classe %>"><%= status %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5" style="text-align: center;">Nenhuma nota encontrada.</td>
        </tr>
        <%
            }
        %>

    </table>

    <div class="voltar">
        <a href="javascript:history.back()">← Voltar</a>
    </div>
</div>
</body>
</html>