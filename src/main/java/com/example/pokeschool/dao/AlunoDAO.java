package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public boolean inserir(Aluno a) {
        String sql = "INSERT INTO aluno (ra, nome_completo, email, senha, id_sala) VALUES (?,?,?,?,?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getRa());
            ps.setString(2, a.getNomeCompleto());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getSenha());
            ps.setInt(5, a.getIdSala());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Aluno> listar() {

        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Aluno a = new Aluno();

                a.setRa(rs.getInt("ra"));
                a.setNomeCompleto(rs.getString("nome_completo"));
                a.setEmail(rs.getString("email"));
                a.setSenha(rs.getString("senha"));
                a.setIdSala(rs.getInt("id_sala"));

                lista.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Aluno buscarPorRa(int ra) {
        String sql = "SELECT * FROM aluno WHERE ra = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ra);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Aluno a = new Aluno ();

                a.setRa(rs.getInt("ra"));
                a.setNomeCompleto(rs.getString("nome_completo"));
                a.setEmail(rs.getString("email"));
                a.setSenha(rs.getString("senha"));
                a.setIdSala(rs.getInt("id_sala"));

                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void atualizar(Aluno a) {
        String sql = "UPDATE aluno SET nome_completo=?, email=?, senha=?, id_sala=? WHERE ra=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getNomeCompleto());
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getSenha());
            ps.setInt(4, a.getIdSala());
            ps.setInt(5, a.getRa());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int ra) {
        String sql = "DELETE FROM aluno WHERE ra=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ra);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Aluno login(String usuario, String senha) {

        String sql = "SELECT * FROM aluno WHERE (ra=? OR email=?) AND senha=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try {
                int ra = Integer.parseInt(usuario);
                ps.setInt(1, ra);
                ps.setInt(2, ra);
            } catch (NumberFormatException e) {
                ps.setString(1, usuario);
                ps.setString(2, usuario);
            }

            ps.setString(3, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Aluno a = new Aluno();

                a.setRa(rs.getInt("ra"));
                a.setNomeCompleto(rs.getString("nome_completo"));
                a.setEmail(rs.getString("email"));
                a.setSenha(rs.getString("senha"));
                a.setIdSala(rs.getInt("id_sala"));

                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean verificaLoginAluno(int ra, String senha) {

        String sql = "SELECT * FROM aluno WHERE ra=? AND senha=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ra);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}