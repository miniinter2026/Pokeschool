package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.dao.ProfessorDAO;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Professor;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/CadastroAluno", "/CadastroProfessor"})
public class Cadastro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Captura o caminho utilizado para chegar no método
            String caminho = request.getServletPath();
            System.out.println("Caminho acessado (GET): " + caminho);

            // Direciona usuário a partir do caminho que chegou no servlet
            if (caminho.equals("/CadastroAluno")) {
                System.out.println("Entrou no aluno - GET");
                RequestDispatcher rd = request.getRequestDispatcher("cadastro-aluno/index.jsp"); // arrumar caminho
                rd.forward(request, response);

            } else if (caminho.equals("/CadastroProfessor")) {
                System.out.println("Entrou no professor - GET");
                RequestDispatcher rd = request.getRequestDispatcher("cadastro-professor/index.jsp");
                rd.forward(request, response);

            } else {
                // Caminho não reconhecido
                System.out.println("Caminho não reconhecido: " + caminho);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Página não encontrada");
            }

        } catch (NullPointerException e) {
            System.err.println("NullPointerException no doGet: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro: Caminho nulo - " + e.getMessage() + "</h3>");
            out.println("<a href='login.jsp'>Voltar ao Login</a>");

        } catch (ServletException e) {
            System.err.println("ServletException no doGet: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro no Servlet: " + e.getMessage() + "</h3>");

        } catch (Exception e) {
            System.err.println("Erro geral no doGet: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro inesperado: " + e.getMessage() + "</h3>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Captura o caminho utilizado para chegar no método
            String caminho = request.getServletPath();
            System.out.println("Caminho acessado (POST): " + caminho);

            // Verifica se é aluno ou professor
            if (caminho.equals("/CadastroAluno")) {
                System.out.println("Entrou no if de cadastro aluno - POST");

                // GUARDA AS VARIÁVEIS DE CADASTRO
                int ra = Integer.parseInt(request.getParameter("ra"));
                String nomeCompleto = request.getParameter("nomeCompleto");
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                int idSala = Integer.parseInt(request.getParameter("Idsala"));

                System.out.println("RA: " + ra);
                System.out.println("Nome: " + nomeCompleto);
                System.out.println("Email: " + email);
                System.out.println("ID Sala: " + idSala);

                // cria o aluno
                Aluno aluno = new Aluno(ra, nomeCompleto, email, senha, idSala);

                // insere no bd
                AlunoDAO alunoDAO = new AlunoDAO();
                boolean sucesso = alunoDAO.inserir(aluno);

                if (sucesso) {
                    System.out.println("Aluno cadastrado com sucesso!");
                    // Redireciona para o login (redirect para evitar reenvio do formulário)
                    response.sendRedirect("login.jsp");
                } else {
                    System.out.println("Falha ao cadastrar aluno");
                    request.setAttribute("erro", "Falha ao cadastrar aluno");
                    RequestDispatcher rd = request.getRequestDispatcher("cadastro-aluno/index.jsp");
                    rd.forward(request, response);
                }

            } else if (caminho.equals("/CadastroProfessor")) {
                System.out.println("Entrou no if de cadastro professor - POST");

                // GUARDA AS VARIÁVEIS DE CADASTRO
                String nomeCompleto = request.getParameter("nomeCompleto");
                String nomeUsuario = request.getParameter("nomeUsuario");
                String senha = request.getParameter("senha");

                System.out.println("Nome: " + nomeCompleto);
                System.out.println("Usuário: " + nomeUsuario);

                // cria o professor
                Professor professor = new Professor(nomeCompleto, nomeUsuario, senha);

                // cadastra no banco de dados
                ProfessorDAO professorDAO = new ProfessorDAO();
                boolean sucesso = professorDAO.inserir(professor);

                if (sucesso) {
                    System.out.println("Professor cadastrado com sucesso!");
                    response.sendRedirect("login.jsp");
                } else {
                    System.out.println("Falha ao cadastrar professor");
                    request.setAttribute("erro", "Falha ao cadastrar professor");
                    RequestDispatcher rd = request.getRequestDispatcher("cadastro-professor/index.jsp");
                    rd.forward(request, response);
                }

            } else {
                System.out.println("Caminho POST não reconhecido: " + caminho);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Página não encontrada");
            }

        } catch (NumberFormatException e) {
            System.err.println("NumberFormatException: Número inválido nos parâmetros");
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro: Dados numéricos inválidos!</h3>");
            out.println("<p>Verifique os campos RA e ID da Sala.</p>");
            out.println("<a href='CadastroAluno'>Tentar novamente</a>");

        } catch (NullPointerException e) {
            System.err.println("NullPointerException no doPost: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro: Parâmetro nulo - " + e.getMessage() + "</h3>");
            out.println("<p>Verifique se todos os campos do formulário estão preenchidos.</p>");

        } catch (Exception e) {
            System.err.println("Erro geral no doPost: " + e.getMessage());
            e.printStackTrace();
            out.println("<h3>Erro inesperado: " + e.getMessage() + "</h3>");
        }
    }
}