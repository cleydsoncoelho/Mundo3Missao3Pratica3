package cadastrobd.model.util;

import java.sql.*;

public class SequenceManager {
    public static int getValue(String sequenceName) throws SQLException {
        String sql = "SELECT NEXT VALUE FOR " + sequenceName + " AS seq";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("seq");
            }
        }
        return -1;
    }
}
