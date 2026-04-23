package Federation.Agricole.API.service;

import Federation.Agricole.API.config.DataSource;
import Federation.Agricole.API.dto.CreateMemberPaymentDTO;
import Federation.Agricole.API.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final DataSource dataSource;

    public PaymentService(PaymentRepository paymentRepository, DataSource dataSource) {
        this.paymentRepository = paymentRepository;
        this.dataSource = dataSource;
    }

    public void processMemberPayment(String memberId, CreateMemberPaymentDTO dto) {
        // Find member's collectivity ID via the member_collectivity table
        String collectivityId = getMemberCollectivity(memberId);

        if (collectivityId == null) {
            throw new RuntimeException("Member " + memberId + " is not linked to a collectivity");
        }

        paymentRepository.savePaymentAndTransaction(
                memberId,
                dto.getAmount(),
                dto.getAccountCreditedIdentifier(),
                dto.getPaymentMode(),
                collectivityId
        );
    }

    private String getMemberCollectivity(String memberId) {
        String sql = "SELECT collectivity_id FROM member_collectivity WHERE member_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("collectivity_id");
        } catch (SQLException e) {
            throw new RuntimeException("Database error during collectivity lookup", e);
        }
        return null;
    }
}