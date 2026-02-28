package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Avaliacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    public List<Avaliacao> listarPorAluno(int ra) {
        List<Avaliacao> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.id_boletim, d.nome AS disciplina, b.n1, b.n2 " +
                "FROM avaliacao a " +
                "JOIN disciplina d ON a.id_disciplina = d.id " +
                "JOIN boletim b ON a.id_boletim = b.id " +
                "WHERE a.id_aluno = ? ORDER BY a.id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ra);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Avaliacao a = new Avaliacao();
                a.setId(rs.getInt("id"));
                a.setIdBoletim(rs.getInt("id_boletim"));
                a.setNomeDisciplina(rs.getString("disciplina"));
                a.setN1(rs.getDouble("n1"));
                a.setN2(rs.getDouble("n2"));
                lista.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void salvar(Avaliacao a) {
        String sqlBoletim = "INSERT INTO boletim (n1, n2) VALUES (?, ?) RETURNING id";
        String sqlAvaliacao = "INSERT INTO avaliacao (id_aluno, id_disciplina, id_boletim) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);

            int idBoletim;
            try (PreparedStatement ps = conn.prepareStatement(sqlBoletim)) {
                ps.setDouble(1, a.getN1());
                ps.setDouble(2, a.getN2());
                ResultSet rs = ps.executeQuery();
                rs.next();
                idBoletim = rs.getInt(1);
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlAvaliacao)) {
                ps2.setInt(1, a.getIdAluno());
                ps2.setInt(2, a.getIdDisciplina());
                ps2.setInt(3, idBoletim);
                ps2.executeUpdate();
            }

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Avaliacao a) {
        String sql = "UPDATE boletim SET n1 = ?, n2 = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, a.getN1());
            ps.setDouble(2, a.getN2());
            ps.setInt(3, a.getIdBoletim());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ NOVO MÉTODO DELETAR
    public void deletar(int id) {
        String sqlGetBoletim = "SELECT id_boletim FROM avaliacao WHERE id = ?";
        String sqlDeleteAvaliacao = "DELETE FROM avaliacao WHERE id = ?";
        String sqlDeleteBoletim = "DELETE FROM boletim WHERE id = ?";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);

            int idBoletim = 0;

            // Pegar o id do boletim antes de deletar a avaliação
            try (PreparedStatement ps = conn.prepareStatement(sqlGetBoletim)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    idBoletim = rs.getInt("id_boletim");
                }
            }

            // Deletar a avaliação
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteAvaliacao)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // Deletar o boletim
            if (idBoletim > 0) {
                try (PreparedStatement ps = conn.prepareStatement(sqlDeleteBoletim)) {
                    ps.setInt(1, idBoletim);
                    ps.executeUpdate();
                }
            }

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}