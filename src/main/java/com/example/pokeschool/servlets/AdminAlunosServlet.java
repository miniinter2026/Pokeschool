package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.model.Aluno;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/adminAlunos")
public class AdminAlunosServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            AlunoDAO dao = new AlunoDAO();
            List<Aluno> lista = dao.listar();
            request.setAttribute("listaAlunos", lista);
            request.getRequestDispatcher("/adminDashboard").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/html/erro.html");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String acao = request.getParameter("acao");
        System.out.println("AdminAlunos - Ação: " + acao);

        try {
            AlunoDAO dao = new AlunoDAO();

            if ("inserir".equals(acao)) {
                int ra = Integer.parseInt(request.getParameter("ra"));
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                int sala = Integer.parseInt(request.getParameter("sala"));

                Aluno a = new Aluno();
                a.setRa(ra);
                a.setNomeCompleto(nome);
                a.setEmail(email);
                a.setSenha(senha);
                a.setIdSala(sala);

                dao.inserir(a);
                System.out.println("Aluno inserido RA: " + ra);
            }
            else if ("editar".equals(acao)) {
                int ra = Integer.parseInt(request.getParameter("ra"));
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                int sala = Integer.parseInt(request.getParameter("sala"));

                Aluno a = new Aluno();
                a.setRa(ra);
                a.setNomeCompleto(nome);
                a.setEmail(email);
                a.setIdSala(sala);

                //so atualiza senha se foi preenchida
                if (senha != null && !senha.trim().isEmpty()) {
                    a.setSenha(senha);
                }

                boolean atualizou = dao.atualizar(a);
                System.out.println("Aluno atualizado RA " + ra + ": " + (atualizou ? "SIM" : "NÃO"));
            }
            else if ("excluir".equals(acao)) {
                int ra = Integer.parseInt(request.getParameter("ra"));
                dao.deletar(ra);
                System.out.println("Aluno excluído RA: " + ra);
            }

            response.sendRedirect(request.getContextPath() + "/adminDashboard");

        } catch (Exception e) {
            System.err.println("Erro no AdminAlunosServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/html/erro.html");
        }
    }
}