package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.AvaliacaoDAO;
import com.example.pokeschool.model.Avaliacao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/professor/editarNota")
public class EditarNotaServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");
        String raStr = request.getParameter("ra");
        String disciplinaStr = request.getParameter("disciplinaId");
        String n1Str = request.getParameter("n1");
        String n2Str = request.getParameter("n2");

        AvaliacaoDAO dao = new AvaliacaoDAO();

        // ========== AÇÃO DE EXCLUIR ==========
        if ("excluir".equals(acao)) {
            if (idStr != null && !idStr.isEmpty()) {
                dao.deletar(Integer.parseInt(idStr));
            }
            response.sendRedirect("notas?ra=" + raStr);
            return;
        }

        // ========== SALVAR OU ATUALIZAR ==========
        Avaliacao a = new Avaliacao();

        try {
            a.setN1(Double.parseDouble(n1Str));
            a.setN2(Double.parseDouble(n2Str));

            if (idStr == null || idStr.isEmpty()) {
                // Nova nota
                a.setIdAluno(Integer.parseInt(raStr));
                a.setIdDisciplina(Integer.parseInt(disciplinaStr));
                dao.salvar(a);
            } else {
                // Atualizar nota existente
                a.setId(Integer.parseInt(idStr));
                dao.atualizar(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("notas?ra=" + raStr);
    }
}