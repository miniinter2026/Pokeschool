<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <title>Verificar Token</title>
</head>
<body>

<div class="container">
  <form action="${pageContext.request.contextPath}/recuperar-senha" method="POST">

    <input type="hidden" name="acao" value="verificar">

    <input type="text"
           name="token"
           maxlength="6"
           placeholder="Digite o token recebido"
           required>

    <button type="submit">Verificar</button>
  </form>
</div>

</body>
</html>