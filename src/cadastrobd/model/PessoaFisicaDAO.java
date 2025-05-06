package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import cadastrobd.model.util.SequenceManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {

    public void incluir(PessoaFisica pf) throws SQLException {
        String sqlPessoa =
            "INSERT INTO Pessoa (id_pessoa, nome, logradouro, telefone) " +
            "VALUES (?, ?, ?, ?)";
        String sqlFisica =
            "INSERT INTO PessoaFisica (id_pessoa, cpf) VALUES (?, ?)";

        int id = SequenceManager.getValue("seq_pessoa");
        pf.setId(id);

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sqlPessoa);
             PreparedStatement ps2 = conn.prepareStatement(sqlFisica)) {

            // Pessoa
            ps1.setInt(1, pf.getId());
            ps1.setString(2, pf.getNome());
            ps1.setString(3, pf.getLogradouro());
            ps1.setString(4, pf.getTelefone());
            ps1.executeUpdate();

            // PessoaFisica
            ps2.setInt(1, pf.getId());
            ps2.setString(2, pf.getCpf());
            ps2.executeUpdate();
        }
    }

    public void alterar(PessoaFisica pf) throws SQLException {
        String sqlPessoa =
            "UPDATE Pessoa SET nome=?, logradouro=?, telefone=? " +
            "WHERE id_pessoa=?";
        String sqlFisica =
            "UPDATE PessoaFisica SET cpf=? WHERE id_pessoa=?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sqlPessoa);
             PreparedStatement ps2 = conn.prepareStatement(sqlFisica)) {

            // Pessoa
            ps1.setString(1, pf.getNome());
            ps1.setString(2, pf.getLogradouro());
            ps1.setString(3, pf.getTelefone());
            ps1.setInt(4, pf.getId());
            ps1.executeUpdate();

            // PessoaFisica
            ps2.setString(1, pf.getCpf());
            ps2.setInt(2, pf.getId());
            ps2.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sqlFisica = "DELETE FROM PessoaFisica WHERE id_pessoa=?";
        String sqlPessoa = "DELETE FROM Pessoa WHERE id_pessoa=?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sqlFisica);
             PreparedStatement ps2 = conn.prepareStatement(sqlPessoa)) {

            ps1.setInt(1, id);
            ps1.executeUpdate();

            ps2.setInt(1, id);
            ps2.executeUpdate();
        }
    }

    public PessoaFisica getPessoa(int id) throws SQLException {
        String sql =
            "SELECT p.id_pessoa, p.nome, p.logradouro, p.telefone, pf.cpf " +
            "FROM Pessoa p " +
            "JOIN PessoaFisica pf ON p.id_pessoa = pf.id_pessoa " +
            "WHERE p.id_pessoa = ?";

        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PessoaFisica(
                        rs.getInt("id_pessoa"),
                        rs.getString("nome"),
                        rs.getString("logradouro"),
                        null,   // cidade não existe
                        null,   // estado não existe
                        rs.getString("telefone"),
                        null,   // email não existe
                        rs.getString("cpf")
                    );
                }
            }
        }
        return null;
    }

    public List<PessoaFisica> getPessoas() throws SQLException {
        String sql =
            "SELECT p.id_pessoa, p.nome, p.logradouro, p.telefone, pf.cpf " +
            "FROM Pessoa p " +
            "JOIN PessoaFisica pf ON p.id_pessoa = pf.id_pessoa";

        List<PessoaFisica> lista = new ArrayList<>();
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new PessoaFisica(
                    rs.getInt("id_pessoa"),
                    rs.getString("nome"),
                    rs.getString("logradouro"),
                    null,
                    null,
                    rs.getString("telefone"),
                    null,
                    rs.getString("cpf")
                ));
            }
        }
        return lista;
    }
}
