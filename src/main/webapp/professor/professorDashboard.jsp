<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="../error.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Aluno" %>
<%@ page import="com.example.pokeschool.model.Professor" %>
<%@ page import="com.example.pokeschool.model.Avaliacao" %>
<%@ page import="com.example.pokeschool.model.Observacoes" %>
<%@ page import="com.example.pokeschool.dao.AvaliacaoDAO" %>
<%@ page import="com.example.pokeschool.dao.ObservacoesDAO" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>PokeSchool - Dashboard Professor</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Styles/professorDashboard.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .aluno-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 30px;
            margin-bottom: 15px;
            padding-bottom: 8px;
            border-bottom: 3px solid #d80000;
        }
        .aluno-header h3 {
            margin: 0;
            color: #333;
            font-size: 1.2rem;
        }
        .aluno-header .btn-add {
            background-color: #d80000;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        .aluno-header .btn-add:hover {
            background-color: #b30000;
        }
        .obs-card {
            margin-left: 20px;
        }
    </style>
</head>
<body>

<div class="layout">
    <%
        Professor professor = (Professor) session.getAttribute("professor");
        if (professor == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
        String termoBusca = (String) request.getAttribute("termoBusca");
        Integer disciplinaId = (Integer) request.getAttribute("disciplinaId");

        if (disciplinaId == null) {
            disciplinaId = (Integer) session.getAttribute("professorDisciplina");
        }
    %>

    <aside class="sidebar">
        <div class="profile">
            <img src="<%= request.getContextPath() %>/assets/img/ProfesorOak.jpg" alt="Foto">
            <h3><%= professor.getNomeCompleto() %></h3>
        </div>
        <nav>
            <a class="nav-btn active" data-target="dashboard">DASHBOARD</a>
            <a class="nav-btn" data-target="notas">NOTAS</a>
            <a class="nav-btn" data-target="observacoes">OBSERVAÇÕES</a>
            <a class="nav-btn logout" href="<%= request.getContextPath() %>/logout">SAIR</a>
        </nav>
    </aside>

    <main class="content">
        <div class="top-wave"></div>
        <div class="container">

            <!-- DASHBOARD -->
            <section id="dashboard" class="section active-section">
                <h2>Dashboard</h2>
                <div class="obs-card">
                    <p><strong>Bem-vindo(a), </strong><%= professor.getNomeCompleto() %></p>
                    <p><strong>Disciplina:</strong> <%= professor.getNomeDisciplina() %></p>
                </div>

                <div class="filtro-container">
                    <input type="text" id="filtroDashboard" placeholder="Filtrar por nome ou RA..."
                           style="width:100%; padding:10px; margin:10px 0; border:1px solid #ddd; border-radius:5px;">
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
                    <tbody id="dashboard-body">
                        <% if (listaAlunos != null) for (Aluno a : listaAlunos) { %>
                        <tr class="aluno-row" data-nome="<%= a.getNomeCompleto().toLowerCase() %>" data-ra="<%= a.getRa() %>">
                            <td><%= a.getRa() %></td>
                            <td><%= a.getNomeCompleto() %></td>
                            <td><%= a.getEmail() %></td>
                            <td><%= a.getIdSala() %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </section>

            <!-- NOTAS -->
            <section id="notas" class="section">
                <h2>Gerenciamento de Notas</h2>

                <div class="filtro-container">
                    <input type="text" id="filtroNotas" placeholder="Filtrar por nome ou RA..."
                           style="width:100%; padding:10px; margin:10px 0; border:1px solid #ddd; border-radius:5px;">
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>RA</th>
                            <th>Nome</th>
                            <th>N1</th>
                            <th>N2</th>
                            <th>Média</th>
                            <th>Situação</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody id="notas-body">
                        <% if (listaAlunos != null) {
                            AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
                            for (Aluno a : listaAlunos) {
                                List<Avaliacao> notas = avaliacaoDAO.listarPorAlunoERa(a.getRa(), disciplinaId);
                                if (notas != null && !notas.isEmpty()) {
                                    for (Avaliacao av : notas) {
                                        double media = (av.getN1() + av.getN2()) / 2;
                                        String situacao = media >= 7 ? "Aprovado" : "Reprovado";
                                        String classe = media >= 7 ? "aprovado" : "reprovado";
                        %>
                        <tr class="nota-row" data-nome="<%= a.getNomeCompleto().toLowerCase() %>" data-ra="<%= a.getRa() %>">
                            <td><%= a.getRa() %></td>
                            <td><%= a.getNomeCompleto() %></td>
                            <td><%= av.getN1() %></td>
                            <td><%= av.getN2() %></td>
                            <td class="<%= classe %>"><%= String.format("%.1f", media) %></td>
                            <td class="<%= classe %>"><%= situacao %></td>
                            <td>
                                <button class="btn-edit" onclick="abrirEditar('<%= av.getIdBoletim() %>', '<%= av.getN1() %>', '<%= av.getN2() %>')">Editar</button>
                            </td>
                        </tr>
                        <%      }
                                } else { %>
                        <tr class="nota-row" data-nome="<%= a.getNomeCompleto().toLowerCase() %>" data-ra="<%= a.getRa() %>">
                            <td><%= a.getRa() %></td>
                            <td><%= a.getNomeCompleto() %></td>
                            <td colspan="4" style="color:#888;">Sem notas</td>
                            <td><button class="btn-add" onclick="abrirModalNota('<%= a.getRa() %>')">+ Adicionar</button></td>
                        </tr>
                        <%      }
                            }
                        } %>
                    </tbody>
                </table>
            </section>

            <!-- OBSERVAÇÕES -->
            <section id="observacoes" class="section">
                <h2>Observações Acadêmicas</h2>

                <div class="filtro-container">
                    <input type="text" id="filtroObservacoes" placeholder="Filtrar por nome, RA ou texto..."
                           style="width:100%; padding:10px; margin:10px 0; border:1px solid #ddd; border-radius:5px;">
                </div>

                <div class="obs-container" id="observacoes-container">
                    <% if (listaAlunos != null) {
                        ObservacoesDAO obsDAO = new ObservacoesDAO();
                        for (Aluno a : listaAlunos) {
                            List<Observacoes> observacoes = obsDAO.listarPorAlunoERa(a.getRa(), disciplinaId);
                    %>
                    <div class="aluno-header">
                        <h3><%= a.getNomeCompleto() %> (RA: <%= a.getRa() %>)</h3>
                        <button class="btn-add" onclick="abrirModalObs('<%= a.getRa() %>')">Nova Observação</button>
                    </div>

                    <%
                        if (observacoes != null && !observacoes.isEmpty()) {
                            for (Observacoes o : observacoes) {
                    %>
                    <div class="obs-card obs-item"
                         data-nome="<%= a.getNomeCompleto().toLowerCase() %>"
                         data-ra="<%= a.getRa() %>"
                         data-texto="<%= o.getDescricao().toLowerCase() %>">
                        <p class="obs-descricao"><%= o.getDescricao() %></p>
                        <div class="obs-footer">
                            <span class="obs-date"><%= o.getData() %></span>
                            <div>
                                <button class="btn-edit-small" onclick="editarObs('<%= o.getId() %>', '<%= o.getDescricao().replace("'", "\\'") %>', '<%= a.getRa() %>')">Editar</button>
                                <button class="btn-danger-small" onclick="deleteObs('<%= o.getId() %>', '<%= a.getRa() %>')">Excluir</button>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    } else {
                    %>
                    <div class="obs-card">
                        <p style="text-align:center; color:#888;">Nenhuma observação para este aluno</p>
                    </div>
                    <%
                            }
                        }
                    } %>
                </div>
            </section>
        </div>
    </main>
</div>

<!-- MODAIS -->
<div id="modal-add" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-add')">&times;</span>
        <h3>Nova Nota</h3>
        <form action="<%= request.getContextPath() %>/professor/notas" method="post">
            <input type="hidden" name="acao" value="inserir">
            <input type="hidden" name="ra" id="modal-ra">
            <input type="hidden" name="disciplinaId" value="<%= disciplinaId %>">
            <div class="form-group">
                <label>N1:</label>
                <input type="number" name="n1" step="0.01" min="0" max="10" required>
            </div>
            <div class="form-group">
                <label>N2:</label>
                <input type="number" name="n2" step="0.01" min="0" max="10" required>
            </div>
            <button type="submit" class="btn-primary">Salvar</button>
        </form>
    </div>
</div>

<div id="modal-edit" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-edit')">&times;</span>
        <h3>Editar Nota</h3>
        <form action="<%= request.getContextPath() %>/professor/notas" method="post">
            <input type="hidden" name="acao" value="editar">
            <input type="hidden" name="idBoletim" id="edit-id">
            <div class="form-group">
                <label>N1:</label>
                <input type="number" name="n1" id="edit-n1" step="0.01" required>
            </div>
            <div class="form-group">
                <label>N2:</label>
                <input type="number" name="n2" id="edit-n2" step="0.01" required>
            </div>
            <button type="submit" class="btn-primary">Atualizar</button>
        </form>
    </div>
</div>

<div id="modal-add-obs" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-add-obs')">&times;</span>
        <h3>Nova Observação</h3>
        <form action="<%= request.getContextPath() %>/professor/observacoes" method="post">
            <input type="hidden" name="acao" value="inserir">
            <input type="hidden" name="ra" id="modal-obs-ra">
            <textarea name="descricao" rows="5" placeholder="Digite a observação..." required></textarea>
            <button type="submit" class="btn-primary">Salvar</button>
        </form>
    </div>
</div>

<div id="modal-edit-obs" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-edit-obs')">&times;</span>
        <h3>Editar Observação</h3>
        <form action="<%= request.getContextPath() %>/professor/observacoes" method="post">
            <input type="hidden" name="acao" value="editar">
            <input type="hidden" name="id" id="edit-id-obs">
            <input type="hidden" name="ra" id="edit-obs-ra">
            <textarea name="descricao" id="edit-descricao-obs" rows="5" required></textarea>
            <button type="submit" class="btn-primary">Atualizar</button>
        </form>
    </div>
</div>

<div id="modal-delete-obs" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-delete-obs')">&times;</span>
        <h3>Confirmar Exclusão</h3>
        <form action="<%= request.getContextPath() %>/professor/observacoes" method="post">
            <input type="hidden" name="acao" value="excluir">
            <input type="hidden" name="id" id="delete-id-obs">
            <input type="hidden" name="ra" id="delete-obs-ra">
            <button type="submit" class="btn-danger">Excluir</button>
            <button type="button" class="btn-primary" onclick="fecharModal('modal-delete-obs')">Cancelar</button>
        </form>
    </div>
</div>

<!-- JAVASCRIPT -->
<script>
    // Navegação por abas
    document.querySelectorAll(".nav-btn").forEach(btn => {
        btn.onclick = function() {
            document.querySelectorAll(".nav-btn").forEach(b => b.classList.remove("active"));
            this.classList.add("active");
            document.querySelectorAll(".section").forEach(s => s.classList.remove("active-section"));
            document.getElementById(this.dataset.target).classList.add("active-section");
        };
    });

    // Modais
    function abrirModal(id) { document.getElementById(id).style.display = "block"; }
    function fecharModal(id) { document.getElementById(id).style.display = "none"; }
    window.onclick = e => { if (e.target.classList.contains("modal")) e.target.style.display = "none"; };

    function abrirModalNota(ra) { document.getElementById("modal-ra").value = ra; abrirModal("modal-add"); }
    function abrirModalObs(ra) { document.getElementById("modal-obs-ra").value = ra; abrirModal("modal-add-obs"); }

    function abrirEditar(id, n1, n2) {
        document.getElementById("edit-id").value = id;
        document.getElementById("edit-n1").value = n1;
        document.getElementById("edit-n2").value = n2;
        abrirModal("modal-edit");
    }

    function editarObs(id, desc, ra) {
        document.getElementById("edit-id-obs").value = id;
        document.getElementById("edit-descricao-obs").value = desc;
        document.getElementById("edit-obs-ra").value = ra;
        abrirModal("modal-edit-obs");
    }

    function deleteObs(id, ra) {
        document.getElementById("delete-id-obs").value = id;
        document.getElementById("delete-obs-ra").value = ra;
        abrirModal("modal-delete-obs");
    }

    // FILTROS
    document.getElementById("filtroDashboard")?.addEventListener("input", function() {
        let termo = this.value.toLowerCase();
        document.querySelectorAll("#dashboard-body .aluno-row").forEach(linha => {
            let nome = (linha.dataset.nome || "").toLowerCase();
            let ra = (linha.dataset.ra || "").toLowerCase();
            linha.style.display = (nome.includes(termo) || ra.includes(termo)) ? "" : "none";
        });
    });

    document.getElementById("filtroNotas")?.addEventListener("input", function() {
        let termo = this.value.toLowerCase();
        document.querySelectorAll("#notas-body .nota-row").forEach(linha => {
            let nome = (linha.dataset.nome || "").toLowerCase();
            let ra = (linha.dataset.ra || "").toLowerCase();
            linha.style.display = (nome.includes(termo) || ra.includes(termo)) ? "" : "none";
        });
    });

    // FILTRO DE OBSERVAÇÕES CORRIGIDO
    document.getElementById("filtroObservacoes")?.addEventListener("input", function() {
        let termo = this.value.toLowerCase().trim();

        document.querySelectorAll(".obs-item").forEach(card => {
            let nome = (card.getAttribute("data-nome") || "").toLowerCase();
            let ra = (card.getAttribute("data-ra") || "").toLowerCase();
            let texto = (card.getAttribute("data-texto") || "").toLowerCase();

            if (termo === "") {
                card.style.display = "";
            } else if (nome.includes(termo) || ra.includes(termo) || texto.includes(termo)) {
                card.style.display = "";
            } else {
                card.style.display = "none";
            }
        });

        // Também esconde cabeçalhos de alunos sem observações visíveis
        document.querySelectorAll(".aluno-header").forEach(header => {
            let alunoRa = header.querySelector("h3").textContent.match(/RA: (\d+)/)?.[1];
            if (alunoRa) {
                let temObsVisivel = false;
                document.querySelectorAll(`.obs-item[data-ra="${alunoRa}"]`).forEach(card => {
                    if (card.style.display !== "none") temObsVisivel = true;
                });
                header.style.display = temObsVisivel ? "flex" : "none";
            }
        });
    });
</script>

</body>
</html>