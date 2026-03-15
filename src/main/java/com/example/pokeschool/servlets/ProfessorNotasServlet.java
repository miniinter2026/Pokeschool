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
        Integer disciplinaId = (Integer) session.getAttribute("professorDisciplina");

        try {
            if ("inserir".equals(acao)) {
                int ra = Integer.parseInt(request.getParameter("ra"));

                //n1 e n2 como opcionais
                Double n1 = null;
                Double n2 = null;

                String n1Param = request.getParameter("n1");
                String n2Param = request.getParameter("n2");

                if (n1Param != null && !n1Param.trim().isEmpty()) {
                    n1 = Double.parseDouble(n1Param);
                }

                if (n2Param != null && !n2Param.trim().isEmpty()) {
                    n2 = Double.parseDouble(n2Param);
                }

                AvaliacaoDAO dao = new AvaliacaoDAO();
                Avaliacao av = new Avaliacao();
                av.setIdAluno(ra);
                av.setIdDisciplina(disciplinaId);
                av.setN1(n1);
                av.setN2(n2);

                dao.salvar(av);
                System.out.println("nota inserida para RA: " + ra + " (N1=" + n1 + ", N2=" + n2 + ")");
            }
            else if ("editar".equals(acao)) {
                int idBoletim = Integer.parseInt(request.getParameter("idBoletim"));

                //n1 e n2 como opcionais
                Double n1 = null;
                Double n2 = null;

                String n1Param = request.getParameter("n1");
                String n2Param = request.getParameter("n2");

                if (n1Param != null && !n1Param.trim().isEmpty()) {
                    n1 = Double.parseDouble(n1Param);
                }

                if (n2Param != null && !n2Param.trim().isEmpty()) {
                    n2 = Double.parseDouble(n2Param);
                }

                AvaliacaoDAO dao = new AvaliacaoDAO();
                Avaliacao av = new Avaliacao();
                av.setIdBoletim(idBoletim);
                av.setN1(n1);
                av.setN2(n2);

                dao.atualizar(av);
                System.out.println("nota atualizada ID: " + idBoletim + " (N1=" + n1 + ", N2=" + n2 + ")");
            }
            else if ("excluir".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                AvaliacaoDAO dao = new AvaliacaoDAO();
                dao.deletar(id);
                System.out.println("nota excluída ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("erro no ProfessorNotasServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/html/erro.html");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/professor/dashboard");
    }
}