package com.example.pokeschool.connection;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Dotenv dotenv = Dotenv.load();
    private static String url = dotenv.get("dbUrl");
    private static String user = dotenv.get("dbUser");
    private static String pass = dotenv.get("dbSenha");

    // Sempre que a classe for inicializada ele vai rodar esse método estatico para garantir que a classe seja verificada
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método para fazer a conexão com o banco
    public static Connection conectar() {

        Connection conn = null;

        try {
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