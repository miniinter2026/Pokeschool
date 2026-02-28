package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.ObservacoesDAO;
import com.example.pokeschool.model.Observacoes;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/aluno/observacoes")
public class ObservacoesAlunoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔥 Pegando o RA da URL
        String raParam = request.getParameter("ra");

        if (raParam == null || raParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "RA não informado.");
            return;
        }

        int ra = Integer.parseInt(raParam);

        ObservacoesDAO dao = new ObservacoesDAO();
        List<Observacoes> lista = dao.listarPorAluno(ra);

        request.setAttribute("listaObservacoes", lista);
        request.setAttribute("ra", ra); // opcional, caso queira usar na JSP

        request.getRequestDispatcher("/observacoesAluno.jsp")
                .forward(request, response);
    }
}