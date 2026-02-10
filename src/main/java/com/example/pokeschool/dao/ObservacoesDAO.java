package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Observacoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ObservacoesDAO {

    public void inserir(Observacoes o) {

        String sql = "INSERT INTO observacoes (data,descricao,id_disciplina,id_aluno,id_professor) VALUES (?,?,?,?,?)";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, o.getData());
            ps.setString(2, o.getDescricao());
            ps.setInt(3, o.getIdDisciplina());
            ps.setInt(4, o.getIdAluno());
            ps.setInt(5, o.getIdProfessor());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Observacoes> listar() {

        List<Observacoes> lista = new ArrayList<>();
        String sql = "SELECT * FROM observacoes";

        try {

            Connection conn = Conexao.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Observacoes ob = new Observacoes();

                ob.setId(rs.getInt("id"));
                ob.setData(rs.getDate("data"));
                ob.setDescricao(rs.getString("descricao"));
                ob.setIdDisciplina(rs.getInt("id_disciplina"));
                ob.setIdAluno(rs.getInt("id_aluno"));
                ob.setIdProfessor(rs.getInt("id_professor"));

                lista.add(ob);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
