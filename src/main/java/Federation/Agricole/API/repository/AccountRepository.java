package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import Federation.Agricole.API.dto.AccountDTO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> findBalancesAtDate(String collectivityId, String date) {
        List<Map<String, Object>> accounts = new ArrayList<>();
        // Logic: Substract transactions that happened AFTER the 'at' date from current amount
        String sql = "SELECT fa.id, fa.account_type, fa.amount as current_bal, " +
                "COALESCE(SUM(t.amount), 0) as sum_after " +
                "FROM financial_accounts fa " +
                "LEFT JOIN transactions t ON fa.id = t.account_id AND t.creation_date > CAST(? AS DATE) " +
                "WHERE fa.collectivity_id = ? " +
                "GROUP BY fa.id, fa.account_type, fa.amount";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date);
            ps.setString(2, collectivityId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> account = new HashMap<>();
                double balanceAtDate = rs.getDouble("current_bal") - rs.getDouble("sum_after");
                account.put("id", rs.getString("id"));
                account.put("type", rs.getString("account_type"));
                account.put("balance", balanceAtDate);
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating retroactive balance: " + e.getMessage());
        }
        return accounts;
    }
}