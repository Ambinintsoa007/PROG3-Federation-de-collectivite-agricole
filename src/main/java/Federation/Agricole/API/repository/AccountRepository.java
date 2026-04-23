package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import Federation.Agricole.API.dto.AccountDTO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class AccountRepository {

    private final DataSource dataSource;

    public AccountRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(String id, String collId, AccountDTO dto) {
        String sql = "INSERT INTO financial_accounts (id, collectivity_id, account_type, amount, holder_name, bank_name, mobile_number, mobile_service, bank_code, branch_code, account_number, account_key) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, collId);
            ps.setString(3, dto.getAccountType());
            ps.setObject(4, dto.getAmount());
            ps.setString(5, dto.getHolderName());
            ps.setString(6, dto.getBankName());
            ps.setObject(7, dto.getMobileNumber());
            ps.setString(8, dto.getMobileBankingService());
            ps.setObject(9, dto.getBankCode());
            ps.setObject(10, dto.getBankBranchCode());
            ps.setObject(11, dto.getBankAccountNumber());
            ps.setObject(12, dto.getBankAccountKey());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SQL Error: " + e.getMessage(), e);
        }
    }
}