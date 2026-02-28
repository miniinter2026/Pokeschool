<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <title>Recuperar Senha</title>
</head>
<body>

<div class="container">
  <h2>Recuperar Senha</h2>

  <form action="${pageContext.request.contextPath}/recuperar-senha" method="POST">
    <input type="hidden" name="acao" value="solicitar">

    <input type="email"
           name="email"
           placeholder="Digite seu e-mail"
           required>

    <button type="submit">Enviar Token</button>
  </form>
</div>

</body>
</html>