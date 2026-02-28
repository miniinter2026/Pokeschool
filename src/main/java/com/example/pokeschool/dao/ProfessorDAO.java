package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Administrador;
import com.example.pokeschool.model.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    private Conexao banco = new Conexao();
    private Connection conn;

    public boolean inserir(Professor professor) {
        boolean retorno = false;
        String sql = "INSERT INTO professor (nome_completo, nome_usuario, email, senha) VALUES (?, ?, ?, ?)";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, professor.getNomeCompleto());
            ps.setString(2, professor.getNomeUsuario());
            ps.setString(3, professor.getEmail());
            ps.setString(4, professor.getSenha());

            retorno = ps.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println("!!SQLException ao chamar ProfessorDAO.inserir()!!");
            sqle.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    }

    public List<Professor> listar() {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome_completo, p.nome_usuario, p.email, p.senha, " +
                "d.id AS id_disciplina, d.nome AS nome_disciplina " +
                "FROM professor p " +
                "LEFT JOIN disciplina d ON p.id = d.id_professor";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Professor p = new Professor();
                p.setId(rs.getInt("id"));
                p.setNomeCompleto(rs.getString("nome_completo"));
                p.setNomeUsuario(rs.getString("nome_usuario"));
                p.setEmail(rs.getString("email"));
                p.setSenha(rs.getString("senha"));
                p.setIdDisciplina(rs.getInt("id_disciplina"));
                p.setNomeDisciplina(rs.getString("nome_disciplina"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Conexao.desconectar(conn);
        }
        return lista;
    }

    public boolean atualizar(Professor p) {
        boolean retorno = false;
        String sql = "UPDATE professor SET nome_completo=?, nome_usuario=?, email=?, senha=? WHERE id=?";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, p.getNomeCompleto());
            ps.setString(2, p.getNomeUsuario());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getSenha());
            ps.setInt(5, p.getId());

            retorno = ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    }

    public boolean deletar(int id) {
        boolean retorno = false;
        String sql = "DELETE FROM professor WHERE id=?";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            retorno = ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    }

    public boolean verificaLoginProfessor(String nomeUsuario, String senha) {
        boolean retorno = false;
        String sql = "SELECT * FROM professor WHERE nome_usuario = ? AND senha = ?";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nomeUsuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();
            retorno = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    }

    public Professor login(String usuario, String senha) {
        String sql = "SELECT p.id, p.nome_completo, p.nome_usuario, p.email, p.senha, " +
                "d.id AS id_disciplina, d.nome AS nome_disciplina " +
                "FROM professor p " +
                "LEFT JOIN disciplina d ON p.id = d.id_professor " +
                "WHERE p.nome_usuario = ? AND p.senha = ?";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Professor p = new Professor();
                p.setId(rs.getInt("id"));
                p.setNomeCompleto(rs.getString("nome_completo"));
                p.setNomeUsuario(rs.getString("nome_usuario"));
                p.setEmail(rs.getString("email"));
                p.setSenha(rs.getString("senha"));
                p.setIdDisciplina(rs.getInt("id_disciplina"));
                p.setNomeDisciplina(rs.getString("nome_disciplina"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
        }
        return null;
    }
}