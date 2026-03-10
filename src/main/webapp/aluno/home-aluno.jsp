<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Avaliacao" %>
<%@ page import="com.example.pokeschool.model.Observacoes" %>
<%@ page import="com.example.pokeschool.model.Aluno" %>

<!DOCTYPE html>
<html>
<head>
    <title>PokeSchool | Área do Aluno</title>
    <link rel="stylesheet" href="../Styles/dashboard.css">
</head>
<body>

<div class="layout">

    <!-- SIDEBAR -->
    <%
        List<Aluno> listaAlunos = (List<Aluno>)
                request.getAttribute("listaAlunos");
    %>
    <% if (listaAlunos !=null) { for (Aluno a : listaAlunos)
    { %>
    <aside class="sidebar">
        <div class="profile">
            <img src="../assets/img/pikachu.jpg" alt="Perfil">
            <h3><%= a.getNomeCompleto() %></h3>
        </div>

        <nav>
            <a class="menu-btn active" data-target="notas">NOTAS</a>
            <a class="menu-btn" data-target="observacoes">OBSERVAÇÕES</a>
            <a class="menu-btn" data-target="boletim">BOLETIM</a>
        </nav>
    </aside>

    <!-- CONTEÚDO -->
    <main class="content">
        <div class="top-wave"></div>

        <div class="container">

            <!-- ================= NOTAS ================= -->
            <section id="notas" class="section active-section">
                <h2>Minhas Notas</h2>

                <table>
                    <tr>
                        <th>Disciplina</th>
                        <th>Nota 1</th>
                        <th>Nota 2</th>
                    </tr>

                    <%
                        List<Avaliacao> notas =
                                (List<Avaliacao>) request.getAttribute("notas");

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
            </section>

            <!-- ================= OBSERVAÇÕES ================= -->
            <section id="observacoes" class="section">

                <h2>Minhas Observações</h2>

                <%
                    List<Observacoes> lista =
                            (List<Observacoes>) request.getAttribute("listaObservacoes");

                    if (lista != null) {
                        for (Observacoes o : lista) {
                %>

                <div class="obs-card">
                    <p class="descricao"><%= o.getDescricao() %></p>

                    <div class="obs-footer">
                        <span>
                            <%= o.getNomeProfessor() != null ?
                                    o.getNomeProfessor() : "Anônimo" %>
                        </span>
                        <span><%= o.getData() %></span>
                    </div>
                </div>

                <%
                        }
                    }
                %>
            </section>

            <!-- ================= BOLETIM ================= -->
            <section id="boletim" class="section">

                <h2>Boletim</h2>

                <table>
                    <tr>
                        <th>Disciplina</th>
                        <th>Média</th>
                        <th>Status</th>
                    </tr>

                    <%
                        if (notas != null) {
                            for (Avaliacao a : notas) {
                                double media =
                                        (a.getN1() + a.getN2()) / 2.0;
                                String status =
                                        media >= 7 ? "APROVADO" : "REPROVADO";
                                String classe =
                                        media >= 7 ? "aprovado" : "reprovado";
                    %>

                    <tr>
                        <td><%= a.getNomeDisciplina() %></td>
                        <td><%= String.format("%.1f", media) %></td>
                        <td class="<%= classe %>"><%= status %></td>
                    </tr>

                    <%
                            }
                        }
                    %>
                </table>

            </section>

        </div>
    </main>
</div>

<!-- ================= JAVASCRIPT ================= -->
<script>
    const buttons = document.querySelectorAll(".menu-btn");
    const sections = document.querySelectorAll(".section");

    buttons.forEach(button => {
        button.addEventListener("click", function (e) {
            e.preventDefault();

            // remove active do menu
            buttons.forEach(btn => btn.classList.remove("active"));
            this.classList.add("active");

            // esconde todas sections
            sections.forEach(sec => sec.classList.remove("active-section"));

            // mostra a section clicada
            const target = this.getAttribute("data-target");
            document.getElementById(target)
                .classList.add("active-section");
        });
    });
</script>

</body>
</html>