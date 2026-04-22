package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class AccountRepository {

    private DataSource dataSource;

    public AccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(String id, String collectivityId, String type, String holder, String bank, String mobile) {
        String sql = "INSERT INTO financial_accounts (id, collectivity_id, account_type, holder_name, bank_name, mobile_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, collectivityId);
            ps.setString(3, type);
            ps.setString(4, holder);
            ps.setString(5, bank);
            ps.setString(6, mobile);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SQL Error: Unable to create the financial account.", e);
        }
    }
}
