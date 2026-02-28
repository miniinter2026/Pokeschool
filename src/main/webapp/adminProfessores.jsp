<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Professor" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Professores - PokeSchool</title>
    <style>
        * { box-sizing: border-box; }

        body {
            font-family: Arial, sans-serif;
            padding: 20px;
            background-color: #f5f5f5;
            margin: 0;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        .header {
            background-color: #673AB7;
            color: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .nav-links {
            display: flex;
            gap: 10px;
        }

        .nav-link {
            background-color: rgba(255,255,255,0.2);
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
        }

        .nav-link.active {
            background-color: white;
            color: #673AB7;
        }

        .card {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        h2 { color: #333; margin-top: 0; }

        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }

        input, select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .password-container {
            position: relative;
        }

        .password-container input {
            padding-right: 40px;
        }

        .toggle-password {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            font-size: 18px;
            color: #666;
        }

        .btn {
            padding: 10px 20px;
            cursor: pointer;
            border: none;
            border-radius: 4px;
            font-size: 14px;
        }

        .btn-primary { background-color: #673AB7; color: white; }
        .btn-edit { background-color: #2196F3; color: white; }
        .btn-danger { background-color: #f44336; color: white; }
        .btn-add { background-color: #673AB7; color: white; padding: 12px 24px; font-size: 16px; }

        .search-box {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }

        .search-box input { flex: 1; }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th { background-color: #673AB7; color: white; }
        tr:nth-child(even) { background-color: #f9f9f9; }

        .actions { display: flex; gap: 5px; }

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
            margin: 5% auto;
            padding: 30px;
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

        .form-row { display: flex; gap: 15px; }
        .form-row .form-group { flex: 1; }

        .no-data {
            text-align: center;
            color: #666;
            padding: 20px;
            font-style: italic;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>📚 PokeSchool - Admin</h1>
        <div class="nav-links">
            <a href="adminAlunos" class="nav-link">Alunos</a>
            <a href="adminProfessores" class="nav-link active">Professores</a>
            <a href="logout" class="nav-link">Sair</a>
        </div>
    </div>

    <div class="card">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h2>👨‍🏫 Gerenciar Professores</h2>
            <button class="btn btn-add" onclick="abrirModalAdd()">+ Novo Professor</button>
        </div>

        <div class="search-box">
            <input type="text" id="busca" placeholder="Buscar por nome..." onkeyup="buscarProfessor()">
        </div>

        <table id="tabelaProfessores">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Usuário</th>
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
                    <td><%= p.getNomeDisciplina() != null ? p.getNomeDisciplina() : "Não definida" %></td>
                    <td>
                        <div class="actions">
                            <button class="btn btn-edit" onclick="abrirModalEdit('<%= p.getId() %>', '<%= p.getNomeCompleto() %>', '<%= p.getNomeUsuario() %>', '<%= p.getIdDisciplina() %>')">Editar</button>
                            <button class="btn btn-danger" onclick="confirmarDelete('<%= p.getId() %>')">Excluir</button>
                        </div>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="no-data">Nenhum professor cadastrado.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<!-- MODAL: Adicionar/Editar Professor -->
<div id="modal-professor" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-professor')">&times;</span>
        <h3 id="modal-title">Novo Professor</h3>
        <form action="adminProfessor" method="post" id="form-professor">
            <input type="hidden" name="acao" id="acao" value="inserir">
            <input type="hidden" name="idOriginal" id="idOriginal">

            <div class="form-group">
                <label>Nome Completo:</label>
                <input type="text" name="nome" id="nome" required>
            </div>

            <div class="form-group">
                <label>Usuário:</label>
                <input type="text" name="usuario" id="usuario" required>
            </div>

            <div class="form-group">
                <label>Senha:</label>
                <div class="password-container">
                    <input type="password" name="senha" id="senha" required>
                    <span class="toggle-password" onclick="togglePassword('senha')">👁️</span>
                </div>
            </div>

            <div class="form-group">
                <label>Disciplina:</label>
                <select name="disciplina" id="disciplina" required>
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

<!-- MODAL: Confirmar Delete -->
<div id="modal-delete" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-delete')">&times;</span>
        <h3>🗑️ Confirmar Exclusão</h3>
        <p>Tem certeza que deseja excluir este professor?</p>
        <form action="adminProfessor" method="post">
            <input type="hidden" name="acao" value="excluir">
            <input type="hidden" name="id" id="delete-id">
            <button type="submit" class="btn btn-danger">Sim, Excluir</button>
            <button type="button" class="btn" onclick="fecharModal('modal-delete')">Cancelar</button>
        </form>
    </div>
</div>

<script>
    function abrirModalAdd() {
        document.getElementById('modal-title').innerText = 'Novo Professor';
        document.getElementById('acao').value = 'inserir';
        document.getElementById('form-professor').reset();
        document.getElementById('modal-professor').style.display = 'block';
    }

    function abrirModalEdit(id, nome, usuario, disciplina) {
        document.getElementById('modal-title').innerText = 'Editar Professor';
        document.getElementById('acao').value = 'editar';
        document.getElementById('idOriginal').value = id;
        document.getElementById('nome').value = nome;
        document.getElementById('usuario').value = usuario;
        document.getElementById('disciplina').value = disciplina;
        document.getElementById('senha').required = false;
        document.getElementById('modal-professor').style.display = 'block';
    }

    function confirmarDelete(id) {
        document.getElementById('delete-id').value = id;
        document.getElementById('modal-delete').style.display = 'block';
    }

    function fecharModal(id) {
        document.getElementById(id).style.display = 'none';
    }

    function togglePassword(fieldId) {
        var field = document.getElementById(fieldId);
        if (field.type === "password") {
            field.type = "text";
        } else {
            field.type = "password";
        }
    }

    function buscarProfessor() {
        var input = document.getElementById('busca');
        var filter = input.value.toUpperCase();
        var table = document.getElementById('tabelaProfessores');
        var tr = table.getElementsByTagName('tr');

        for (var i = 1; i < tr.length; i++) {
            var tdNome = tr[i].getElementsByTagName('td')[1];
            if (tdNome) {
                var textNome = tdNome.textContent || tdNome.innerText;
                if (textNome.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = '';
                } else {
                    tr[i].style.display = 'none';
                }
            }
        }
    }

    window.onclick = function(event) {
        if (event.target.className === 'modal') {
            event.target.style.display = 'none';
        }
    }
</script>
</body>
</html>