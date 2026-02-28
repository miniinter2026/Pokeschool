package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Boletim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoletimDAO {

    public void inserir(Boletim b) {

        String sql = "INSERT INTO boletim (n1,n2) VALUES (?,?)";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, b.getN1());
            ps.setDouble(2, b.getN2());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Boletim> listar() {

        List<Boletim> lista = new ArrayList<>();
        String sql = "SELECT * FROM boletim";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Boletim b = new Boletim();

                b.setId(rs.getInt("id"));
                b.setN1(rs.getDouble("n1"));
                b.setN2(rs.getDouble("n2"));

                lista.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
