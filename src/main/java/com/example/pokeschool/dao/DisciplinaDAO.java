package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    public void inserir(Disciplina d) {
        String sql = "INSERT INTO disciplina (nome, id_professor) VALUES (?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNome());
            ps.setInt(2, d.getIdProfessor());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Disciplina> listar() {
        List<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM disciplina";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Disciplina d = new Disciplina();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setIdProfessor(rs.getInt("id_professor"));
                lista.add(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Disciplina> listarTodas() {
        return listar();
    }

    public void vincularProfessor(int idDisciplina, int idProfessor) {
        String sql = "UPDATE disciplina SET id_professor = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProfessor);
            ps.setInt(2, idDisciplina);
            ps.executeUpdate();
            System.out.println("Disciplina " + idDisciplina + " vinculada ao professor " + idProfessor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desvincularProfessor(int idProfessor) {
        String sql = "UPDATE disciplina SET id_professor = NULL WHERE id_professor = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProfessor);
            ps.executeUpdate();
            System.out.println("Professor " + idProfessor + " desvinculado das disciplinas");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}