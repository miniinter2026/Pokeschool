<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Aluno" %>
<%@ page import="com.example.pokeschool.model.Professor" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>PokeSchool - Admin</title>
    <link rel="stylesheet" type="text/css" href="../Styles/admDashboard.css">
</head>
<body>

<div class="layout">

    <!-- SIDEBAR -->
    <div class="sidebar">
        <div class="profile">
            <img src="../assets/img/arceus.jpg" alt="Admin">
            <h3>ADMIN</h3>
        </div>

        <nav>
            <a href="#" class="nav-btn active" onclick="mostrarSecao('alunos', this)">Alunos</a>
            <a href="#" class="nav-btn" onclick="mostrarSecao('professores', this)">Professores</a>
            <a href="login.jsp">Sair</a>
        </nav>
    </div>

    <!-- CONTENT -->
    <div class="content">
        <div class="top-wave"></div>

        <div class="container">

            <!-- ===================== -->
            <!-- SEÇÃO ALUNOS -->
            <!-- ===================== -->
            <div id="secao-alunos" class="section active-section">

                <h2>Gerenciar Alunos</h2>

                <input type="text" id="buscaAluno" placeholder="Buscar por nome ou RA..." onkeyup="buscarAluno()">

                <table id="tabelaAlunos">
                    <thead>
                    <tr>
                        <th>RA</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Sala</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
                        if (listaAlunos != null) {
                            for (Aluno a : listaAlunos) {
                    %>
                    <tr>
                        <td><%= a.getRa() %></td>
                        <td><%= a.getNomeCompleto() %></td>
                        <td><%= a.getEmail() %></td>
                        <td><%= a.getIdSala() %></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    </tbody>
                </table>
            </div>


            <!-- ===================== -->
            <!-- SEÇÃO PROFESSORES -->
            <!-- ===================== -->
            <div id="secao-professores" class="section">

                <h2>Gerenciar Professores</h2>

                <input type="text" id="buscaProfessor" placeholder="Buscar por nome..." onkeyup="buscarProfessor()">

                <table id="tabelaProfessores">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Usuário</th>
                        <th>Email</th>
                        <th>Disciplina</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Professor> listaProfessores = (List<Professor>) request.getAttribute("listaProfessores");
                        if (listaProfessores != null) {
                            for (Professor p : listaProfessores) {
                    %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getNomeCompleto() %></td>
                        <td><%= p.getNomeUsuario() %></td>
                        <td><%= p.getEmail() %></td>
                        <td><%= p.getNomeDisciplina() != null ? p.getNomeDisciplina() : "Não definida" %></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

<script>

    function mostrarSecao(secao, element) {

        // Remove active de todos
        document.querySelectorAll('.nav-btn').forEach(btn => btn.classList.remove('active'));

        // Ativa o botão clicado
        element.classList.add('active');

        // Esconde todas seções
        document.querySelectorAll('.section').forEach(sec => {
            sec.classList.remove('active-section');
        });

        // Mostra a selecionada
        document.getElementById('secao-' + secao).classList.add('active-section');
    }


    function buscarAluno() {
        let input = document.getElementById('buscaAluno').value.toUpperCase();
        let rows = document.querySelectorAll('#tabelaAlunos tbody tr');

        rows.forEach(row => {
            let ra = row.cells[0].textContent.toUpperCase();
            let nome = row.cells[1].textContent.toUpperCase();
            row.style.display = (ra.includes(input) || nome.includes(input)) ? '' : 'none';
        });
    }

    function buscarProfessor() {
        let input = document.getElementById('buscaProfessor').value.toUpperCase();
        let rows = document.querySelectorAll('#tabelaProfessores tbody tr');

        rows.forEach(row => {
            let nome = row.cells[1].textContent.toUpperCase();
            row.style.display = nome.includes(input) ? '' : 'none';
        });
    }

</script>

</body>
</html>