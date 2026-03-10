<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Aluno" %>
<%@ page import="com.example.pokeschool.model.Professor" %>
<%@ page import="com.example.pokeschool.model.Avaliacao" %>
<%@ page import="com.example.pokeschool.model.Observacoes" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>PokeSchool - Dashboard Professor</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Styles/professorDashboard.css">
</head>

<body>

<div class="layout">
    <%
        Professor professor = (Professor) session.getAttribute("professor");
        List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
        Aluno alunoSelecionado = (Aluno) request.getAttribute("alunoSelecionado");
    %>
    <aside class="sidebar">
        <div class="profile">
            <img src="../assets/img/ProfesorOak.jpg" alt="Foto do Professor Oak">
            <h3><%= professor != null ? professor.getNomeCompleto() : "Professor" %></h3>
        </div>
        <nav>
            <a class="nav-btn active" data-target="dashboard">DASHBOARD</a>
            <a class="nav-btn" data-target="notas">NOTAS</a>
            <a class="nav-btn" data-target="observacoes">OBSERVAÇÕES</a>
            <a class="nav-btn logout" href="logout">Sair</a>
        </nav>
    </aside>

    <main class="content">
        <div class="top-wave"></div>
        <div class="container">

            <section id="dashboard" class="section active-section">
                <h2>Dashboard</h2>
                <div class="obs-card">
                    <p><strong>Bem-vindo(a), </strong><%= professor.getNomeCompleto() %>!</p>
                    <p><strong>Disciplina:</strong>
                        <%= professor.getNomeDisciplina() != null ? professor.getNomeDisciplina() : "Não definida" %>
                    </p>
                </div>

                <h3>Alunos Matriculados</h3>
                <table>
                    <thead>
                    <tr>
                        <th>RA</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Sala</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (listaAlunos != null) { for (Aluno a : listaAlunos) { %>
                    <tr>
                        <td><%= a.getRa() %></td>
                        <td><%= a.getNomeCompleto() %></td>
                        <td><%= a.getEmail() %></td>
                        <td><%= a.getIdSala() %></td>
                    </tr>
                    <% } } %>
                    </tbody>
                </table>
            </section>

            <section id="notas" class="section">
                <h2>Gerenciamento de Notas</h2>
                <div class="obs-card">
                    <form method="get" action="professor/notas">
                        <label>Filtrar Aluno:</label>
                        <select name="ra" onchange="this.form.submit()">
                            <option value="">Selecione aluno</option>
                            <%
                                String raSelecionado = (String) request.getAttribute("raSelecionado");
                                if (listaAlunos != null) {
                                    for (Aluno a : listaAlunos) {
                                        String selected = (raSelecionado != null && raSelecionado.equals(String.valueOf(a.getRa()))) ? "selected" : "";
                            %>
                            <option value="<%= a.getRa() %>" <%= selected %>>RA <%= a.getRa() %> - <%= a.getNomeCompleto() %></option>
                            <% } } %>
                        </select>
                    </form>
                </div>

                <% if (alunoSelecionado != null) { %>
                <button class="btn btn-add" onclick="abrirModal('modal-add')">+ ADICIONAR NOTA</button>
                <table>
                    <thead>
                    <tr>
                        <th>Disciplina</th>
                        <th>N1</th>
                        <th>N2</th>
                        <th>Média</th>
                        <th>Situação</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Avaliacao> listaAvaliacao = (List<Avaliacao>) request.getAttribute("listaAvaliacao");
                        if (listaAvaliacao != null) { for (Avaliacao av : listaAvaliacao) {
                            double media = (av.getN1() + av.getN2()) / 2;
                            String classe = media >= 7 ? "aprovado" : "reprovado";
                    %>
                    <tr>
                        <td><%= av.getNomeDisciplina() %></td>
                        <td><%= av.getN1() %></td>
                        <td><%= av.getN2() %></td>
                        <td><%= String.format("%.1f", media) %></td>
                        <td class="<%= classe %>"><%= media >= 7 ? "Aprovado" : "Reprovado" %></td>
                        <td>
                            <button class="btn btn-edit" onclick="abrirEditar('<%= av.getIdBoletim() %>', '<%= av.getN1() %>', '<%= av.getN2() %>')">EDITAR</button>
                            <button class="btn btn-danger" onclick="confirmarDelete('<%= av.getId() %>')">EXCLUIR</button>
                        </td>
                    </tr>
                    <% } } %>
                    </tbody>
                </table>
                <% } %>
            </section>

            <section id="observacoes" class="section">
                <h2>Observações Acadêmicas</h2>
                <div class="obs-card">
                    <form method="get" action="professor/observacoes">
                        <label>Filtrar Aluno:</label>
                        <select name="ra" onchange="this.form.submit()">
                            <option value="">Selecione aluno</option>
                            <% if (listaAlunos != null) { for (Aluno a : listaAlunos) { %>
                            <option value="<%= a.getRa() %>">RA <%= a.getRa() %> - <%= a.getNomeCompleto() %></option>
                            <% } } %>
                        </select>
                    </form>
                </div>

                <% if (alunoSelecionado != null) { %>
                <button class="btn btn-add" onclick="abrirModal('modal-add-obs')">+ NOVA OBSERVAÇÃO</button>
                <div class="obs-container">
                    <%
                        List<Observacoes> listaObservacoes = (List<Observacoes>) request.getAttribute("listaObservacoes");
                        if (listaObservacoes != null && !listaObservacoes.isEmpty()) {
                            for (Observacoes o : listaObservacoes) {
                    %>
                    <div class="obs-card">
                        <p class="obs-descricao"><%= o.getDescricao() %></p>
                        <div class="obs-footer">
                            <div class="obs-meta">
                                <span class="obs-tag"><%= o.getNomeDisciplina() %></span>
                                <span class="obs-date">📅 <%= o.getData() %></span>
                            </div>
                            <div class="obs-actions">
                                <button class="btn btn-edit-small" onclick="editarObs('<%= o.getId() %>', '<%= o.getDescricao().replace("'", "\\'") %>')">✏️ Editar</button>
                                <button class="btn btn-danger-small" onclick="deleteObs('<%= o.getId() %>')">🗑️ Excluir</button>
                            </div>
                        </div>
                    </div>
                    <% } } else { %>
                    <p style="text-align: center; color: #888; margin-top: 20px;">Nenhuma observação encontrada.</p>
                    <% } %>
                </div>
                <% } %>
            </section>
        </div>
    </main>
