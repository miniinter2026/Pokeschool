package com.example.pokeschool.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.dao.ProfessorDAO;
import com.example.pokeschool.dao.AdministradorDAO;
import com.example.pokeschool.model.Administrador;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Professor;

@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            System.out.println("🔵 Acessando página de login (GET)");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            System.err.println("❌ Erro no doGet do Login: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro ao carregar página de login: " + e.getMessage() + "</h3>");
            out.println("<a href='index.jsp'>Tentar novamente</a>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String usuario = request.getParameter("usuario");
            String senha = request.getParameter("senha");

            System.out.println("🔵 Tentativa de login com usuário: " + usuario);

            String tipo = identificarTipo(usuario);
            System.out.println("🔵 Tipo identificado: " + tipo);

            if (usuario == null || usuario.isEmpty() || senha == null || senha.isEmpty()) {
                System.out.println("❌ Usuário ou senha vazios");
                request.setAttribute("erro", "Preencha todos os campos!");
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
                return;
            }

            if (tipo.equals("ALUNO")) {
                AlunoDAO alunoDAO = new AlunoDAO();
                int ra = Integer.parseInt(usuario);
                System.out.println("🔵 RA do aluno: " + ra);

                boolean loginValido = false;
                try {
                    loginValido = alunoDAO.verificaLoginAluno(ra, senha);
                } catch (Exception e) {
                    System.err.println("❌ Erro ao verificar login de aluno: " + e.getMessage());
                    e.printStackTrace();
                }

                if (loginValido) {
                    System.out.println("✅ Login de aluno válido!");
                    RequestDispatcher rd = request.getRequestDispatcher("aluno/home-aluno.jsp");
                    rd.forward(request, response);
                } else {
                    System.out.println("❌ Login de aluno inválido");
                    request.setAttribute("erro", "Usuário ou senha incorretos!");
                    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                    rd.forward(request, response);
                }

            } else if (tipo.equals("PROFESSOR")) {
                ProfessorDAO professorDAO = new ProfessorDAO();
                System.out.println("🔵 Verificando login de professor: " + usuario);

                Professor professor = null;

                try {
                    professor = professorDAO.login(usuario, senha);
                    System.out.println("🔵 Professor retornado: " + (professor != null ? "SIM ✅" : "NÃO ❌"));
                    if (professor != null) {
                        System.out.println("🔵 ID: " + professor.getId());
                        System.out.println("🔵 Nome: " + professor.getNomeCompleto());
                        System.out.println("🔵 Disciplina ID: " + professor.getIdDisciplina());
                        System.out.println("🔵 Disciplina Nome: " + professor.getNomeDisciplina());
                    }
                } catch (Exception e) {
                    System.err.println("❌ Erro ao verificar login de professor: " + e.getMessage());
                    e.printStackTrace();
                }

                if (professor != null) {
                    System.out.println("✅ Login de professor válido!");

                    // ✅ SALVAR NA SESSÃO
                    HttpSession session = request.getSession();
                    session.setAttribute("professorId", professor.getId());
                    session.setAttribute("professorNome", professor.getNomeCompleto());
                    session.setAttribute("professorDisciplina", professor.getIdDisciplina());
                    session.setAttribute("professorNomeDisciplina", professor.getNomeDisciplina());
                    session.setAttribute("professor", professor);

                    // ✅ REDIRECIONAR PARA DASHBOARD
                    RequestDispatcher rd = request.getRequestDispatcher("professor/dashboard");
                    rd.forward(request, response);
                } else {
                    System.out.println("❌ Login de professor inválido");
                    request.setAttribute("erro", "Usuário ou senha incorretos!");
                    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                    rd.forward(request, response);
                }

            } else if (tipo.equals("ADM")) {
                AdministradorDAO administradorDAO = new AdministradorDAO();
                System.out.println("🔵 Verificando login de administrador: " + usuario);

                boolean loginValido = false;
                try {
                    loginValido = administradorDAO.verificaLoginAdministrador(usuario, senha);
                } catch (Exception e) {
                    System.err.println("❌ Erro ao verificar login de administrador: " + e.getMessage());
                    e.printStackTrace();
                }

                if (loginValido) {
                    System.out.println("✅ Login de administrador válido!");
                    RequestDispatcher rd = request.getRequestDispatcher("area-restrita/index.jsp");
                    rd.forward(request, response);
                } else {
                    System.out.println("❌ Login de administrador inválido");
                    request.setAttribute("erro", "Usuário ou senha incorretos!");
                    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                    rd.forward(request, response);
                }
            } else {
                System.out.println("❌ Tipo de usuário não reconhecido");
                request.setAttribute("erro", "Tipo de usuário não reconhecido!");
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            }

        } catch (NumberFormatException e) {
            System.err.println("❌ NumberFormatException no Login: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("erro", "RA deve conter apenas números!");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } catch (NullPointerException e) {
            System.err.println("❌ NullPointerException no Login: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("erro", "Dados não recebidos corretamente.");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } catch (ServletException e) {
            System.err.println("❌ ServletException no Login: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao processar requisição.");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            System.err.println("❌ Erro geral no Login: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("erro", "Erro inesperado: " + e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
    }

    // ✅ MÉTODO CORREGIDO - Agora reconhece ana.mat corretamente
    private String identificarTipo(String usuario) {
        // ✅ ALUNO: Apenas números (5 ou 6 dígitos)
        if (usuario != null && usuario.matches("^\\d{5,6}$")) {
            return "ALUNO";
        }
        // ✅ PROFESSOR: Letras + ponto + letras (ex: ana.mat, joao.silva)
        else if (usuario != null && usuario.matches("^[a-zA-Z]+\\.[a-zA-Z]+$")) {
            return "PROFESSOR";
        }
        // ✅ ADM: Letras + ponto + "adm" (ex: beatrizAdm.user)
        else if (usuario != null && usuario.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9])(?=.*(?i)adm).+$")) {
            return "ADM";
        }
        return "INVALIDO";
    }
}