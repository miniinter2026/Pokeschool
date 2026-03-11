package com.example.pokeschool.servlets;

import com.example.pokeschool.dao.ProfessorDAO;
import com.example.pokeschool.dao.DisciplinaDAO;
import com.example.pokeschool.model.Professor;
import com.example.pokeschool.model.Disciplina;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/adminProfessores")
public class AdminProfessoresServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            ProfessorDAO dao = new ProfessorDAO();
            List<Professor> lista = dao.listarComDisciplinas();

            DisciplinaDAO discDao = new DisciplinaDAO();
            List<Disciplina> disciplinas = discDao.listarTodas();

            request.setAttribute("listaProfessores", lista);
            request.setAttribute("disciplinas", disciplinas);

            request.getRequestDispatcher("/area-restrita/index.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            System.err.println("❌ Erro ao listar professores: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/html/erro.html");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String acao = request.getParameter("acao");
        System.out.println("🔵 AdminProfessores - Ação: " + acao);

        try {
            ProfessorDAO dao = new ProfessorDAO();
            DisciplinaDAO discDao = new DisciplinaDAO();

            if ("inserir".equals(acao)) {
                String nome = request.getParameter("nome");
                String usuario = request.getParameter("usuario");
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                int disciplinaId = Integer.parseInt(request.getParameter("disciplina"));

                Professor p = new Professor();
                p.setNomeCompleto(nome);
                p.setNomeUsuario(usuario);
                p.setEmail(email);
                p.setSenha(senha);

                boolean inseriu = dao.inserir(p);
                System.out.println("✅ Professor inserido: " + (inseriu ? "SIM" : "NÃO"));

                if (inseriu) {
                    // Busca o ID do professor recém-criado pelo nome de usuário
                    Professor prof = dao.buscarPorUsuario(usuario);
                    if (prof != null) {
                        discDao.vincularProfessor(disciplinaId, prof.getId());
                    }
                }
            }
            else if ("editar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nome = request.getParameter("nome");
                String usuario = request.getParameter("usuario");
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                int disciplinaId = Integer.parseInt(request.getParameter("disciplina"));

                Professor p = new Professor();
                p.setId(id);
                p.setNomeCompleto(nome);
                p.setNomeUsuario(usuario);
                p.setEmail(email);

                if (senha != null && !senha.trim().isEmpty()) {
                    p.setSenha(senha);
                }

                // Antes de atualizar, desvincula o professor das disciplinas antigas
                discDao.desvincularProfessor(id);

                boolean atualizou = dao.atualizar(p);
                System.out.println("✅ Professor atualizado ID " + id + ": " + (atualizou ? "SIM" : "NÃO"));

                if (atualizou) {
                    // Vincula a nova disciplina
                    discDao.vincularProfessor(disciplinaId, id);
                }
            }
            else if ("excluir".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));

                // Desvincula as disciplinas antes de excluir
                discDao.desvincularProfessor(id);

                boolean deletou = dao.deletar(id);
                System.out.println("✅ Professor excluído ID " + id + ": " + (deletou ? "SIM" : "NÃO"));
            }

            response.sendRedirect(request.getContextPath() + "/adminDashboard");

        } catch (Exception e) {
            System.err.println("❌ Erro no AdminProfessoresServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/html/erro.html");
        }
    }
}