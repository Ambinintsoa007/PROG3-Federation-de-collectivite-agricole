package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import Federation.Agricole.API.dto.MembershipFeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MembershipFeeRepository {
    private final  DataSource dataSource;

    @Autowired //spring will inject the datasource automatically here when starting
    public MembershipFeeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(String id, String collId, String eligibleFrom, String frequency, Double amount, String label) {
        String sql = "INSERT INTO membership_fees (id, collectivity_id, eligible_from, frequency, amount, label, status) VALUES (?, ?, ?, ?, ?, ?, 'ACTIVE')";

        try(
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, id);
            stmt.setString(2, collId);
            stmt.setDate(3, java.sql.Date.valueOf(eligibleFrom));
            stmt.setString(4, frequency);
            stmt.setDouble(5, amount);
            stmt.setString(6, label);

            stmt.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException( "SQL error : " + e);
        }
    }

    public List<MembershipFeeDTO> findByCollectivityId(String collectivityId) {
        String sql = "SELECT id, collectivity_id, eligible_from, frequency, amount, label, status " +
                "FROM membership_fees WHERE collectivity_id = ?";
        List<MembershipFeeDTO> fees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                fees.add(new MembershipFeeDTO(
                        rs.getString("id"),
                        rs.getString("collectivity_id"),
                        rs.getDate("eligible_from").toLocalDate(),
                        rs.getString("frequency"),
                        rs.getDouble("amount"),
                        rs.getString("label"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL lors de la récupération : " + e.getMessage());
        }
        return fees;
    }
}
