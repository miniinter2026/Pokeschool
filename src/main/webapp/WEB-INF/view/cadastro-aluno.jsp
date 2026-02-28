<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Cadastro Aluno</title>
</head>
<body>

<div class="form-box">
  <h2>Cadastro Aluno</h2>

  <form action="${pageContext.request.contextPath}/CadastroAluno" method="post">
    <input type="text" name="ra" placeholder="RA" required>
    <input type="text" name="nome" placeholder="Nome" required>
    <input type="email" name="email" placeholder="Email" required>
    <input type="password" name="senha" placeholder="Senha" required>
    <input type="number" name="Idsala" placeholder="Sala" required>
    <button type="submit">Cadastrar</button>
  </form>

</div>

</body>
</html>