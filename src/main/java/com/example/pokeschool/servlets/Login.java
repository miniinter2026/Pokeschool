package com.example.pokeschool.servlets;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.dao.ProfessorDAO;
import com.example.pokeschool.dao.AdministradorDAO;
import com.example.pokeschool.model.Administrador;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Professor;

@WebServlet(urlPatterns = {"/login"}) // conferir form no código do front
public class Login extends HttpServlet {

    // Método que envia para a página de login
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            System.out.println("Acessando página de login (GET)");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            System.err.println("Erro no doGet do Login: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro ao carregar página de login: " + e.getMessage() + "</h3>");
            out.println("<a href='login.jsp'>Tentar novamente</a>");
        }
    }

    // Método para RECEBER os dados do formulário
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // recebe os parâmetros da página de login
            String usuario = request.getParameter("usuario");
            String senha = request.getParameter("senha");

            System.out.println("Tentativa de login com usuário: " + usuario);

            // gurda em uma variável se é professor, aluno ou inválido
            String tipo = identificarTipo(usuario);
            System.out.println("Tipo identificado: " + tipo);

            // Verifica se usuário e senha foram enviados
            if (usuario == null || usuario.isEmpty() || senha == null || senha.isEmpty()) {
                System.out.println("Usuário ou senha vazios");
                request.setAttribute("erro", "Preencha todos os campos!");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
                return;
            }

            if (tipo.equals("ALUNO")) {
                // ===== CHAMADA DO MÉTODO PARA ALUNO =====
                AlunoDAO alunoDAO = new AlunoDAO();
                // ja que é aluno podemos converter a String usuario para int, para poder usá-lo no método de verificar login
                int ra = Integer.parseInt(usuario); // converte aqui
                System.out.println("RA do aluno: " + ra);

                boolean loginValido = false;
                try {
                    loginValido = alunoDAO.verificaLoginAluno(ra, senha);
                } catch (Exception e) {
                    System.err.println("Erro ao verificar login de aluno: " + e.getMessage());
                    e.printStackTrace();
                }

                if (loginValido) {
                    System.out.println("Login de aluno válido!");
                    RequestDispatcher rd = request.getRequestDispatcher("aluno/index.jsp");
                    rd.forward(request, response);
                } else {
                    System.out.println("Login de aluno inválido");
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/erro.jsp"); // arrumar caminho
                    rd.forward(request, response);
                }

            } else if (tipo.equals("PROFESSOR")) {
                // ===== CHAMADA DO MÉTODO PARA PROFESSOR =====
                ProfessorDAO professorDAO = new ProfessorDAO();
                // Faz a verificação se é o professor existe no banco
                System.out.println("Verificando login de professor: " + usuario);

                boolean loginValido = false;
                try {
                    loginValido = professorDAO.verificaLoginProfessor(usuario, senha);
                } catch (Exception e) {
                    System.err.println("Erro ao verificar login de professor: " + e.getMessage());
                    e.printStackTrace();
                }

                if (loginValido) {
                    System.out.println("Login de professor válido!");
                    RequestDispatcher rd = request.getRequestDispatcher("professor/index.jsp");
                    rd.forward(request, response);
                } else {
                    System.out.println("Login de professor inválido");
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/erro.jsp"); // arrumar caminho
                    rd.forward(request, response);
                }

            } else if (tipo.equals("ADM")) {
                // ===== CHAMADA DO MÉTODO PARA ADMINISTRADOR =====
                AdministradorDAO administradorDAO = new AdministradorDAO();
                // Faz a verificação se é o administrador existe no banco
                System.out.println("Verificando login de administrador: " + usuario);

                boolean loginValido = false;
                try {
                    loginValido = administradorDAO.verificaLoginAdministrador(usuario, senha);
                } catch (Exception e) {
                    System.err.println("Erro ao verificar login de administrador: " + e.getMessage());
                    e.printStackTrace();
                }

                if (loginValido) {
                    System.out.println("Login de administrador válido!");
                    RequestDispatcher rd = request.getRequestDispatcher("area-restrita/index.jsp");
                    rd.forward(request, response);
                } else {
                    System.out.println("Login de administrador inválido");
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/erro.jsp"); // arrumar caminho
                    rd.forward(request, response);
                }
            } else {
                System.out.println("Tipo de usuário não reconhecido");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/erro.jsp"); // arrumar caminho
                rd.forward(request, response);
            }

        } catch (NumberFormatException e) {
            // Erro ao converter RA para número (quando usuário mistura letras no campo de número)
            System.err.println("NumberFormatException no Login: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro: RA deve conter apenas números!</h3>");
            out.println("<a href='login.jsp'>Voltar</a>");

        } catch (NullPointerException e) {
            // Algum parâmetro veio nulo
            System.err.println("NullPointerException no Login: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro: Dados não recebidos corretamente.</h3>");
            out.println("<p>Detalhes: " + e.getMessage() + "</p>");
            out.println("<a href='login.jsp'>Voltar</a>");

        } catch (ServletException e) {
            // Erro ao redirecionar/forward
            System.err.println("ServletException no Login: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro ao processar requisição: " + e.getMessage() + "</h3>");
            out.println("<a href='login.jsp'>Voltar</a>");

        } catch (Exception e) {
            // Qualquer outro erro
            System.err.println("Erro geral no Login: " + e.getMessage());
            e.printStackTrace();
            ((PrintWriter) out).println("<h3>Erro inesperado: " + e.getMessage() + "</h3>");
            out.println("<a href='login.jsp'>Voltar</a>");
        }
    }

    private String identificarTipo(String usuario) {

        // verifica se é aluno, professor ou administrador
        if (usuario != null && usuario.matches("^\\d{5,6}$")) {
            return "ALUNO";

        } else if (usuario != null && usuario.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).+$")) {
            return "PROFESSOR";

        } else if (usuario != null && usuario.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9])(?=.*(?i)adm).+$")) {
            return "ADM";
        }
        return "INVALIDO";
    }
}
