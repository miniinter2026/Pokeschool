<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Observacoes" %>

<html>
<head>
    <title>Minhas Observações</title>
</head>
<body>

<h2>📘 Minhas Observações</h2>

<table border="1" cellpadding="10">
    <tr>
        <th>Data</th>
        <th>Disciplina</th>
        <th>Professor</th>
        <th>Descrição</th>
    </tr>

    <%
        List<Observacoes> lista =
                (List<Observacoes>) request.getAttribute("listaObservacoes");

        if (lista != null && !lista.isEmpty()) {
            for (Observacoes o : lista) {
    %>

    <tr>
        <td><%= o.getData() %></td>
        <td><%= o.getNomeDisciplina() %></td>
        <td><%= o.getNomeProfessor() %></td>
        <td><%= o.getDescricao() %></td>
    </tr>

    <%
        }
    } else {
    %>
    <tr>
        <td colspan="4">Nenhuma observação encontrada.</td>
    </tr>
    <%
        }
    %>

</table>

</body>
</html>