</div>

<!-- ================= MODAL ADD NOTA ================= -->
<div id="modal-add" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-add')">&times;</span>
        <h3>➕ Nova Nota</h3>
        <form action="professor/notas" method="post">
            <form action="${pageContext.request.contextPath}/notas" method="post">
                <input type="hidden" name="acao" value="inserir">
                <input type="hidden" name="ra" value="<%= alunoSelecionado != null ? alunoSelecionado.getRa() : "" %>">
                <div class="form-group">
                    <label>Disciplina:</label>
                    <select name="disciplinaId" required>
                        <option value="">Selecione...</option>
                        <option value="1">Matemática</option>
                        <option value="2">Português</option>
                        <option value="3">História</option>
                        <option value="4">Ciências</option>
                        <option value="5">Informática</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>N1:</label>
                    <input type="number" name="n1" step="0.01" min="0" max="10" required>
                </div>
                <div class="form-group">
                    <label>N2:</label>
                    <input type="number" name="n2" step="0.01" min="0" max="10" required>
                </div>
                <button type="submit" class="btn btn-primary">Salvar</button>
            </form>
    </div>
</div>

<!-- ================= MODAL EDIT NOTA ================= -->
<div id="modal-edit" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-edit')">&times;</span>
        <h3>✏️ Editar Nota</h3>
        <form action="professor/notas" method="post">
            <form action="${pageContext.request.contextPath}/notas" method="post">
                <input type="hidden" name="acao" value="editar">
                <input type="hidden" name="idBoletim" id="edit-id">
                <input type="hidden" name="ra" value="<%= alunoSelecionado != null ? alunoSelecionado.getRa() : "" %>">
                <div class="form-group">
                    <label>N1:</label>
                    <input type="number" name="n1" id="edit-n1" step="0.01" min="0" max="10" required>
                </div>
                <div class="form-group">
                    <label>N2:</label>
                    <input type="number" name="n2" id="edit-n2" step="0.01" min="0" max="10" required>
                </div>
                <button type="submit" class="btn btn-primary">Atualizar</button>
            </form>
    </div>
</div>

