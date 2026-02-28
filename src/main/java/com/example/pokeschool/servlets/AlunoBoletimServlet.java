package com.example.pokeschool.servlets;
import com.example.pokeschool.dao.AvaliacaoDAO;
import com.example.pokeschool.model.Avaliacao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/aluno/boletim")
public class AlunoBoletimServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int ra = Integer.parseInt(request.getParameter("ra"));

            AvaliacaoDAO dao = new AvaliacaoDAO();
            List<Avaliacao> lista = dao.listarPorAluno(ra);

            request.setAttribute("boletins", lista);
            request.getRequestDispatcher("/boletim.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao carregar boletim.");
        }
    }
}