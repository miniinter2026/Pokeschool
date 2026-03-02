<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Aluno" %>
<%@ page import="com.example.pokeschool.model.Avaliacao" %>
<html>
<head>
    <title>Notas dos Alunos</title>
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

        select, input {
            padding: 10px;
            font-size: 16px;
            width: 100%;
            box-sizing: border-box;
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
        .btn-edit { background-color: #2196F3; color: white; }
        .btn-danger { background-color: #f44336; color: white; }
        .btn-add { background-color: #4CAF50; color: white; padding: 10px 20px; font-size: 16px; }

        .aprovado { color: green; font-weight: bold; }
        .reprovado { color: red; font-weight: bold; }

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

        .close:hover { color: black; }

        .actions { display: flex; gap: 5px; }
        .no-data { text-align: center; color: #666; font-style: italic; }
    </style>
</head>
<body>
<div class="container">
    <h2>📊 Lançamento de Notas</h2>

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
        List<Avaliacao> listaAvaliacao = (List<Avaliacao>) request.getAttribute("listaAvaliacao");

        if (alunoSelecionado != null) {
    %>

    <h3>👤 Aluno: <%= alunoSelecionado.getNomeCompleto() %> (RA: <%= alunoSelecionado.getRa() %>)</h3>

    <button class="btn btn-add" onclick="abrirModalAdd()">+ Lançar Nota</button>

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
                if (listaAvaliacao != null && !listaAvaliacao.isEmpty()) {
                    for (Avaliacao av : listaAvaliacao) {
                        double media = (av.getN1() + av.getN2()) / 2;
                        String situacao = media >= 7 ? "Aprovado" : "Reprovado";
                        String classe = media >= 7 ? "aprovado" : "reprovado";
            %>
            <tr>
                <td><%= av.getNomeDisciplina() %></td>
                <td><%= av.getN1() %></td>
                <td><%= av.getN2() %></td>
                <td><%= String.format("%.1f", media) %></td>
                <td class="<%= classe %>"><%= situacao %></td>
                <td>
                    <div class="actions">
                        <button class="btn btn-edit" onclick="abrirModalEdit('<%= av.getId() %>', '<%= av.getN1() %>', '<%= av.getN2() %>')">Editar</button>
                        <button class="btn btn-danger" onclick="confirmarDelete('<%= av.getId() %>')">Excluir</button>
                    </div>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="6" class="no-data">Nenhuma nota lançada para este aluno.</td>
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
    <a href="javascript:history.back()">← Voltar</a>
</div>

<!-- MODAL: Adicionar Nova Nota -->
<div id="modal-add" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-add')">&times;</span>
        <h3>➕ Lançar Nota</h3>
        <form action="editarNota" method="post">
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

<!-- MODAL: Editar Nota -->
<div id="modal-edit" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-edit')">&times;</span>
        <h3>✏️ Editar Nota</h3>
        <form action="editarNota" method="post">
            <input type="hidden" name="id" id="edit-id">
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

<!-- MODAL: Confirmar Delete -->
<div id="modal-delete" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-delete')">&times;</span>
        <h3>🗑️ Confirmar Exclusão</h3>
        <p>Tem certeza que deseja excluir esta nota?</p>
        <form action="editarNota" method="post">
            <input type="hidden" name="acao" value="excluir">
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

    function abrirModalAdd() {
        abrirModal('modal-add');
    }

    function abrirModalEdit(id, n1, n2) {
        document.getElementById('edit-id').value = id;
        document.getElementById('edit-n1').value = n1;
        document.getElementById('edit-n2').value = n2;
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