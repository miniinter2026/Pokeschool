<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Aluno" %>
<%@ page import="com.example.pokeschool.model.Observacoes" %>
<html>
<head>
    <title>Observações dos Alunos</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
            background-color: #f5f5f5;
        }

        .container {
            max-width: 1000px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        h2 { color: #333; }
        h3 { color: #555; margin-top: 30px; }

        .form-group { margin-bottom: 20px; }

        label { display: block; margin-bottom: 8px; font-weight: bold; }

        select {
            padding: 10px;
            font-size: 16px;
            width: 100%;
            max-width: 400px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th { background-color: #4CAF50; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }

        .btn {
            padding: 8px 16px;
            cursor: pointer;
            border: none;
            border-radius: 4px;
            font-size: 14px;
        }

        .btn-primary { background-color: #4CAF50; color: white; }
        .btn-danger { background-color: #f44336; color: white; }
        .btn-edit { background-color: #2196F3; color: white; }
        .btn-add { background-color: #4CAF50; color: white; padding: 10px 20px; font-size: 16px; }

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 10% auto;
            padding: 25px;
            border: 1px solid #888;
            width: 60%;
            max-width: 500px;
            border-radius: 8px;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .form-group input, .form-group textarea, .form-group select {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .actions { display: flex; gap: 5px; }
        .no-data { text-align: center; color: #666; font-style: italic; }
    </style>
</head>
<body>
<div class="container">
    <h2>📋 Observações dos Alunos</h2>

    <!-- Dropdown de Alunos -->
    <div class="form-group">
        <label for="alunoSelect">Selecione o Aluno:</label>
        <form id="formAluno" method="get">
            <select name="ra" id="alunoSelect" onchange="this.form.submit()">
                <option value="">-- Selecione um Aluno --</option>
                <%
                    List<Aluno> listaAlunos = (List<Aluno>) request.getAttribute("listaAlunos");
                    if (listaAlunos != null) {
                        String raSelecionado = (String) request.getAttribute("raSelecionado");
                        for (Aluno a : listaAlunos) {
                            String selected = "";
                            if (raSelecionado != null && raSelecionado.equals(String.valueOf(a.getRa()))) {
                                selected = "selected";
                            }
                %>
                <option value="<%= a.getRa() %>" <%= selected %>>
                    RA <%= a.getRa() %> - <%= a.getNomeCompleto() %>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </form>
    </div>

    <%
        Aluno alunoSelecionado = (Aluno) request.getAttribute("alunoSelecionado");
        List<Observacoes> listaObservacoes = (List<Observacoes>) request.getAttribute("listaObservacoes");

        if (alunoSelecionado != null) {
    %>

    <h3>👤 Aluno: <%= alunoSelecionado.getNomeCompleto() %> (RA: <%= alunoSelecionado.getRa() %>)</h3>

    <button class="btn btn-add" onclick="abrirModal('modal-add')">+ Nova Observação</button>

    <table>
        <thead>
            <tr>
                <th>Data</th>
                <th>Disciplina</th>
                <th>Professor</th>
                <th>Descrição</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (listaObservacoes != null && !listaObservacoes.isEmpty()) {
                    for (Observacoes o : listaObservacoes) {
            %>
            <tr>
                <td><%= o.getData() %></td>
                <td><%= o.getNomeDisciplina() %></td>
                <td><%= o.getNomeProfessor() %></td>
                <td><%= o.getDescricao() %></td>
                <td>
                    <div class="actions">
                        <button class="btn btn-edit" onclick="abrirEditar('<%= o.getId() %>', '<%= o.getDescricao().replace("'", "\\'") %>')">Editar</button>
                        <button class="btn btn-danger" onclick="confirmarDelete('<%= o.getId() %>')">Excluir</button>
                    </div>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="5" class="no-data">Nenhuma observação encontrada para este aluno.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <%
        }
    %>

    <br><br>
    <a href="dashboardProfessor.jsp" style="color: #4CAF50;">← Voltar ao Dashboard</a>
</div>

<!-- MODAL: Adicionar Nova Observação -->
<div id="modal-add" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-add')">&times;</span>
        <h3>➕ Nova Observação</h3>
        <form action="editarObservacao" method="post">
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

<!-- MODAL: Editar Observação -->
<div id="modal-edit" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-edit')">&times;</span>
        <h3>✏️ Editar Observação</h3>
        <form action="editarObservacao" method="post">
            <input type="hidden" name="id" id="edit-id">
            <input type="hidden" name="ra" value="<%= alunoSelecionado != null ? alunoSelecionado.getRa() : "" %>">

            <div class="form-group">
                <label>Descrição:</label>
                <textarea name="descricao" id="edit-descricao" rows="5" required></textarea>
            </div>

            <button type="submit" class="btn btn-primary">Atualizar</button>
        </form>
    </div>
</div>

<!-- MODAL: Confirmar Delete -->
<div id="modal-delete" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-delete')">&times;</span>
        <h3>🗑️ Confirmar Exclusão</h3>
        <p>Tem certeza que deseja excluir esta observação?</p>
        <form action="deletarObservacao" method="post">
            <input type="hidden" name="id" id="delete-id">
            <input type="hidden" name="ra" value="<%= alunoSelecionado != null ? alunoSelecionado.getRa() : "" %>">
            <button type="submit" class="btn btn-danger">Sim, Excluir</button>
            <button type="button" class="btn" onclick="fecharModal('modal-delete')">Cancelar</button>
        </form>
    </div>
</div>

<script>
    function abrirModal(id) {
        document.getElementById(id).style.display = 'block';
    }

    function fecharModal(id) {
        document.getElementById(id).style.display = 'none';
    }

    function abrirEditar(id, descricao) {
        document.getElementById('edit-id').value = id;
        document.getElementById('edit-descricao').value = descricao;
        abrirModal('modal-edit');
    }

    function confirmarDelete(id) {
        document.getElementById('delete-id').value = id;
        abrirModal('modal-delete');
    }

    window.onclick = function(event) {
        if (event.target.className === 'modal') {
            event.target.style.display = 'none';
        }
    }
</script>

</body>
</html>