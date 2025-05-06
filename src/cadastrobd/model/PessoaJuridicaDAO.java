package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaJuridicaDAO {

    public void incluir(PessoaJuridica pj) throws SQLException {
        String sqlPessoa =
            "INSERT INTO Pessoa (id_pessoa, nome, logradouro, telefone) " +
            "VALUES (?, ?, ?, ?)";
        String sqlJuridica =
            "INSERT INTO PessoaJuridica (id_pessoa, cnpj) VALUES (?, ?)";

        int id = SequenceManager.getValue("seq_pessoa");
        pj.setId(id);

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sqlPessoa);
             PreparedStatement ps2 = conn.prepareStatement(sqlJuridica)) {

            // Pessoa
            ps1.setInt(1, pj.getId());
            ps1.setString(2, pj.getNome());
            ps1.setString(3, pj.getLogradouro());
            ps1.setString(4, pj.getTelefone());
            ps1.executeUpdate();

            // PessoaJuridica
            ps2.setInt(1, pj.getId());
            ps2.setString(2, pj.getCnpj());
            ps2.executeUpdate();
        }
    }

    public void alterar(PessoaJuridica pj) throws SQLException {
        String sqlPessoa =
            "UPDATE Pessoa SET nome=?, logradouro=?, telefone=? " +
            "WHERE id_pessoa=?";
        String sqlJuridica =
            "UPDATE PessoaJuridica SET cnpj=? WHERE id_pessoa=?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sqlPessoa);
             PreparedStatement ps2 = conn.prepareStatement(sqlJuridica)) {

            // Pessoa
            ps1.setString(1, pj.getNome());
            ps1.setString(2, pj.getLogradouro());
            ps1.setString(3, pj.getTelefone());
            ps1.setInt(4, pj.getId());
            ps1.executeUpdate();

            // PessoaJuridica
            ps2.setString(1, pj.getCnpj());
            ps2.setInt(2, pj.getId());
            ps2.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sqlJuridica = "DELETE FROM PessoaJuridica WHERE id_pessoa=?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id_pessoa=?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sqlJuridica);
             PreparedStatement ps2 = conn.prepareStatement(sqlPessoa)) {

            ps1.setInt(1, id);
            ps1.executeUpdate();

            ps2.setInt(1, id);
            ps2.executeUpdate();
        }
    }

    public PessoaJuridica getPessoa(int id) throws SQLException {
        String sql =
            "SELECT p.id_pessoa, p.nome, p.logradouro, p.telefone, pj.cnpj " +
            "FROM Pessoa p " +
            "JOIN PessoaJuridica pj ON p.id_pessoa = pj.id_pessoa " +
            "WHERE p.id_pessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PessoaJuridica(
                        rs.getInt("id_pessoa"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        null,
                        null,
                        rs.getString("telefone"),
                        null,
                        rs.getString("cnpj")
                    );
                }
            }
        }
        return null;
    }

    public List<PessoaJuridica> getPessoas() throws SQLException {
        String sql =
            "SELECT p.id_pessoa, p.nome, p.logradouro, p.telefone, pj.cnpj " +
            "FROM Pessoa p " +
            "JOIN PessoaJuridica pj ON p.id_pessoa = pj.id_pessoa";

        List<PessoaJuridica> lista = new ArrayList<>();
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new PessoaJuridica(
                    rs.getInt("id_pessoa"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    null,
                    null,
                    rs.getString("telefone"),
                    null,
                    rs.getString("cnpj")
                ));
            }
        }
        return lista;
    }
}