<!-- ================= MODAL ADD OBSERVAÇÃO ================= -->
<div id="modal-add-obs" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-add-obs')">&times;</span>
        <h3>➕ Nova Observação</h3>
        <form action="professor/observacoes" method="post">
            <form action="${pageContext.request.contextPath}/observacoes" method="post">
                <input type="hidden" name="acao" value="inserir">
                <input type="hidden" name="ra" value="<%= alunoSelecionado != null ? alunoSelecionado.getRa() : "" %>">
                <div class="form-group">
                    <label>Disciplina:</label>
                    <select name="disciplinaId" required>
                        <option value="">Selecione...</option>
                        <option value="1">Matemática</option>
                        <option value="2">Português</option>
                        <option value="3">História</option>
                        <option value="4">Ciências</option>
                        <option value="5">Informática</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Descrição:</label>
                    <textarea name="descricao" rows="5" placeholder="Digite a observação..." required></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Salvar</button>
            </form>
    </div>
</div>

<!-- ================= MODAL EDIT OBSERVAÇÃO ================= -->
<div id="modal-edit-obs" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-edit-obs')">&times;</span>
        <h3>✏️ Editar Observação</h3>
        <form action="professor/observacoes" method="post">
            <form action="${pageContext.request.contextPath}/observacoes" method="post">
                <input type="hidden" name="acao" value="editar">
                <input type="hidden" name="id" id="edit-id-obs">
                <input type="hidden" name="ra" value="<%= alunoSelecionado != null ? alunoSelecionado.getRa() : "" %>">
                <div class="form-group">
                    <label>Descrição:</label>
                    <textarea name="descricao" id="edit-descricao-obs" rows="5" required></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Atualizar</button>
            </form>
    </div>
</div>

<!-- ================= MODAL DELETE OBSERVAÇÃO ================= -->
<div id="modal-delete-obs" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-delete-obs')">&times;</span>
        <h3>🗑️ Confirmar Exclusão</h3>
        <p>Tem certeza que deseja excluir esta observação?</p>
        <form action="professor/observacoes" method="post">
            <form action="${pageContext.request.contextPath}/observacoes" method="post">
                <input type="hidden" name="acao" value="excluir">
                <input type="hidden" name="id" id="delete-id-obs">
                <input type="hidden" name="ra" value="<%= alunoSelecionado != null ? alunoSelecionado.getRa() : "" %>">
                <button type="submit" class="btn btn-danger">Sim, Excluir</button>
                <button type="button" class="btn" onclick="fecharModal('modal-delete-obs')">Cancelar</button>
            </form>
    </div>
</div>

<!-- ================= JAVASCRIPT ================= -->
<script>
    // ✅ ABAS - NAVEGAÇÃO
    const buttons = document.querySelectorAll(".nav-btn");
    const sections = document.querySelectorAll(".section");

    buttons.forEach(btn => {
        btn.onclick = function() {
            // Remove active de todos os botões
            buttons.forEach(b => b.classList.remove("active"));
            // Adiciona active no botão clicado
            this.classList.add("active");

            // Esconde todas as seções
            sections.forEach(s => s.classList.remove("active-section"));

            // Mostra a seção correta
            const target = this.dataset.target;
            document.getElementById(target).classList.add("active-section");
        }
    });

    // ✅ MODAIS - ABRIR E FECHAR
    function abrirModal(id) {
        document.getElementById(id).style.display = "block";
    }

    function fecharModal(id) {
        document.getElementById(id).style.display = "none";
    }

    // ✅ FECHAR MODAL AO CLICAR FORA
    window.onclick = function(event) {
        if (event.target.classList.contains("modal")) {
            event.target.style.display = "none";
        }
    }

    // ✅ EDITAR NOTA
    function abrirEditar(id, n1, n2) {
        document.getElementById("edit-id").value = id;
        document.getElementById("edit-n1").value = n1;
        document.getElementById("edit-n2").value = n2;
        abrirModal("modal-edit");
    }

    // ✅ EXCLUIR NOTA
    function confirmarDelete(id) {
        if (confirm("Tem certeza que deseja excluir esta nota?")) {
            location.href = "professor/notas?acao=excluir&id=" + id;
            location.href = "${pageContext.request.contextPath}/notas?acao=excluir&id=" + id;
        }
    }

    // ✅ EDITAR OBSERVAÇÃO
    function editarObs(id, descricao) {
        document.getElementById("edit-id-obs").value = id;
        document.getElementById("edit-descricao-obs").value = descricao;
        abrirModal("modal-edit-obs");
    }

    // ✅ EXCLUIR OBSERVAÇÃO
    function deleteObs(id) {
        if (confirm("Tem certeza que deseja excluir esta observação?")) {
            document.getElementById("delete-id-obs").value = id;
            abrirModal("modal-delete-obs");
        }
    }
</script>
</body>
</html>