package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Administrador;
import com.example.pokeschool.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {
    private Conexao banco = new Conexao();
    private Connection conn;

    public boolean inserir(Administrador administrador){
        boolean retorno = false;
        try{
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO administrador (nome, nome_usuario, senha) VALUES (?,?, ?)");

            ps.setString(1, administrador.getNome());
            ps.setString(2, administrador.getNomeUsuario());
            ps.setString(3, administrador.getSenha());

            retorno = ps.executeUpdate() == 1;
        }
        catch(SQLException sqle){
            System.out.println("!!SQLException ao chamar AdministradorDAO.inserir(administrador)!!");
            sqle.printStackTrace();
        }
        finally{
            banco.desconectar(conn);
            return retorno;
        }
    } // Método que inseri um administrador na tabela

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

                Administrador administrador = new Administrador(
                        rs.getString("nome_usuario"),
                        rs.getString("nome"),
                        rs.getString("senha")
                );

                lista.add(administrador);
            }

        } catch (Exception e) {
            System.out.println("!!Exception ao chamar AdministradorDAO.listar()!!");
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
        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM administrador WHERE nome_usuario LIKE ? AND senha LIKE ?");
            ps.setString(1,nomeUsuario);
            ps.setString(2,senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                retorno = true;
            }
        } catch (Exception e) {
            System.out.println("!!SQLException ao chamar AdministradorDAO.verificaLoginAdmin(nomeUsuario ,senha)!!");
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    } // Método que verifica se existe aquela conta de administrador
}
