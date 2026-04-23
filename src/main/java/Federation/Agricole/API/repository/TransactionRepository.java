package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import Federation.Agricole.API.dto.TransactionDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {
    private final DataSource dataSource;

    public TransactionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<TransactionDTO> findByPeriod(String collId, String from, String to) {
        List<TransactionDTO> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE collectivity_id = ? AND creation_date BETWEEN ? AND ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(new TransactionDTO(
                        rs.getString("id"),
                        rs.getDate("creation_date").toLocalDate(),
                        rs.getDouble("amount"),
                        rs.getString("payment_mode"),
                        rs.getString("account_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des transactions", e);
        }
        return transactions;
    }
}