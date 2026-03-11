package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.dao.ProfessorDAO;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Professor;
import com.example.pokeschool.model.Administrador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar se o admin está logado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> listaAlunos = alunoDAO.listar();

        System.out.println("Servlet: Total de alunos = " + listaAlunos.size());

        request.setAttribute("listaAlunos", listaAlunos);

        ProfessorDAO professorDAO = new ProfessorDAO();
        List<Professor> listaProfessores = professorDAO.listar();

        System.out.println("Servlet: Total de professores = " + listaProfessores.size());

        request.setAttribute("listaProfessores", listaProfessores);

        request.getRequestDispatcher("/area-restrita/index.jsp")
                .forward(request, response);
    }
}