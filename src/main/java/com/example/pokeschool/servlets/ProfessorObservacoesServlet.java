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

@WebServlet("/professor/observacoes")
public class ProfessorObservacoesServlet extends HttpServlet {

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

        List<Observacoes> listaObservacoes = null;
        Aluno alunoSelecionado = null;

        if (raStr != null && !raStr.isEmpty()) {

            int ra = Integer.parseInt(raStr);

            ObservacoesDAO obsDAO = new ObservacoesDAO();
            listaObservacoes = obsDAO.listarPorAlunoERa(ra, disciplinaId);

            alunoSelecionado = alunoDAO.buscarPorRa(ra);
        }

        request.setAttribute("listaObservacoes", listaObservacoes);
        request.setAttribute("alunoSelecionado", alunoSelecionado);
        request.setAttribute("raSelecionado", raStr);

        request.getRequestDispatcher("/professor/professorDashboard.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        int professorId = (Integer) session.getAttribute("professorId");

        String acao = request.getParameter("acao");
        int ra = Integer.parseInt(request.getParameter("ra"));

        ObservacoesDAO dao = new ObservacoesDAO();

        if ("inserir".equals(acao)) {

            int disciplinaId = Integer.parseInt(request.getParameter("disciplinaId"));
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

        response.sendRedirect("observacoes?ra=" + ra);
    }
}