<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>PokeSchool | Login</title>
    <link rel="stylesheet" type="text/css" href="Styles/sign-in.css" />
  </head>
  <body>
    <!-- Login Forms -->
    <section class="loginFormsSection">
      <h1>
        Bem Vindo <br />
        Novamente!!
      </h1>
      <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="usuario">RA ou usuário:</label>
        <input class="raCpf" type="text" name="usuario" placeholder="RA ou usuário" required>
        <label for="Senha">Senha:</label>
        <input class="senha" type="password" name="senha" placeholder="Senha" required>
        <button type="button" class="toggle-senha">👁️</button>
        <button type="submit">Entrar</button>
        <p>Não tem login? <a href="cadastro/index.html">Cadastre-se</a></p>
        <a class="ems" href="${pageContext.request.contextPath}/recuperaSenha/solicitar-senha.jsp"
        style="font-family: var(--font-text);
  color: #d80000;
  text-decoration: none;
  align-self: center;
  font-weight: 600;
  transition: 0.2s ease;">
          Esqueci minha senha
        </a>
      </form>
    </section>
    <!-- JAVA SCRIPT -->
    <script>
      const toggleSenha = document.querySelector(".toggle-senha");
      const inputSenha = document.querySelector(".senha");

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
      if (urlParams.get("sucesso") === "senhaAtualizada") {
        alert("Senha atualizada com sucesso! Faça login.");
      }
    </script>
  </body>
</html>
