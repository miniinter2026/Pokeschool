<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Avaliacao" %>
<!DOCTYPE html>
<html>
<head>
    <title>PokeSchool | Boletim</title>
    <link rel="stylesheet" type="text/css" href="../Styles/boletim.css"/>
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
            <a href="notas.jsp">NOTAS</a>
            <a href="observacoesAluno.jsp">OBSERVAÇÕES</a>
            <a href="#" class="active">BOLETIM</a>
        </nav>
    </aside>

    <!-- CONTEÚDO -->
    <main class="content">

        <div class="top-wave"></div>

        <div class="boletim-container">
            <h2>Boletim Escolar</h2>

            <table>
                <tr>
                    <th>Disciplina</th>
                    <th>Nota 1</th>
                    <th>Nota 2</th>
                    <th>Média Final</th>
                </tr>

                <%
                    List<Avaliacao> boletins = (List<Avaliacao>) request.getAttribute("boletins");
                    if (boletins != null && !boletins.isEmpty()) {
                        for (Avaliacao nota : boletins) {
                            double media = nota.getMediaCalculada();
                %>
                <tr>
                    <td><%= nota.getNomeDisciplina() %></td>
                    <td><%= nota.getN1() %></td>
                    <td><%= nota.getN2() %></td>
                    <td><%= String.format("%.1f", media) %></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="4" style="text-align:center;">
                        Nenhuma nota encontrada.
                    </td>
                </tr>
                <%
                    }
                %>
            </table>

            <p class="resultado">
                Resultado: <span class="aprovado">Aprovado</span>
            </p>

        </div>

    </main>
</div>

</body>
</html>