package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.ObservacoesDAO;
import com.example.pokeschool.model.Observacoes;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/professor/editarObservacao")
public class EditarObservacaoServlet extends HttpServlet {

    // Método GET - Exibe o formulário de edição
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr);

            ObservacoesDAO dao = new ObservacoesDAO();
            Observacoes observacao = dao.buscarPorId(id);

            request.setAttribute("observacao", observacao);
            request.getRequestDispatcher("/editarObservacao.jsp").forward(request, response);
        } else {
            response.sendRedirect("observacoes");
        }
    }

    // Método POST - Salva as alterações
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String raStr = request.getParameter("ra");
        String disciplinaStr = request.getParameter("disciplinaId");
        String descricao = request.getParameter("descricao");

        ObservacoesDAO dao = new ObservacoesDAO();
        Observacoes o = new Observacoes();

        try {
            o.setDescricao(descricao);

            if (idStr == null || idStr.isEmpty()) {
                // Nova observação
                o.setIdAluno(Integer.parseInt(raStr));
                o.setIdDisciplina(Integer.parseInt(disciplinaStr));
                dao.inserir(o);
            } else {
                // Atualizar observação existente
                o.setId(Integer.parseInt(idStr));
                dao.atualizar(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("observacoes?ra=" + raStr);
    }
}