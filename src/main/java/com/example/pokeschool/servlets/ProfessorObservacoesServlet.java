package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.dao.ObservacoesDAO;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Observacoes;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(
        urlPatterns = {"/professor/observacoes"},
        loadOnStartup = 1
)
public class ProfessorObservacoesServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("ProfessorObservacoesServlet INICIADO!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("professorId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer disciplinaId = (Integer) session.getAttribute("professorDisciplina");
        Integer professorId = (Integer) session.getAttribute("professorId");

        AlunoDAO alunoDAO = new AlunoDAO();
        String termoBusca = request.getParameter("busca");

        List<Aluno> listaAlunos;
        if (termoBusca != null && !termoBusca.isEmpty()) {
            listaAlunos = alunoDAO.buscarPorNomeOuRa(termoBusca);
        } else {
            listaAlunos = alunoDAO.listar();
        }

        request.setAttribute("listaAlunos", listaAlunos);
        request.setAttribute("termoBusca", termoBusca);
        request.setAttribute("disciplinaId", disciplinaId);
        request.setAttribute("professorId", professorId);

        request.getRequestDispatcher("/professor/professorDashboard.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("professorId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String acao = request.getParameter("acao");
        int ra = Integer.parseInt(request.getParameter("ra"));
        Integer disciplinaId = (Integer) session.getAttribute("professorDisciplina");
        Integer professorId = (Integer) session.getAttribute("professorId");

        ObservacoesDAO dao = new ObservacoesDAO();

        if ("inserir".equals(acao)) {
            String descricao = request.getParameter("descricao");

            Observacoes o = new Observacoes();
            o.setIdAluno(ra);
            o.setIdDisciplina(disciplinaId);
            o.setDescricao(descricao);
            o.setIdProfessor(professorId);
            o.setData(new Date(System.currentTimeMillis()));

            dao.inserir(o);
        }

        else if ("editar".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String descricao = request.getParameter("descricao");

            Observacoes o = new Observacoes();
            o.setId(id);
            o.setDescricao(descricao);

            dao.atualizar(o);
        }

        else if ("excluir".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deletar(id);
        }

        String termoBusca = request.getParameter("busca");
        if (termoBusca != null && !termoBusca.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/professor/dashboard?busca=" + termoBusca);
        } else {
            response.sendRedirect(request.getContextPath() + "/professor/dashboard");
        }
    }
}