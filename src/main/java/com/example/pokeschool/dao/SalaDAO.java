package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {

    public void inserir(Sala s) {

        String sql = "INSERT INTO sala (serie, letra) VALUES (?, ?)";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, s.getSerie());
            ps.setString(2, s.getLetra());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Sala> listar() {

        List<Sala> lista = new ArrayList<>();
        String sql = "SELECT * FROM sala";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Sala s = new Sala();

                s.setId(rs.getInt("id"));
                s.setSerie(rs.getInt("serie"));
                s.setLetra(rs.getString("letra"));

                lista.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
