<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <title>Erro</title>
</head>
<body>

<div class="error-box">
  <h1>Erro</h1>
  <h2>Algo deu errado 😕</h2>
  <p>Não foi possível processar sua solicitação.</p>

  <a href="${pageContext.request.contextPath}/loginPage">
    Voltar para o início
  </a>
</div>

</body>
</html>