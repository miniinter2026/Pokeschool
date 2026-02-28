<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<div class="login-box">
    <h2>Login</h2>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="text" name="usuario" placeholder="RA ou usuário" required>
        <input type="password" name="senha" placeholder="Senha" required>
        <button type="submit">Entrar</button>
    </form>

    <a href="${pageContext.request.contextPath}/recuperaSenha/solicitar-senha.jsp">
        Esqueci minha senha
    </a>

    <br><br>

    <a href="${pageContext.request.contextPath}/CadastroAluno">
        Cadastre-se
    </a>
</div>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('sucesso') === 'senhaAtualizada') {
        alert("Senha atualizada com sucesso! Faça login.");
    }
</script>
</body>
</html>