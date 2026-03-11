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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/aluno/home")
public class AlunoHomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🔵 AlunoHomeServlet INICIADO!");

        HttpSession session = request.getSession(false);

        // Verifica se o aluno está logado
        if (session == null) {
            System.out.println("❌ Sessão é nula");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        System.out.println("🔵 Sessão ID: " + session.getId());

        Object alunoObj = session.getAttribute("aluno");
        Object raObj = session.getAttribute("alunoRa");

        System.out.println("🔵 aluno na sessão: " + alunoObj);
        System.out.println("🔵 alunoRa na sessão: " + raObj);

        if (alunoObj == null || raObj == null) {
            System.out.println("❌ Aluno não está na sessão");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Pega o RA da sessão
            Integer ra = (Integer) raObj;
            System.out.println("🔵 RA do aluno: " + ra);

            // Cria lista com UM aluno (para o sidebar funcionar)
            AlunoDAO alunoDAO = new AlunoDAO();
            System.out.println("🔵 Buscando aluno no banco...");
            Aluno aluno = alunoDAO.buscarPorRa(ra);

            if (aluno == null) {
                System.out.println("❌ Aluno não encontrado no banco com RA: " + ra);
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            System.out.println("🔵 Aluno encontrado: " + aluno.getNomeCompleto());

            List<Aluno> listaAlunos = new ArrayList<>();
            listaAlunos.add(aluno);

            // Busca as notas do aluno
            System.out.println("🔵 Buscando notas...");
            AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();
            List<Avaliacao> notas = avaliacaoDAO.listarPorAluno(ra);
            System.out.println("🔵 Notas encontradas: " + (notas != null ? notas.size() : 0));

            // Busca as observações do aluno
            System.out.println("🔵 Buscando observações...");
            ObservacoesDAO observacoesDAO = new ObservacoesDAO();
            List<Observacoes> observacoes = observacoesDAO.listarPorAluno(ra);
            System.out.println("🔵 Observações encontradas: " + (observacoes != null ? observacoes.size() : 0));

            // Coloca tudo na requisição
            request.setAttribute("listaAlunos", listaAlunos);
            request.setAttribute("notas", notas != null ? notas : new ArrayList<>());
            request.setAttribute("listaObservacoes", observacoes != null ? observacoes : new ArrayList<>());

            System.out.println("🔵 Fazendo forward para home-aluno.jsp");
            request.getRequestDispatcher("/aluno/home-aluno.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("❌ Erro no AlunoHomeServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login?erro=erro_interno");
        }
    }
}