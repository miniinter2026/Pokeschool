package com.example.pokeschool.servlets;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.example.pokeschool.dao.AlunoDAO;
import com.example.pokeschool.dao.ProfessorDAO;
import com.example.pokeschool.dao.AdministradorDAO;
import com.example.pokeschool.model.Administrador;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Professor;

@WebServlet(urlPatterns = {"/login"}) // conferir form no código do front
public class Login extends HttpServlet {

    // Método que envia para
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
    }

    // Método para RECEBER os dados do formulário
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // recebe os parâmetros da página de login
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        // gurda em uma variável se é professor, aluno ou inválido
        String tipo = identificarTipo(usuario);
        System.out.println(tipo);

        if (tipo.equals("ALUNO")) {
            // ===== CHAMADA DO MÉTODO PARA ALUNO =====
            AlunoDAO alunoDAO = new AlunoDAO();
            // ja que é aluno podemos convertar a String usuario para int, para poder usá0lo no método de verficar login
            int ra = Integer.parseInt(usuario); // converte aqui
            boolean loginValido = alunoDAO.verificaLoginAluno(ra, senha);

            if (loginValido) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/home-aluno.jsp"); // arrumar caminho
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/erro.jsp"); // arrumar caminho
                rd.forward(request, response);
            }

        } else if (tipo.equals("PROFESSOR")) {
            // ===== CHAMADA DO MÉTODO PARA PROFESSOR =====
            ProfessorDAO professorDAO = new ProfessorDAO();
            // Faz a verificação se é o professor existe no banco
            boolean loginValido = professorDAO.verificaLoginProfessor(usuario, senha);


            if (loginValido) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/home-professor.jsp"); // arrumar caminho
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/erro.jsp"); // arrumar caminho
                rd.forward(request, response);
            }

        } else if (tipo.equals("ADM")) {
            // ===== CHAMADA DO MÉTODO PARA PROFESSOR =====
            AdministradorDAO administradorDAO = new AdministradorDAO();
            // Faz a verificação se é o professor existe no banco
            boolean loginValido = administradorDAO.verificaLoginAdministrador(usuario, senha);
            if (loginValido) {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/home-professor.jsp"); // arrumar caminho
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/erro.jsp"); // arrumar caminho
                rd.forward(request, response);
            }
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/erro.jsp"); // arrumar caminho
            rd.forward(request, response);
        }
    }

    private String identificarTipo(String usuario) {

        // verifica se é aluno, professor ou administrador
         if (usuario.matches("^\\d{5,6}$")) {
            return "ALUNO";

        } else if (usuario.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).+$")) {
            return "PROFESSOR";

        } else if (usuario.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9])(?=.*(?i)adm).+$")) {
            return "ADM";
        }
        return "INVALIDO";
    }
}
