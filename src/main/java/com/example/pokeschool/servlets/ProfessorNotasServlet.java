package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.dao.AvaliacaoDAO;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Avaliacao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/professor/notas")
public class ProfessorNotasServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Carregar lista de alunos para o dropdown
        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> listaAlunos = alunoDAO.listar();
        request.setAttribute("listaAlunos", listaAlunos);

        // Verificar se foi selecionado um aluno
        String raStr = request.getParameter("ra");
        List<Avaliacao> listaAvaliacao = null;
        Aluno alunoSelecionado = null;

        if (raStr != null && !raStr.isEmpty()) {
            int ra = Integer.parseInt(raStr);

            AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
            listaAvaliacao = avaliacaoDAO.listarPorAluno(ra);

            // Buscar dados do aluno selecionado
            alunoSelecionado = alunoDAO.buscarPorRa(ra);
        }

        request.setAttribute("listaAvaliacao", listaAvaliacao);
        request.setAttribute("alunoSelecionado", alunoSelecionado);
        request.setAttribute("raSelecionado", raStr);

        request.getRequestDispatcher("/professorNotas.jsp")
                .forward(request, response);
    }
}