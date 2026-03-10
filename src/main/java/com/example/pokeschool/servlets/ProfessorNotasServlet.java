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
        Aluno alunoSelecionado = null;

        if (raStr != null && !raStr.isEmpty()) {

            int ra = Integer.parseInt(raStr);

            AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
            listaAvaliacao = avaliacaoDAO.listarPorAlunoERa(ra, disciplinaId);

            alunoSelecionado = alunoDAO.buscarPorRa(ra);
        }

        request.setAttribute("listaAvaliacao", listaAvaliacao);
        request.setAttribute("alunoSelecionado", alunoSelecionado);
        request.setAttribute("raSelecionado", raStr);

        request.getRequestDispatcher("/professor/professorDashboard.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        int ra = Integer.parseInt(request.getParameter("ra"));

        AvaliacaoDAO dao = new AvaliacaoDAO();

        if ("inserir".equals(acao)) {

            int disciplinaId = Integer.parseInt(request.getParameter("disciplinaId"));
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

        response.sendRedirect("notas?ra=" + ra);
    }
}