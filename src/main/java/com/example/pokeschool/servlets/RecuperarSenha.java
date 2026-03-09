package com.example.pokeschool.servlets;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.dao.TokenDAO;
import com.example.pokeschool.model.Token;
import com.example.pokeschool.util.Email;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = {"/recuperar-senha"})
public class RecuperarSenha extends HttpServlet {
    private Conexao banco = new Conexao();
    private Connection conn;

    // ========== DISPATCHER ==========
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        if (token == null) {
            // Se não tem token, redireciona para página de solicitar
            response.sendRedirect("recuperaSenha/solicitar-senha.jsp");
            return;
        }

        if (validarToken(token)) {
            // CORRIGIDO: adicionar pasta "recuperaSenha/"
            response.sendRedirect("recuperaSenha/nova-senha.jsp?token=" + token);
        } else {
            response.sendRedirect(request.getContextPath() + "html/erroTokenInvalidoOuExpirado.html");
        }
    }

    // No método doPost() do RecuperarSenha.java

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");

        // 🔧 CORREÇÃO: Tratar caso acao seja null
        if (acao == null) {
            response.sendRedirect(request.getContextPath() + "html/parametroObrigatorio.html");
            return;
        }

        switch (acao) {
            case "solicitar":
                solicitarToken(request, response);
                break;
            case "verificar":  // ← Não esqueça de adicionar esse case!
                verificarToken(request, response);
                break;
            case "redefinir":
                atualizarSenha(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "html/acaoInvalida.html");
        }
    }

    // ========== NOVO MÉTODO: VERIFICAR TOKEN ==========
    private void verificarToken(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        System.out.println("TOKEN RECEBIDO: " + token);

        if (token == null || token.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "html/tokenObrigatorio.html");
            return;
        }

        // Usar o TokenDAO para buscar o token
        TokenDAO tokenDAO = new TokenDAO();
        Token tokenObj = tokenDAO.buscarPorToken(token);

        if (tokenObj == null) {
            response.sendRedirect(request.getContextPath() + "html/tokenInvalido.html");
            return;
        }

        if (tokenObj.isUtilizado()) {
            response.sendRedirect(request.getContextPath() + "html/tokenJaUtilizado.html");
            return;
        }

        if (tokenObj.getDataExpiracao().isBefore(LocalDateTime.now())) {
            response.sendRedirect(request.getContextPath() + "html/tokenExpirado.html");
            return;
        }

        // Token válido!
        response.sendRedirect("recuperaSenha/nova-senha.jsp?token=" + token);
    }


    // ========== MÉTODO 1: SOLICITAR TOKEN ==========
    private void solicitarToken(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        if (email == null || email.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "html/emailObrigatorio.html");
            return;
        }
        // No método solicitarToken(), no início:
        System.out.println(">>> SOLICITAR TOKEN - Email: " + email);

        try {
            conn = banco.conectar();

            // Verificar se email existe
            String checkSql = "SELECT ra FROM aluno WHERE email = ? UNION SELECT id FROM professor WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, email);
            checkStmt.setString(2, email);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                response.sendRedirect(request.getContextPath() + "html/emailNaoEncontrado.html");
                return;
            }

            // Gerar token
            TokenDAO tokenDAO = new TokenDAO();
            String tokenStr = tokenDAO.gerarToken6Digitos();
            LocalDateTime expiracao = LocalDateTime.now().plusHours(2);

            // Salvar token no banco
            Token token = new Token(tokenStr, email, LocalDateTime.now(), expiracao, false);
            tokenDAO.inserir(token);

            // ========== ENVIAR EMAIL REAL ==========
            boolean emailEnviado = Email.enviarTokenRecuperacao(email, tokenStr);

            if (emailEnviado) {
                response.sendRedirect("recuperaSenha/verificar-token.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "html/erroAoEnviarEmail.html");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "html/erroToken.html");
        } finally {
            banco.desconectar(conn);
        }
    }

    // ========== MÉTODO 2: VALIDAR TOKEN ==========
    private boolean validarToken(String token) {
        try {
            conn = banco.conectar();
            String sql = "SELECT * FROM token "
                    + "WHERE token = ? AND utilizado = FALSE "
                    + "AND data_expiracao > NOW()";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            banco.desconectar(conn);
        }
    }

    // ========== MÉTODO 3: ATUALIZAR SENHA ==========
    private void atualizarSenha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        String novaSenha = request.getParameter("novaSenha");

        if (token == null || token.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "html/tokenInvalido.html");
            return;
        }
        if (token == null || novaSenha == null) {
            response.sendRedirect(request.getContextPath() + "html/tokenESenhaObrigatorios.html");
            return;
        }

        if (!validarToken(token)) {
            response.sendRedirect(request.getContextPath() + "html/tokenInvalido.html");
            return;
        }

        try {
            conn = banco.conectar();

            // Pegar email do token
            String getEmailSql = "SELECT email_usuario FROM token WHERE token = ?";
            PreparedStatement getEmailStmt = conn.prepareStatement(getEmailSql);
            getEmailStmt.setString(1, token);
            ResultSet rs = getEmailStmt.executeQuery();

            String email = null;
            if (rs.next()) {
                email = rs.getString("email_usuario");
            }

            if (email == null) {
                response.sendRedirect(request.getContextPath() + "html/erroUsuario.html");
                return;
            }

            // Atualizar senha em ambas as tabelas
            String updateAlunoSql = "UPDATE aluno SET senha = ? WHERE email = ?";
            String updateProfessorSql = "UPDATE professor SET senha = ? WHERE email = ?";

            PreparedStatement updateAlunoStmt = conn.prepareStatement(updateAlunoSql);
            updateAlunoStmt.setString(1, novaSenha);
            updateAlunoStmt.setString(2, email);
            updateAlunoStmt.executeUpdate();

            PreparedStatement updateProfessorStmt = conn.prepareStatement(updateProfessorSql);
            updateProfessorStmt.setString(1, novaSenha);
            updateProfessorStmt.setString(2, email);
            updateProfessorStmt.executeUpdate();

            // Marcar token como utilizado
            String updateTokenSql = "UPDATE token SET utilizado = TRUE WHERE token = ?";
            PreparedStatement updateTokenStmt = conn.prepareStatement(updateTokenSql);
            updateTokenStmt.setString(1, token);
            updateTokenStmt.executeUpdate();

            response.sendRedirect(request.getContextPath() + "/index.jsp?sucesso=senhaAtualizada");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "html/erroAlterarSenha.html");
        } finally {
            banco.desconectar(conn);
        }
    }
}