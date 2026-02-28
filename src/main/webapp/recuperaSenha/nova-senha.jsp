<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <title>Nova Senha</title>
</head>
<body>

<div class="container">
  <h2>Redefinir Senha</h2>

  <form action="${pageContext.request.contextPath}/recuperar-senha"
        method="POST"
        onsubmit="return validarSenhas()">

    <input type="hidden" name="acao" value="redefinir">
    <input type="hidden" name="token" id="tokenField">

    <input type="password" name="novaSenha"
           placeholder="Nova senha" required minlength="6">

    <input type="password" name="confirmarSenha"
           placeholder="Confirmar senha" required minlength="6">

    <button type="submit">Atualizar Senha</button>
  </form>
</div>

<script>
  // Pega o token da URL
  const urlParams = new URLSearchParams(window.location.search);
  const token = urlParams.get('token');
  if (token) {
    document.getElementById('tokenField').value = token;
  }

  // Valida se as senhas são iguais
  function validarSenhas() {
    const novaSenha = document.querySelector('input[name="novaSenha"]').value;
    const confirmarSenha = document.querySelector('input[name="confirmarSenha"]').value;

    if (novaSenha !== confirmarSenha) {
      alert('As senhas não coincidem!');
      return false;
    }
    return true;
  }
</script>

</body>
</html>