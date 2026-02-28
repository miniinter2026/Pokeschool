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

        AlunoDAO dao = new AlunoDAO();
        List<Aluno> lista = dao.listar();

        request.setAttribute("listaAlunos", lista);

        request.getRequestDispatcher("/adminAlunos.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String raStr = request.getParameter("ra");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String salaStr = request.getParameter("sala");

        AlunoDAO dao = new AlunoDAO();

        if ("excluir".equals(acao)) {
            if (raStr != null && !raStr.isEmpty()) {
                dao.deletar(Integer.parseInt(raStr));
            }
            response.sendRedirect("adminAlunos");
            return;
        }

        Aluno a = new Aluno();
        a.setRa(Integer.parseInt(raStr));
        a.setNomeCompleto(nome);
        a.setEmail(email);
        a.setIdSala(Integer.parseInt(salaStr));  // CORRIGIDO: setSala → setIdSala

        if ("inserir".equals(acao)) {
            a.setSenha(senha);
            dao.inserir(a);
        } else {
            if (senha != null && !senha.isEmpty()) {
                a.setSenha(senha);
            }
            dao.atualizar(a);
        }

        response.sendRedirect("adminAlunos");
    }
}