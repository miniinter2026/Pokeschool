<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8" />
  <title>Recuperar Senha</title>
  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/Styles/solicitar-senha.css" />
</head>
<body>
<img src="<%= request.getContextPath() %>/assets/img/mew2.png" class="mew2" style="
        position: absolute;
        width: 270px;
        right: 425px;
        top: 15%;
        z-index: 2;
    "/>

<div class="container">
  <h2>Recuperar Senha</h2>

  <form action="${pageContext.request.contextPath}/recuperar-senha" method="POST">
    <input type="hidden" name="acao" value="solicitar">
    <input type="email" name="email" placeholder="Digite seu e-mail" required>
    <button type="submit">Enviar Token</button>
  </form>
</div>

<img src="<%= request.getContextPath() %>/assets/img/mew1.png" class="mew1" style="
        position: absolute;
        width: 200px;
        left: 450px;
        top: 55%;
    "/>
</body>
</html>