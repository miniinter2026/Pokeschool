package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.AvaliacaoDAO;
import com.example.pokeschool.model.Avaliacao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/aluno/notas")
public class AlunoNotasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int ra = Integer.parseInt(request.getParameter("ra"));

            AvaliacaoDAO dao = new AvaliacaoDAO();
            List<Avaliacao> lista = dao.listarPorAluno(ra);

            request.setAttribute("notas", lista);

            request.getRequestDispatcher("/notas.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao carregar notas.");
        }
    }
}