<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Painel do Professor</title>
</head>
<body>

<div class="header">
  <h2>Bem-vindo, ${nome}!</h2>
</div>

<div class="container">
  <div class="card">
    <h3>Seus Dados</h3>
    <p><strong>Nome:</strong> ${nome}</p>
    <p><strong>Registro:</strong> ${registro}</p>
    <p><strong>Disciplina:</strong> ${disciplina}</p>
  </div>

  <div class="card">
    <h3>Área do Professor</h3>
    <p>Aqui você pode gerenciar suas turmas e conteúdos.</p>

    <button onclick="window.location.href='${pageContext.request.contextPath}/loginPage'">
      Sair
    </button>
  </div>
</div>

</body>
</html>