package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.dao.AvaliacaoDAO;
import com.example.pokeschool.dao.ObservacoesDAO;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Avaliacao;
import com.example.pokeschool.model.Observacoes;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(
        urlPatterns = {"/professor/dashboard"},
        loadOnStartup = 1
)
public class ProfessorDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("professorId") == null) {
            response.sendRedirect("login");
            return;
        }

        Integer disciplinaId = (Integer) session.getAttribute("professorDisciplina");

        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> listaAlunos = alunoDAO.listar();
        request.setAttribute("listaAlunos", listaAlunos);

        String raStr = request.getParameter("ra");

        List<Avaliacao> listaAvaliacao = null;
        List<Observacoes> listaObservacoes = null;
        Aluno alunoSelecionado = null;

        if (raStr != null && !raStr.isEmpty()) {

            int ra = Integer.parseInt(raStr);

            AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
            listaAvaliacao = avaliacaoDAO.listarPorAlunoERa(ra, disciplinaId);

            ObservacoesDAO obsDAO = new ObservacoesDAO();
            listaObservacoes = obsDAO.listarPorAlunoERa(ra, disciplinaId);

            alunoSelecionado = alunoDAO.buscarPorRa(ra);
        }

        request.setAttribute("listaAvaliacao", listaAvaliacao);
        request.setAttribute("listaObservacoes", listaObservacoes);
        request.setAttribute("alunoSelecionado", alunoSelecionado);
        request.setAttribute("raSelecionado", raStr);

        request.getRequestDispatcher("/professor/professorDashboard.jsp")
                .forward(request, response);
    }
}