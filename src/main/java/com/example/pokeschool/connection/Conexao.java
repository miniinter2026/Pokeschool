package com.example.pokeschool.connection;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Método para fazer a conexão com o banco
    public static Connection conectar() {

        Connection conn = null;

        try {

            Dotenv dotenv = Dotenv.configure()
                    .directory("src/main/resources")
                    .load();

            String url = dotenv.get("dbUrl");
            String user = dotenv.get("dbUser");
            String pass = dotenv.get("dbSenha");

            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    //  Método para desconectar
    public static void desconectar(Connection conn) {

        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                    System.out.println("Conexão fechada com sucesso!");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão:");
                e.printStackTrace();
            }
        }
    }
}