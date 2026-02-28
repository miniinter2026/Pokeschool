package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Aluno;
import com.example.pokeschool.model.Token;

import java.security.SecureRandom;
import java.sql.*;

public class TokenDAO {
    private Conexao banco = new Conexao();
    private Connection conn;

    public boolean inserir(Token token){
        boolean retorno = false;
        try{
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO token(token, email_usuario, data_criacao, data_expiracao, utilizado) VALUES(?, ?, ?, ?, ?)");

            ps.setString(1, token.getToken());
            ps.setString(2, token.getEmailUsuario());
            ps.setTimestamp(3, Timestamp.valueOf(token.getDataCriacao()));
            ps.setTimestamp(4, Timestamp.valueOf(token.getDataExpiracao()));
            ps.setBoolean(5, token.isUtilizado());

            retorno = ps.executeUpdate() == 1;
        }
        catch(SQLException sqle){
            System.out.println("!!SQLException ao chamar AlunoDAO.inserir(aluno)!!");
            sqle.printStackTrace();
        }
        finally{
            banco.desconectar(conn);
            return retorno;
        }
    } // Método que inseri um token na tabela

    private static final SecureRandom random = new SecureRandom();

    public String gerarToken6Digitos() {
        int numero = 100000 + random.nextInt(900000);
        return String.valueOf(numero);
    } // método que gera os tokens

    public Token buscarPorToken(String token) {
        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM token WHERE token = ?");
            ps.setString(1, token);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Token(
                        rs.getInt("id"),
                        rs.getString("token"),
                        rs.getString("email_usuario"),
                        rs.getTimestamp("data_criacao").toLocalDateTime(),
                        rs.getTimestamp("data_expiracao").toLocalDateTime(),
                        rs.getBoolean("utilizado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
        }
        return null;
    } // verfifica se o token é válido

    public boolean marcarComoUtilizado(String token) {
        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE token SET utilizado = TRUE WHERE token = ?");
            ps.setString(1, token);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
        }
        return false;
    } // atualiza o token como utilizado

    public boolean emailExiste(String email) {
        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id FROM aluno WHERE email = ? UNION SELECT id FROM professor WHERE email = ?");
            ps.setString(1, email);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true se encontrou em alguma tabela
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
        }
        return false;
    } // verifica se o email existe na tabela
}
