<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Observacoes" %>

<!DOCTYPE html>
<html>
<head>
    <title>PokeSchool | Observações</title>
    <link rel="stylesheet" href="../Styles/observacoes.css">
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
            <a href="#" class="active">OBSERVAÇÕES</a>
            <a href="boletim.jsp">BOLETIM</a>
        </nav>
    </aside>

    <!-- CONTEÚDO -->
    <main class="content">

        <div class="top-wave"></div>

        <div class="boletim-container">
            <h2>Minhas Observações</h2>

            <%
                List<Observacoes> lista =
                        (List<Observacoes>) request.getAttribute("listaObservacoes");

                if (lista != null && !lista.isEmpty()) {
                    for (Observacoes o : lista) {
            %>

            <div class="obs-card">
                <p class="descricao">
                    <%= o.getDescricao() %>
                </p>

                <div class="obs-footer">
                    <span class="professor">
                        <%= o.getNomeProfessor() != null ?
                                o.getNomeProfessor() : "Anônimo" %>
                    </span>

                    <span class="data">
                        <%= o.getData() %>
                    </span>
                </div>
            </div>

            <%
                }
            } else {
            %>

            <div class="obs-card">
                <p>Nenhuma observação encontrada.</p>
            </div>

            <%
                }
            %>

        </div>

    </main>
</div>

</body>
</html>