package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public void inserir(Aluno a) {

        String sql = "INSERT INTO aluno (ra,nome_completo,email,senha,sala) VALUES (?,?,?,?,?)";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, a.getRa());
            ps.setString(2, a.getNomeCompleto());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getSenha());
            ps.setInt(5, a.getSala());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> listar() {

        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Aluno a = new Aluno();

                a.setRa(rs.getInt("ra"));
                a.setNomeCompleto(rs.getString("nome_completo"));
                a.setEmail(rs.getString("email"));
                a.setSenha(rs.getString("senha"));
                a.setSala(rs.getInt("sala"));

                lista.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
