package cadastrobd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Loja;encrypt=true;trustServerCertificate=true;";
        String user = "loja";
        String pass = "loja";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("OK: conexão estabelecida!");
        } catch (SQLException e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
}
