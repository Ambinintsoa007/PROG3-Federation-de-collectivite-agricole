package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.UUID;

@Repository
public class PaymentRepository {
    private final DataSource dataSource;

    public PaymentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void savePaymentAndTransaction(String memberId, Double amount, String accountId, String mode, String collectivityId) {
        String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8);
        String transactionId = "TRA-" + UUID.randomUUID().toString().substring(0, 8);

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try {
                // 1. Insert into payments (This table HAS member_id)
                String sqlPay = "INSERT INTO payments (id, member_id, amount, payment_mode, account_id, creation_date) VALUES (?, ?, ?, ?, ?, CURRENT_DATE)";
                try (PreparedStatement psP = conn.prepareStatement(sqlPay)) {
                    psP.setString(1, paymentId);
                    psP.setString(2, memberId);
                    psP.setDouble(3, amount);
                    psP.setString(4, mode);
                    psP.setString(5, accountId);
                    psP.executeUpdate();
                }

                // 2. Insert into transactions (This table DOES NOT HAVE member_id)
                String sqlTrans = "INSERT INTO transactions (id, collectivity_id, amount, creation_date, payment_mode, account_id) VALUES (?, ?, ?, CURRENT_DATE, ?, ?)";
                try (PreparedStatement psT = conn.prepareStatement(sqlTrans)) {
                    psT.setString(1, transactionId);
                    psT.setString(2, collectivityId);
                    psT.setDouble(3, amount);
                    psT.setString(4, mode);
                    psT.setString(5, accountId);
                    psT.executeUpdate();
                }

                conn.commit(); // Success!
            } catch (SQLException e) {
                conn.rollback(); // Error, undo everything
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }
}