package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public boolean inserir(Professor p) {

        String sql = "INSERT INTO professor (nome_completo, nome_usuario, email, senha) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNomeCompleto());
            ps.setString(2, p.getNomeUsuario());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getSenha());

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Professor> listar() {

        List<Professor> lista = new ArrayList<>();

        String sql =
                "SELECT p.id, p.nome_completo, p.nome_usuario, p.email, p.senha, " +
                        "d.id AS id_disciplina, d.nome AS nome_disciplina " +
                        "FROM professor p " +
                        "LEFT JOIN disciplina d ON p.id = d.id_professor";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        }

        return lista;
    }

    public void atualizar(Professor p) {

        String sql =
                "UPDATE professor SET nome_completo=?, nome_usuario=?, email=?, senha=? WHERE id=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNomeCompleto());
            ps.setString(2, p.getNomeUsuario());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getSenha());
            ps.setInt(5, p.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {

        String sql = "DELETE FROM professor WHERE id=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Professor login(String usuario, String senha) {

        String sql =
                "SELECT p.id, p.nome_completo, p.nome_usuario, p.email, p.senha, " +
                        "d.id AS id_disciplina, d.nome AS nome_disciplina " +
                        "FROM professor p " +
                        "LEFT JOIN disciplina d ON p.id = d.id_professor " +
                        "WHERE p.nome_usuario=? AND p.senha=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
        }

        return null;
    }

    public boolean verificaLoginProfessor(String usuario, String senha) {

        String sql =
                "SELECT * FROM professor WHERE nome_usuario=? AND senha=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}