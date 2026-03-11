package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public Professor login(String usuario, String senha) {
        Professor professor = null;
        String sql = "SELECT p.id, p.nome_completo, p.nome_usuario, p.senha, p.email, " +
                "d.id AS id_disciplina, d.nome AS nome_disciplina " +
                "FROM professor p " +
                "LEFT JOIN disciplina d ON p.id = d.id_professor " +
                "WHERE p.nome_usuario = ? AND p.senha = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNomeCompleto(rs.getString("nome_completo"));
                professor.setNomeUsuario(rs.getString("nome_usuario"));
                professor.setSenha(rs.getString("senha"));
                professor.setEmail(rs.getString("email"));

                if (!rs.wasNull() && rs.getObject("id_disciplina") != null) {
                    professor.setIdDisciplina(rs.getInt("id_disciplina"));
                    professor.setNomeDisciplina(rs.getString("nome_disciplina"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return professor;
    }

    public boolean verificaLoginProfessor(String usuario, String senha) {
        String sql = "SELECT COUNT(*) FROM professor WHERE nome_usuario = ? AND senha = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Professor> listar() {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome_completo, p.nome_usuario, p.senha, p.email, " +
                "d.id AS id_disciplina, d.nome AS nome_disciplina " +
                "FROM professor p " +
                "LEFT JOIN disciplina d ON p.id = d.id_professor";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Professor p = new Professor();
                p.setId(rs.getInt("id"));
                p.setNomeCompleto(rs.getString("nome_completo"));
                p.setNomeUsuario(rs.getString("nome_usuario"));
                p.setSenha(rs.getString("senha"));
                p.setEmail(rs.getString("email"));

                if (!rs.wasNull() && rs.getObject("id_disciplina") != null) {
                    p.setIdDisciplina(rs.getInt("id_disciplina"));
                    p.setNomeDisciplina(rs.getString("nome_disciplina"));
                }

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Professor> listarComDisciplinas() {
        return listar();
    }

    public Professor buscarPorId(int id) {
        Professor professor = null;
        String sql = "SELECT p.id, p.nome_completo, p.nome_usuario, p.senha, p.email, " +
                "d.id AS id_disciplina, d.nome AS nome_disciplina " +
                "FROM professor p " +
                "LEFT JOIN disciplina d ON p.id = d.id_professor " +
                "WHERE p.id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNomeCompleto(rs.getString("nome_completo"));
                professor.setNomeUsuario(rs.getString("nome_usuario"));
                professor.setSenha(rs.getString("senha"));
                professor.setEmail(rs.getString("email"));

                if (!rs.wasNull() && rs.getObject("id_disciplina") != null) {
                    professor.setIdDisciplina(rs.getInt("id_disciplina"));
                    professor.setNomeDisciplina(rs.getString("nome_disciplina"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return professor;
    }

    // ✅ MÉTODO ADICIONADO: buscarPorUsuario
    public Professor buscarPorUsuario(String usuario) {
        Professor professor = null;
        String sql = "SELECT * FROM professor WHERE nome_usuario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNomeCompleto(rs.getString("nome_completo"));
                professor.setNomeUsuario(rs.getString("nome_usuario"));
                professor.setEmail(rs.getString("email"));
                professor.setSenha(rs.getString("senha"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professor;
    }

    public boolean inserir(Professor p) {
        String sql = "INSERT INTO professor (nome_completo, nome_usuario, senha, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNomeCompleto());
            ps.setString(2, p.getNomeUsuario());
            ps.setString(3, p.getSenha());
            ps.setString(4, p.getEmail());

            int rowsAffected = ps.executeUpdate();
            System.out.println("✅ ProfessorDAO.inserir: " + rowsAffected + " linha(s) afetada(s)");
            return rowsAffected > 0;

        } catch (Exception e) {
            System.err.println("❌ Erro ao inserir professor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Professor p) {
        if (p.getSenha() != null && !p.getSenha().isEmpty()) {
            String sql = "UPDATE professor SET nome_completo = ?, nome_usuario = ?, senha = ?, email = ? WHERE id = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, p.getNomeCompleto());
                ps.setString(2, p.getNomeUsuario());
                ps.setString(3, p.getSenha());
                ps.setString(4, p.getEmail());
                ps.setInt(5, p.getId());

                int rowsAffected = ps.executeUpdate();
                System.out.println("✅ ProfessorDAO.atualizar (com senha): " + rowsAffected + " linha(s) afetada(s)");
                return rowsAffected > 0;

            } catch (Exception e) {
                System.err.println("❌ Erro ao atualizar professor (com senha): " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            String sql = "UPDATE professor SET nome_completo = ?, nome_usuario = ?, email = ? WHERE id = ?";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, p.getNomeCompleto());
                ps.setString(2, p.getNomeUsuario());
                ps.setString(3, p.getEmail());
                ps.setInt(4, p.getId());

                int rowsAffected = ps.executeUpdate();
                System.out.println("✅ ProfessorDAO.atualizar (sem senha): " + rowsAffected + " linha(s) afetada(s)");
                return rowsAffected > 0;

            } catch (Exception e) {
                System.err.println("❌ Erro ao atualizar professor (sem senha): " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean deletar(int id) {
        String checkSql = "SELECT id FROM disciplina WHERE id_professor = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement checkPs = conn.prepareStatement(checkSql)) {

            checkPs.setInt(1, id);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                System.out.println("⚠️ Professor ID " + id + " possui disciplina vinculada, removendo vínculo primeiro");

                String updateSql = "UPDATE disciplina SET id_professor = NULL WHERE id_professor = ?";
                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setInt(1, id);
                    updatePs.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Erro ao verificar disciplina do professor: " + e.getMessage());
            e.printStackTrace();
        }

        String sql = "DELETE FROM professor WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println("✅ ProfessorDAO.deletar: " + rowsAffected + " linha(s) afetada(s)");
            return rowsAffected > 0;

        } catch (Exception e) {
            System.err.println("❌ Erro ao excluir professor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}