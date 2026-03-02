<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Avaliacao" %>

<!DOCTYPE html>
<html>
<head>
    <title>PokeSchool | Minhas Notas</title>
    <link rel="stylesheet" href="../Styles/notas.css">
</head>
<body>

<div class="layout">

    <!-- SIDEBAR -->
    <aside class="sidebar">
        <div class="profile">
            <img src="../assets/img/pikachu.jpg" alt="Perfil">
            <h3>Pikachu</h3>
        </div>

        <nav>
            <a href="#" class="active">NOTAS</a>
            <a href="observacoesAluno.jsp">OBSERVAÇÕES</a>
            <a href="boletim.jsp">BOLETIM</a>
        </nav>
    </aside>

    <!-- CONTEÚDO -->
    <main class="content">

        <div class="top-wave"></div>

        <div class="boletim-container">
            <h2>Minhas Notas</h2>

            <%
                List<Avaliacao> notas = (List<Avaliacao>) request.getAttribute("notas");
            %>

            <table>
                <tr>
                    <th>Disciplina</th>
                    <th>Nota 1</th>
                    <th>Nota 2</th>
                    <th>Status</th>
                </tr>

                <%
                    if (notas != null && !notas.isEmpty()) {
                        for (Avaliacao a : notas) {
                            double media = (a.getN1() + a.getN2()) / 2.0;
                            String status = media >= 7 ? "APROVADO" : "REPROVADO";
                            String classe = media >= 7 ? "aprovado" : "reprovado";
                %>
                <tr>
                    <td><%= a.getNomeDisciplina() %></td>
                    <td><%= a.getN1() %></td>
                    <td><%= a.getN2() %></td>
                    <td class="<%= classe %>"><%= status %></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="4">Nenhuma nota encontrada.</td>
                </tr>
                <%
                    }
                %>

            </table>

        </div>

    </main>
</div>

</body>
</html>