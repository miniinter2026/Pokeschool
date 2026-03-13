<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>PokeSchool | Login</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Styles/sign-in.css" />
  <link rel="shortcut icon" href="assets/img/LogoPokeSchool.png" type="image/x-icon">
</head>
<body>
<!-- Login Forms -->
<section class="loginFormsSection">
  <h1>
    Bem Vindo <br />
    Novamente!!
  </h1>
  <img src="${pageContext.request.contextPath}/assets/img/LogoPokeSchool.png" class="logo">

  <!-- ✅ EXIBIR ERRO -->
  <%
    String erro = (String) request.getAttribute("erro");
    if (erro != null) {
  %>
  <div class="error-message" style="background-color: #ffe6e6; color: #d32f2f; padding: 10px; border-radius: 5px; margin-bottom: 20px; text-align: center;">
    <%= erro %>
  </div>
  <%
    }
  %>

  <form action="${pageContext.request.contextPath}/login" method="post">
    <input type="text" name="usuario" placeholder="RA ou usuário" required>
    <input type="password" name="senha" id="senha" placeholder="Senha" required>
    <button type="button" class="toggle-senha">👁️</button>
    <button type="submit">Entrar</button>
  </form>

  <a href="${pageContext.request.contextPath}/recuperaSenha/solicitar-senha.jsp" class="ems">
    Esqueci minha senha
  </a>
  <br><br>
  <p>
    Não tem conta?
    <a href="${pageContext.request.contextPath}/CadastroAluno">
      Cadastre-se
    </a>
  </p>
</section>

<!-- JAVA SCRIPT -->
<script>
  const toggleSenha = document.querySelector(".toggle-senha");
  const inputSenha = document.getElementById("senha"); // ✅ CORREÇÃO: usar ID

  toggleSenha.addEventListener("click", function () {
    if (inputSenha.type === "password") {
      inputSenha.type = "text";
      toggleSenha.textContent = "🙈";
    } else {
      inputSenha.type = "password";
      toggleSenha.textContent = "👁️";
    }
  });

  const urlParams = new URLSearchParams(window.location.search);
  if (urlParams.get('sucesso') === 'senhaAtualizada') {
    alert("Senha atualizada com sucesso! Faça login.");
  }
</script>
</body>
</html>