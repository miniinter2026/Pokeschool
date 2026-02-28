package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Boletim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoletimDAO {

    public void inserir(Boletim b) {
        String sql = "INSERT INTO boletim (n1,n2) VALUES (?,?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, b.getN1());
            ps.setDouble(2, b.getN2());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Boletim b) {
        String sql = "UPDATE boletim SET n1 = ?, n2 = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, b.getN1());
            ps.setDouble(2, b.getN2());
            ps.setInt(3, b.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM boletim WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boletim buscarPorId(int id) {
        Boletim b = null;
        String sql = "SELECT * FROM boletim WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                b = new Boletim();
                b.setId(rs.getInt("id"));
                b.setN1(rs.getDouble("n1"));
                b.setN2(rs.getDouble("n2"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}