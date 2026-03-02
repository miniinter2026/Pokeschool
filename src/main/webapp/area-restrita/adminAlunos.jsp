<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.pokeschool.model.Aluno" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Alunos - PokeSchool</title>
    <style>
        * { box-sizing: border-box; }
        body { font-family: Arial, sans-serif; padding: 20px; background-color: #f5f5f5; margin: 0; }
        .container { max-width: 1200px; margin: 0 auto; }
        .header { background-color: #673AB7; color: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center; }
        .nav-links { display: flex; gap: 10px; }
        .nav-link { background-color: rgba(255,255,255,0.2); color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; }
        .nav-link.active { background-color: white; color: #673AB7; }
        .card { background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h2 { color: #333; margin-top: 0; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; }
        .password-container { position: relative; }
        .password-container input { padding-right: 40px; }
        .toggle-password { position: absolute; right: 10px; top: 50%; transform: translateY(-50%); cursor: pointer; font-size: 18px; color: #666; }
        .btn { padding: 10px 20px; cursor: pointer; border: none; border-radius: 4px; font-size: 14px; }
        .btn-primary { background-color: #673AB7; color: white; }
        .btn-edit { background-color: #2196F3; color: white; }
        .btn-danger { background-color: #f44336; color: white; }
        .btn-add { background-color: #673AB7; color: white; padding: 12px 24px; font-size: 16px; }
        .search-box { display: flex; gap: 10px; margin-bottom: 20px; }
        .search-box input { flex: 1; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #673AB7; color: white; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .actions { display: flex; gap: 5px; }
        .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); }
        .modal-content { background-color: #fefefe; margin: 5% auto; padding: 30px; border: 1px solid #888; width: 60%; max-width: 500px; border-radius: 8px; }
        .close { color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer; }
        .close:hover { color: black; }
        .form-row { display: flex; gap: 15px; }
        .form-row .form-group { flex: 1; }
        .no-data { text-align: center; color: #666; padding: 20px; font-style: italic; }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>PokeSchool - Admin</h1>
        <div class="nav-links">
            <a href="adminAlunos" class="nav-link active">Alunos</a>
            <a href="adminProfessores" class="nav-link">Professores</a>
            <a href="/login.jsp" class="nav-link">Sair</a>
        </div>
    </div>

    <div class="card">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h2> Gerenciar Alunos</h2>
            <button class="btn btn-add" onclick="abrirModalAdd()">+ Novo Aluno</button>
        </div>

        <div class="search-box">
            <input type="text" id="busca" placeholder="Buscar por nome ou RA..." onkeyup="buscarAluno()">
        </div>

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
                            <button class="btn btn-edit" onclick="abrirModalEdit('<%= a.getRa() %>', '<%= a.getNomeCompleto() %>', '<%= a.getEmail() %>', '<%= a.getIdSala() %>')">Editar</button>
                            <button class="btn btn-danger" onclick="confirmarDelete('<%= a.getRa() %>')">Excluir</button>
                        </div>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="no-data">Nenhum aluno cadastrado.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<!-- MODAL: Adicionar/Editar Aluno -->
<div id="modal-aluno" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal('modal-aluno')">&times;</span>
        <h3 id="modal-title">Novo Aluno</h3>
        <form action="adminAlunos" method="post" id="form-aluno">
            <input type="hidden" name="acao" id="acao" value="inserir">
            <input type="hidden" name="raOriginal" id="raOriginal">

            <div class="form-row">
                <div class="form-group">
                    <label>RA:</label>
                    <input type="number" name="ra" id="ra" required>
                </div>
                <div class="form-group">
                    <label>Sala:</label>
                    <select name="sala" id="sala" required>
                        <option value="">Selecione...</option>
                        <option value="1">1K</option>
                        <option value="2">2J</option>
                        <option value="3">3H</option>
                        <option value="4">4S</option>
                        <option value="5">5U</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label>Nome Completo:</label>
                <input type="text" name="nome" id="nome" required>
            </div>

            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" id="email" required>
            </div>

            <div class="form-group">
                <label>Senha:</label>
                <div class="password-container">
                    <input type="password" name="senha" id="senha" required>
                    <span class="toggle-password" onclick="togglePassword('senha')">👁️</span>
                </div>
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
        <p>Tem certeza que deseja excluir este aluno?</p>
        <form action="adminAlunos" method="post">
            <input type="hidden" name="acao" value="excluir">
            <input type="hidden" name="ra" id="delete-ra">
            <button type="submit" class="btn btn-danger">Sim, Excluir</button>
            <button type="button" class="btn" onclick="fecharModal('modal-delete')">Cancelar</button>
        </form>
    </div>
</div>

<script>
    function abrirModalAdd() {
        document.getElementById('modal-title').innerText = 'Novo Aluno';
        document.getElementById('acao').value = 'inserir';
        document.getElementById('form-aluno').reset();
        document.getElementById('modal-aluno').style.display = 'block';
    }

    function abrirModalEdit(ra, nome, email, sala) {
        document.getElementById('modal-title').innerText = 'Editar Aluno';
        document.getElementById('acao').value = 'editar';
        document.getElementById('raOriginal').value = ra;
        document.getElementById('ra').value = ra;
        document.getElementById('nome').value = nome;
        document.getElementById('email').value = email;
        document.getElementById('sala').value = sala;
        document.getElementById('senha').required = false;
        document.getElementById('modal-aluno').style.display = 'block';
    }

    function confirmarDelete(ra) {
        document.getElementById('delete-ra').value = ra;
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

    function buscarAluno() {
        var input = document.getElementById('busca');
        var filter = input.value.toUpperCase();
        var table = document.getElementById('tabelaAlunos');
        var tr = table.getElementsByTagName('tr');

        for (var i = 1; i < tr.length; i++) {
            var tdRa = tr[i].getElementsByTagName('td')[0];
            var tdNome = tr[i].getElementsByTagName('td')[1];
            if (tdRa || tdNome) {
                var textRa = tdRa.textContent || tdRa.innerText;
                var textNome = tdNome.textContent || tdNome.innerText;
                if (textRa.toUpperCase().indexOf(filter) > -1 || textNome.toUpperCase().indexOf(filter) > -1) {
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