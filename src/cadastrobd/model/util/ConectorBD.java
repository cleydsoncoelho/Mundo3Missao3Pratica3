package cadastrobd.model.util;

import java.sql.*;

public class ConectorBD {
    private static final String URL =
        "jdbc:sqlserver://localhost:1433;databaseName=loja;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "loja";
    private static final String PASS = "loja";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static PreparedStatement getPrepared(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public static ResultSet getSelect(PreparedStatement ps) throws SQLException {
        return ps.executeQuery();
    }

    public static void close(ResultSet rs) {
        if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
    }

    public static void close(Statement st) {
        if (st != null) try { st.close(); } catch (SQLException ignored) {}
    }

    public static void close(Connection conn) {
        if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
    }
}
