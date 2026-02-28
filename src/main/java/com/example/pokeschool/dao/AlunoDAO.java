package com.example.pokeschool.dao;

import com.example.pokeschool.connection.Conexao;
import com.example.pokeschool.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private Conexao banco = new Conexao();
    private Connection conn;

    public boolean inserir(Aluno aluno){
        boolean retorno = false;
        try{
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO aluno (ra, nome_completo, email, senha, id_sala) VALUES (?,?,?,?,?)");

            ps.setInt(1, aluno.getRa());
            ps.setString(2, aluno.getNomeCompleto());
            ps.setString(3, aluno.getEmail());
            ps.setString(4, aluno.getSenha());
            ps.setInt(5, aluno.getIdSala());

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
    } // Método que inseri um aluno na tabela

    public List<Aluno> listar() {

        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                Aluno a = new Aluno(
                        rs.getInt("ra"),
                        rs.getString("nome_completo"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getInt("id_sala")
                );

                lista.add(a);
            }

        } catch (Exception e) {
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

    public boolean verificaLoginAluno(int ra, String senha) {
        boolean retorno = false;
        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM aluno WHERE ra LIKE ? AND senha LIKE ?");
            ps.setInt(1,ra);
            ps.setString(2,senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                retorno = true;
            }
        } catch (Exception e) {
            System.out.println("!!SQLException ao chamar AlunoDAO.verificaLoginAdmin(ra ,senha)!!");
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    } // Método que verifica se existe aquela conta de aluno
}
