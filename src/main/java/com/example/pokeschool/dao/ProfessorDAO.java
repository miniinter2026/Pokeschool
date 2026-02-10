package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public void inserir(Professor p) {

        String sql = "INSERT INTO professor (nome_completo, nome_usuario, email, senha) VALUES (?, ?, ?, ?)";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, p.getNomeCompleto());
            ps.setString(2, p.getNomeUsuario());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getSenha());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Professor> listar() {

        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT * FROM professor";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Professor p = new Professor();

                p.setId(rs.getInt("id"));
                p.setNomeCompleto(rs.getString("nome_completo"));
                p.setNomeUsuario(rs.getString("nome_usuario"));
                p.setEmail(rs.getString("email"));
                p.setSenha(rs.getString("senha"));

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
