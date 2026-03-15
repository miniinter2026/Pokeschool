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

    @Override
    public void init() throws ServletException {
        System.out.println("ProfessorDashboardServlet INICIADO!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("professorId") == null) {
            System.out.println("Professor não logado, redirecionando para login");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer disciplinaId = (Integer) session.getAttribute("professorDisciplina");
        Integer professorId = (Integer) session.getAttribute("professorId");

        System.out.println("Professor ID: " + professorId);
        System.out.println("Disciplina ID: " + disciplinaId);

        AlunoDAO alunoDAO = new AlunoDAO();
        String termoBusca = request.getParameter("busca");

        List<Aluno> listaAlunos;
        if (termoBusca != null && !termoBusca.isEmpty()) {
            listaAlunos = alunoDAO.buscarPorNomeOuRa(termoBusca);
        } else {
            listaAlunos = alunoDAO.listar();
        }

        System.out.println("Total de alunos: " + listaAlunos.size());

        request.setAttribute("listaAlunos", listaAlunos);
        request.setAttribute("termoBusca", termoBusca);
        request.setAttribute("disciplinaId", disciplinaId);
        request.setAttribute("professorId", professorId);

        System.out.println("Forward para professorDashboard.jsp");
        request.getRequestDispatcher("/professor/professorDashboard.jsp")
                .forward(request, response);
    }
}