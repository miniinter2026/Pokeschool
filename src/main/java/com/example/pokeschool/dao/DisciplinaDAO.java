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

        String sql = "INSERT INTO disciplina (nome,id_professor) VALUES (?,?)";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

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

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

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
}
