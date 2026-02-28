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

    public boolean inserir(Aluno aluno) {
        boolean retorno = false;
        String sql = "INSERT INTO aluno (ra, nome_completo, email, senha, sala) VALUES (?,?,?,?,?)";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, aluno.getRa());
            ps.setString(2, aluno.getNomeCompleto());
            ps.setString(3, aluno.getEmail());
            ps.setString(4, aluno.getSenha());
            ps.setInt(5, aluno.getIdSala());

            retorno = ps.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println("!!SQLException ao chamar AlunoDAO.inserir()!!");
            sqle.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    }

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
                Aluno a = new Aluno();
                a.setRa(rs.getInt("ra"));
                a.setNomeCompleto(rs.getString("nome_completo"));
                a.setEmail(rs.getString("email"));
                a.setSenha(rs.getString("senha"));
                a.setIdSala(rs.getInt("sala"));  // Usa "sala" no banco
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

    public boolean atualizar(Aluno a) {
        boolean retorno = false;
        String sql = "UPDATE aluno SET nome_completo=?, email=?, senha=?, sala=? WHERE ra=?";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, a.getNomeCompleto());
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getSenha());
            ps.setInt(4, a.getIdSala());
            ps.setInt(5, a.getRa());

            retorno = ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    }

    public boolean deletar(int ra) {
        boolean retorno = false;
        String sql = "DELETE FROM aluno WHERE ra=?";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ra);
            retorno = ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    }

    public boolean verificaLoginAluno(int ra, String senha) {
        boolean retorno = false;
        String sql = "SELECT * FROM aluno WHERE ra = ? AND senha = ?";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ra);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();
            retorno = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
            return retorno;
        }
    }

    public Aluno login(String usuario, String senha) {
        String sql = "SELECT * FROM aluno WHERE (ra = ? OR email = ?) AND senha = ?";

        try {
            conn = banco.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);

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
                a.setIdSala(rs.getInt("sala"));
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            banco.desconectar(conn);
        }
        return null;
    }

    public Aluno buscarPorRa(int ra) {
        String sql = "SELECT * FROM aluno WHERE ra = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ra);
            rs = ps.executeQuery();

            if (rs.next()) {
                Aluno a = new Aluno();
                a.setRa(rs.getInt("ra"));
                a.setNomeCompleto(rs.getString("nome_completo"));
                a.setEmail(rs.getString("email"));
                a.setSenha(rs.getString("senha"));
                a.setIdSala(rs.getInt("sala"));
                return a;
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

        return null;
    }
}