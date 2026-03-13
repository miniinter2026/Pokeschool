<%@ page contentType="text/html;charset=UTF-8" import="java.util.List, com.example.pokeschool.model.Aluno, com.example.pokeschool.model.Professor" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>PokeSchool | Admin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Styles/admDashboard.css">
</head>
<body>

<div class="layout">
    <div class="sidebar">
        <div class="profile">
            <img src="<%= request.getContextPath() %>/assets/img/arceus.jpg" alt="Admin">
            <h3>ADMIN</h3>
        </div>
        <nav>
            <!-- MUDANÇA 1: Removido href e adicionado onclick para evitar recarregar -->
            <a href="javascript:void(0)" class="nav-btn active" onclick="mostrarSecao('alunos', this)">ALUNOS</a>
            <a href="javascript:void(0)" class="nav-btn" onclick="mostrarSecao('professores', this)">PROFESSORES</a>
            <a class="nav-btn logout" href="<%= request.getContextPath() %>/logout">SAIR</a>
        </nav>
    </div>

    <div class="content">
        <div class="top-wave"></div>
        <div class="container">

            <!-- SEÇÃO ALUNOS -->
            <div id="secao-alunos" class="section active-section">
                <div class="titulo-add">
                    <h2>Gerenciar Alunos</h2>
                    <button class="btn-add" onclick="abrirModalAddAluno()">Novo Aluno</button>
                </div>
                <input type="text" id="buscaAluno" placeholder="Buscar por nome ou RA..." onkeyup="buscarAluno()">

                <table id="tabelaAlunos">
                    <thead>
                    <tr>
                        <th>RA</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Sala</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
                        if (listaAlunos != null && !listaAlunos.isEmpty()) {
                            for (Aluno a : listaAlunos) {
                    %>
                    <tr>
                        <td><%= a.getRa() %></td>
                        <td><%= a.getNomeCompleto() %></td>
                        <td><%= a.getEmail() %></td>
                        <td><%= a.getIdSala() %></td>
                        <td>
                            <div class="actions">
                                <button class="btn btn-edit"
                                    data-ra="<%= a.getRa() %>"
                                    data-nome="<%= a.getNomeCompleto().replace("'", "\\'") %>"
                                    data-email="<%= a.getEmail().replace("'", "\\'") %>"
                                    data-sala="<%= a.getIdSala() %>"
                                    onclick="abrirModalEditAluno(this)">Editar</button>
                                <button class="btn btn-danger"
                                    data-ra="<%= a.getRa() %>"
                                    onclick="confirmarDeleteAluno(this)">Excluir</button>
                            </div>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" style="text-align: center; color: #666;">Nenhum aluno cadastrado.</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>

            <!-- SEÇÃO PROFESSORES -->
            <div id="secao-professores" class="section">
                <div class="titulo-add">
                    <h2>Gerenciar Professores</h2>
                    <button class="btn-add" onclick="abrirModalAddProfessor()">Novo Professor</button>
                </div>
                <input type="text" id="buscaProfessor" placeholder="Buscar por nome..." onkeyup="buscarProfessor()">

                <table id="tabelaProfessores">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Usuário</th>
                        <th>Email</th>
                        <th>Disciplina</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Professor> listaProfessores = (List<Professor>) request.getAttribute("listaProfessores");
                        if (listaProfessores != null && !listaProfessores.isEmpty()) {
                            for (Professor p : listaProfessores) {
                    %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getNomeCompleto() %></td>
                        <td><%= p.getNomeUsuario() %></td>
                        <td><%= p.getEmail() %></td>
                        <td><%= p.getNomeDisciplina() != null ? p.getNomeDisciplina() : "Não definida" %></td>
                        <td>
                            <div class="actions">
                                <button class="btn btn-edit"
                                    data-id="<%= p.getId() %>"
                                    data-nome="<%= p.getNomeCompleto().replace("'", "\\'") %>"
                                    data-usuario="<%= p.getNomeUsuario().replace("'", "\\'") %>"
                                    data-email="<%= p.getEmail().replace("'", "\\'") %>"
                                    data-disciplina="<%= p.getIdDisciplina() %>"
                                    onclick="abrirModalEditProfessor(this)">Editar</button>
                                <button class="btn btn-danger"
                                    data-id="<%= p.getId() %>"
                                    onclick="confirmarDeleteProfessor(this)">Excluir</button>
                            </div>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6" style="text-align: center; color: #666;">Nenhum professor cadastrado.</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

<!-- MODAIS ALUNOS -->
<div id="modal-add-aluno" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-add-aluno')">&times;</span>
        <h3>Novo Aluno</h3>
        <form action="adminAlunos" method="post">
            <input type="hidden" name="acao" value="inserir">
            <div class="form-group">
                <label>RA:</label>
                <input type="number" name="ra" required>
            </div>
            <div class="form-group">
                <label>Nome Completo:</label>
                <input type="text" name="nome" required>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" required>
            </div>
            <div class="form-group">
                <label>Senha:</label>
                <input type="password" name="senha" required>
            </div>
            <div class="form-group">
                <label>Sala:</label>
                <select name="sala" required>
                    <option value="">Selecione...</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Salvar</button>
        </form>
    </div>
</div>

<div id="modal-edit-aluno" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-edit-aluno')">&times;</span>
        <h3>Editar Aluno</h3>
        <form action="adminAlunos" method="post">
            <input type="hidden" name="acao" value="editar">
            <div class="form-group">
                <label>RA:</label>
                <input type="number" name="ra" id="edit-ra" required>
            </div>
            <div class="form-group">
                <label>Nome Completo:</label>
                <input type="text" name="nome" id="edit-nome" required>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" id="edit-email" required>
            </div>
            <div class="form-group">
                <label>Senha (deixe em branco para manter):</label>
                <input type="password" name="senha" id="edit-senha" placeholder="********">
            </div>
            <div class="form-group">
                <label>Sala:</label>
                <select name="sala" id="edit-sala" required>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Atualizar</button>
        </form>
    </div>
</div>

<div id="modal-delete-aluno" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-delete-aluno')">&times;</span>
        <h3>Confirmar Exclusão</h3>
        <div class="msg-confirmar">
            <p>Tem certeza que deseja excluir este aluno?</p>
        </div>
        <form action="adminAlunos" method="post">
            <input type="hidden" name="acao" value="excluir">
            <input type="hidden" name="ra" id="delete-ra">
            <button type="submit" class="btn btn-danger">Sim, Excluir</button>
            <button type="button" class="btn" onclick="fecharModal('modal-delete-aluno')">Cancelar</button>
        </form>
    </div>
</div>

<!-- MODAIS PROFESSORES -->
<div id="modal-add-professor" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-add-professor')">&times;</span>
        <h3>Novo Professor</h3>
        <form action="adminProfessores" method="post">
            <input type="hidden" name="acao" value="inserir">
            <div class="form-group">
                <label>Nome Completo:</label>
                <input type="text" name="nome" required>
            </div>
            <div class="form-group">
                <label>Usuário:</label>
                <input type="text" name="usuario" required>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" required>
            </div>
            <div class="form-group">
                <label>Senha:</label>
                <input type="password" name="senha" required>
            </div>
            <div class="form-group">
                <label>Disciplina:</label>
                <select name="disciplina" required>
                    <option value="">Selecione...</option>
                    <option value="1">Matemática</option>
                    <option value="2">Português</option>
                    <option value="3">História</option>
                    <option value="4">Ciências</option>
                    <option value="5">Informática</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Salvar</button>
        </form>
    </div>
</div>

<div id="modal-edit-professor" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-edit-professor')">&times;</span>
        <h3>Editar Professor</h3>
        <form action="adminProfessores" method="post">
            <input type="hidden" name="acao" value="editar">
            <div class="form-group">
                <label>Nome Completo:</label>
                <input type="text" name="nome" id="edit-prof-nome" required >
            </div>
            <div class="form-group">
                <label>Usuário:</label>
                <input type="text" name="usuario" id="edit-usuario" required>
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" id="edit-prof-email" required>
            </div>
            <div class="form-group">
                <label>Senha (deixe em branco para manter):</label>
                <input type="password" name="senha" id="edit-prof-senha" placeholder="******">
            </div>
            <div class="form-group">
                <label>Disciplina:</label>
                <select name="disciplina" id="edit-disciplina" required>
                    <option value="1">Matemática</option>
                    <option value="2">Português</option>
                    <option value="3">História</option>
                    <option value="4">Ciências</option>
                    <option value="5">Informática</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Atualizar</button>
        </form>
    </div>
</div>

<div id="modal-delete-professor" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-delete-professor')">&times;</span>
        <h3>Confirmar Exclusão</h3>
        <div class="msg-confirmar">
            <p>Tem certeza que deseja excluir este aluno?</p>
        </div>
        <form action="adminProfessores" method="post">
            <input type="hidden" name="acao" value="excluir">
            <input type="hidden" name="id" id="delete-id">
            <button type="submit" class="btn btn-danger">Sim, Excluir</button>
            <button type="button" class="btn" onclick="fecharModal('modal-delete-professor')">Cancelar</button>
        </form>
    </div>
</div>

<script>
    // MUDANÇA 2: Função melhorada para mostrar seções sem recarregar
    function mostrarSecao(secao, element) {
        // Prevenir comportamento padrão se for evento
        if (event) event.preventDefault();

        // Remover active de todos os botões
        document.querySelectorAll('.nav-btn').forEach(btn => {
            btn.classList.remove('active');
        });

        // Adicionar active no botão clicado
        element.classList.add('active');

        // Esconder todas as seções
        document.querySelectorAll('.section').forEach(sec => {
            sec.classList.remove('active-section');
        });

        // Mostrar a seção selecionada
        document.getElementById('secao-' + secao).classList.add('active-section');

        // Opcional: atualizar a URL sem recarregar
        history.pushState({}, '', '#' + secao);
    }

    // MUDANÇA 3: Inicializar baseado na URL ou mostrar alunos por padrão
    window.onload = function() {
        // Verificar se há hash na URL
        const hash = window.location.hash.substring(1);
        if (hash === 'professores') {
            const professorBtn = document.querySelector('[onclick*="professores"]');
            if (professorBtn) mostrarSecao('professores', professorBtn);
        } else {
            const alunoBtn = document.querySelector('[onclick*="alunos"]');
            if (alunoBtn) mostrarSecao('alunos', alunoBtn);
        }
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

    function fecharModal(id) {
        document.getElementById(id).style.display = 'none';
    }

    function abrirModalAddAluno() {
        fecharModal('modal-edit-aluno');
        fecharModal('modal-delete-aluno');
        document.getElementById('modal-add-aluno').style.display = 'block';
    }

    function abrirModalEditAluno(btn) {
        fecharModal('modal-add-aluno');
        fecharModal('modal-delete-aluno');

        const ra = btn.getAttribute('data-ra');
        const nome = btn.getAttribute('data-nome');
        const email = btn.getAttribute('data-email');
        const sala = btn.getAttribute('data-sala');

        document.getElementById('edit-ra').value = ra;
        document.getElementById('edit-nome').value = nome;
        document.getElementById('edit-email').value = email;
        document.getElementById('edit-sala').value = sala;
        document.getElementById('edit-senha').value = '';
        document.getElementById('modal-edit-aluno').style.display = 'block';
    }

    function confirmarDeleteAluno(btn) {
        fecharModal('modal-add-aluno');
        fecharModal('modal-edit-aluno');

        const ra = btn.getAttribute('data-ra');
        document.getElementById('delete-ra').value = ra;
        document.getElementById('modal-delete-aluno').style.display = 'block';
    }

    function abrirModalAddProfessor() {
        fecharModal('modal-edit-professor');
        fecharModal('modal-delete-professor');
        document.getElementById('modal-add-professor').style.display = 'block';
    }

    function abrirModalEditProfessor(btn) {
        fecharModal('modal-add-professor');
        fecharModal('modal-delete-professor');

        const nome = btn.getAttribute('data-nome');
        const usuario = btn.getAttribute('data-usuario');
        const email = btn.getAttribute('data-email');
        const disciplina = btn.getAttribute('data-disciplina');

        document.getElementById('edit-prof-nome').value = nome;
        document.getElementById('edit-usuario').value = usuario;
        document.getElementById('edit-prof-email').value = email;
        document.getElementById('edit-disciplina').value = disciplina;
        document.getElementById('edit-prof-senha').value = '';
        document.getElementById('modal-edit-professor').style.display = 'block';
    }

    function confirmarDeleteProfessor(btn) {
        fecharModal('modal-add-professor');
        fecharModal('modal-edit-professor');

        const id = btn.getAttribute('data-id');
        document.getElementById('delete-id').value = id;
        document.getElementById('modal-delete-professor').style.display = 'block';
    }

    // Fechar modal ao clicar fora
    window.onclick = function(event) {
        if (event.target.className === 'modal') {
            event.target.style.display = 'none';
        }
    }
</script>
</body>
</html>