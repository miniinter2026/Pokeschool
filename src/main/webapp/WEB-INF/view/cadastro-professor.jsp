<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Cadastro Professor</title>
</head>
<body>

<div class="form-box">
  <h2>Cadastro Professor</h2>

  <form action="${pageContext.request.contextPath}/CadastroProfessor" method="post">
    <input type="text" name="cpf" placeholder="CPF" required>
    <input type="text" name="nome" placeholder="Nome" required>
    <input type="email" name="email" placeholder="Email" required>
    <input type="password" name="senha" placeholder="Senha" required>
    <input type="text" name="disciplina" placeholder="Disciplina" required>
    <button type="submit">Cadastrar</button>
  </form>

</div>

</body>
</html>