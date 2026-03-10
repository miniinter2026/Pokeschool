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

@WebServlet(
        urlPatterns = {"/professor/notas"},
        loadOnStartup = 1
)
public class ProfessorNotasServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("✅ ProfessorNotasServlet INICIADO!");
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

        AvaliacaoDAO dao = new AvaliacaoDAO();

        if ("inserir".equals(acao)) {
            double n1 = Double.parseDouble(request.getParameter("n1"));
            double n2 = Double.parseDouble(request.getParameter("n2"));

            Avaliacao av = new Avaliacao();
            av.setIdAluno(ra);
            av.setIdDisciplina(disciplinaId);
            av.setN1(n1);
            av.setN2(n2);

            dao.salvar(av);
        }

        else if ("editar".equals(acao)) {
            int idBoletim = Integer.parseInt(request.getParameter("idBoletim"));
            double n1 = Double.parseDouble(request.getParameter("n1"));
            double n2 = Double.parseDouble(request.getParameter("n2"));

            Avaliacao av = new Avaliacao();
            av.setIdBoletim(idBoletim);
            av.setN1(n1);
            av.setN2(n2);

            dao.atualizar(av);
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