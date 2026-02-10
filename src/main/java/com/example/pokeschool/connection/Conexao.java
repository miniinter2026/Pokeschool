package com.example.pokeschool.connection;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

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
}
