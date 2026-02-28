package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    public boolean inserir(Administrador administrador) {
        String sql = "INSERT INTO administrador (nome, nome_usuario, senha, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, administrador.getNome());
            ps.setString(2, administrador.getNomeUsuario());
            ps.setString(3, administrador.getSenha());
            ps.setString(4, administrador.getEmail());

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Administrador> listar() {
        List<Administrador> lista = new ArrayList<>();
        String sql = "SELECT * FROM administrador";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Administrador administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                administrador.setNome(rs.getString("nome"));
                administrador.setNomeUsuario(rs.getString("nome_usuario"));
                administrador.setSenha(rs.getString("senha"));
                administrador.setEmail(rs.getString("email"));
                lista.add(administrador);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean verificaLoginAdministrador(String nomeUsuario, String senha) {
        String sql = "SELECT * FROM administrador WHERE nome_usuario = ? AND senha = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomeUsuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Administrador login(String nomeUsuario, String senha) {
        String sql = "SELECT * FROM administrador WHERE nome_usuario = ? AND senha = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomeUsuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Administrador a = new Administrador();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setNomeUsuario(rs.getString("nome_usuario"));
                a.setSenha(rs.getString("senha"));
                a.setEmail(rs.getString("email"));
                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}