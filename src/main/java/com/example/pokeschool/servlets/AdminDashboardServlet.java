package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.dao.ProfessorDAO;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Professor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> listaAlunos = alunoDAO.listar();

        System.out.println("📊 Servlet: Total de alunos = " + listaAlunos.size()); // DEBUG

        request.setAttribute("listaAlunos", listaAlunos);

        ProfessorDAO professorDAO = new ProfessorDAO();
        List<Professor> listaProfessores = professorDAO.listar();

        System.out.println("📊 Servlet: Total de professores = " + listaProfessores.size()); // DEBUG

        request.setAttribute("listaProfessores", listaProfessores);

        request.getRequestDispatcher("/area-restrita/index.jsp")
                .forward(request, response);
    }
}