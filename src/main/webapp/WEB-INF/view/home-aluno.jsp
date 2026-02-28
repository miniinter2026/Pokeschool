<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Painel do Aluno</title>
</head>
<body>

<div class="header">
  <h2>Bem-vindo, ${nome}!</h2>
</div>

<div class="container">
  <div class="card">
    <h3>Seus Dados</h3>
    <p><strong>Nome:</strong> ${nome}</p>
    <p><strong>RA:</strong> ${ra}</p>
    <p><strong>Curso:</strong> ${curso}</p>
  </div>

  <div class="card">
    <h3>Área do Aluno</h3>
    <p>Aqui você pode consultar suas informações acadêmicas.</p>

    <button onclick="window.location.href='${pageContext.request.contextPath}/loginPage'">
      Sair
    </button>
  </div>
</div>

</body>
</html>