package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.ProfessorDAO;
import com.example.pokeschool.model.Professor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/adminProfessores")
public class AdminProfessoresServlet extends HttpServlet {

    // GET - Carrega os dados e mostra a página
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProfessorDAO dao = new ProfessorDAO();
        List<Professor> lista = dao.listar();

        request.setAttribute("listaProfessores", lista);

        request.getRequestDispatcher("/adminProfessores.jsp")
                .forward(request, response);
    }

    // POST - Insere, Edita e Exclui
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        String disciplinaStr = request.getParameter("disciplina");

        ProfessorDAO dao = new ProfessorDAO();

        if ("excluir".equals(acao)) {
            if (idStr != null && !idStr.isEmpty()) {
                dao.deletar(Integer.parseInt(idStr));
            }
            response.sendRedirect("adminProfessores");
            return;
        }

        Professor p = new Professor();
        p.setNomeCompleto(nome);
        p.setNomeUsuario(usuario);
        p.setIdDisciplina(Integer.parseInt(disciplinaStr));

        if ("inserir".equals(acao)) {
            p.setSenha(senha);
            dao.inserir(p);
        } else {
            p.setId(Integer.parseInt(idStr));
            if (senha != null && !senha.isEmpty()) {
                p.setSenha(senha);
            }
            dao.atualizar(p);
        }

        response.sendRedirect("adminProfessores");
    }
}