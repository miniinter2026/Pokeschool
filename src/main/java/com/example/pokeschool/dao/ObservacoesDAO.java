package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Observacoes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObservacoesDAO {

    public void inserir(Observacoes o) {
        String sql = "INSERT INTO observacoes (data, descricao, id_disciplina, id_aluno, id_professor) VALUES (?,?,?,?,?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(o.getData().getTime()));
            ps.setString(2, o.getDescricao());
            ps.setInt(3, o.getIdDisciplina());
            ps.setInt(4, o.getIdAluno());
            ps.setInt(5, o.getIdProfessor());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Observacoes o) {
        String sql = "UPDATE observacoes SET descricao = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getDescricao());
            ps.setInt(2, o.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM observacoes WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Observacoes> listarPorAluno(int idAluno) {
        List<Observacoes> lista = new ArrayList<>();
        String sql = "SELECT o.id, o.data, o.descricao, d.nome AS disciplina, p.nome_completo AS professor " +
                "FROM observacoes o " +
                "JOIN disciplina d ON o.id_disciplina = d.id " +
                "JOIN professor p ON o.id_professor = p.id " +
                "WHERE o.id_aluno = ? ORDER BY o.data DESC";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAluno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Observacoes o = new Observacoes();
                o.setId(rs.getInt("id"));
                o.setData(rs.getDate("data"));
                o.setDescricao(rs.getString("descricao"));
                o.setNomeDisciplina(rs.getString("disciplina"));
                o.setNomeProfessor(rs.getString("professor"));
                lista.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Observacoes buscarPorId(int id) {
        String sql = "SELECT * FROM observacoes WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Observacoes o = new Observacoes();
                o.setId(rs.getInt("id"));
                o.setData(rs.getDate("data"));
                o.setDescricao(rs.getString("descricao"));
                o.setIdDisciplina(rs.getInt("id_disciplina"));
                o.setIdAluno(rs.getInt("id_aluno"));
                o.setIdProfessor(rs.getInt("id_professor"));
                return o;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}