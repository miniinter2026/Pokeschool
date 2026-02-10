package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Avaliacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    public void inserir(Avaliacao a) {

        String sql = "INSERT INTO avaliacao (id_aluno,id_disciplina,id_boletim) VALUES (?,?,?)";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, a.getIdAluno());
            ps.setInt(2, a.getIdDisciplina());
            ps.setInt(3, a.getIdBoletim());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Avaliacao> listar() {

        List<Avaliacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM avaliacao";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Avaliacao a = new Avaliacao();

                a.setId(rs.getInt("id"));
                a.setIdAluno(rs.getInt("id_aluno"));
                a.setIdDisciplina(rs.getInt("id_disciplina"));
                a.setIdBoletim(rs.getInt("id_boletim"));

                lista.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
