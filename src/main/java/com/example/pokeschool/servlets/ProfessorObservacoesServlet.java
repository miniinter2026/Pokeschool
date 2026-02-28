package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.dao.ObservacoesDAO;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Observacoes;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/professor/observacoes")
public class ProfessorObservacoesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Carregar todos os alunos para o dropdown
        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> listaAlunos = alunoDAO.listar();
        request.setAttribute("listaAlunos", listaAlunos);

        // Verificar se foi selecionado um aluno
        String raStr = request.getParameter("ra");
        List<Observacoes> listaObservacoes = null;
        Aluno alunoSelecionado = null;

        if (raStr != null && !raStr.isEmpty()) {
            int ra = Integer.parseInt(raStr);

            ObservacoesDAO obsDAO = new ObservacoesDAO();
            listaObservacoes = obsDAO.listarPorAluno(ra);

            // Buscar dados do aluno selecionado
            alunoSelecionado = alunoDAO.buscarPorRa(ra);
        }

        request.setAttribute("listaObservacoes", listaObservacoes);
        request.setAttribute("alunoSelecionado", alunoSelecionado);
        request.setAttribute("raSelecionado", raStr);

        request.getRequestDispatcher("/professorObservacoes.jsp")
                .forward(request, response);
    }
}