package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    private Conexao banco = new Conexao();
    private Connection conn;

    public boolean inserir(Administrador administrador) {
        boolean retorno = false;
        String sql = "INSERT INTO administrador (nome, nome_usuario, senha, email) VALUES (?, ?, ?, ?)";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, administrador.getNome());
            ps.setString(2, administrador.getNomeUsuario());
            ps.setString(3, administrador.getSenha());
            ps.setString(4, administrador.getEmail());

            retorno = ps.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println("!!SQLException ao chamar AdministradorDAO.inserir()!!");
            sqle.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    }

    public List<Administrador> listar() {
        List<Administrador> lista = new ArrayList<>();
        String sql = "SELECT * FROM administrador";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

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
            System.out.println("!!Exception ao chamar AdministradorDAO.listar()!!");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Conexao.desconectar(conn);
        }
        return lista;
    }

    public boolean verificaLoginAdministrador(String nomeUsuario, String senha) {
        boolean retorno = false;
        String sql = "SELECT * FROM administrador WHERE nome_usuario = ? AND senha = ?";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nomeUsuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                retorno = true;
            }
        } catch (Exception e) {
            System.out.println("!!SQLException ao chamar AdministradorDAO.verificaLoginAdmin()!!");
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    }

    public Administrador login(String nomeUsuario, String senha) {
        String sql = "SELECT * FROM administrador WHERE nome_usuario = ? AND senha = ?";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
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
        } finally {
            banco.desconectar(conn);
        }
        return null;
    }
}