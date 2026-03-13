<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>PokeSchool | Cadastro</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Styles/sign-up.css" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/LogoPokeSchool.png" type="image/x-icon">
</head>
<body>
<!-- Login Forms -->
<section class="cadastroFormsSection">
    <h1>
        Seja <br />
        Bem vindo!!
    </h1>
    <img src="${pageContext.request.contextPath}/assets/img/LogoPokeSchool.png" class="logo">
    <form action="${pageContext.request.contextPath}/CadastroAluno" method="post">
        <input type="text" name="ra" class="ra" placeholder="RA" required>
        <input type="text" name="nomeCompleto" placeholder="Nome" required>
        <input type="email" name="email" class="email" placeholder="Email" required>
        <input type="password" name="senha" class="senha" placeholder="Senha" required>
        <input type="number" name="idSala" placeholder="Sala" required>
        <button type="submit">Cadastrar</button>
        <p>Já tem conta? <a href="${pageContext.request.contextPath}/index.jsp">Faça Login</a></p>
    </form>
</section>
<!-- Details -->
<div class="upwards-details">
    <div class="black-up-left"></div>
    <div class="red-up-left"></div>
</div>
<div class="downwards-details">
    <div class="black-down-rigth"></div>
    <div class="rd-down-rigth"></div>
</div>
<!-- Modal de erro -->
<div id="modal-erros" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Erros de Validação:</h2>
        <ul id="lista-erros"></ul>
    </div>
</div>
<!-- JAVA SCRIPT -->
<script>
    const form = document.querySelector("form");
    const ra = document.querySelector(".ra");
    const senha = document.querySelector(".senha");
    const email = document.querySelector(".email");


    const raRegex = /^\d{5,6}$/;
    const senhaRegex =
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%?&])[A-Za-z\d@$!%?&]{8,}$/;
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        let erros = [];

        // Resetando bordas
        ra.style.borderColor = "";
        senha.style.borderColor = "";

        if (!raRegex.test(ra.value)) {
            erros.push(
                "RA inválido: use o formato 000.000.000-0 SP ou 0000000000SP"
            );
            ra.style.borderColor = "red";
        }

        if (!senhaRegex.test(senha.value)) {
            erros.push(
                "Senha inválida: mínimo 8 caracteres, 1 minúscula, 1 maiúscula, 1 número e 1 especial (@$!%?&)"
            );
            senha.style.borderColor = "red";
        }

        if (!emailRegex.test(email.value)) {
            erros.push("Email inválido: use o formato exemplo.exemplo@email.com");
            email.style.borderColor = "red";
        }

        if (erros.length > 0) {
            exibirModal(erros);
        } else {
            console.log("Todos os campos são válidos.");
            form.submit();
        }
    });

    function exibirModal(erros) {
        const modal = document.getElementById("modal-erros");
        const listaErros = document.getElementById("lista-erros");

        listaErros.innerHTML = "";

        erros.forEach((erro) => {
            const item = document.createElement("li");
            item.textContent = erro;
            listaErros.appendChild(item);
        });

        modal.style.display = "flex";
    }

    document.querySelector(".close").addEventListener("click", function () {
        document.getElementById("modal-erros").style.display = "none";
    });

    window.addEventListener("click", function (e) {
        const modal = document.getElementById("modal-erros");
        if (e.target === modal) {
            modal.style.display = "none";
        }
    });
</script>
</body>
</html>