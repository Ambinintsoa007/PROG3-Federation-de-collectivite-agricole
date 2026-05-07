package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import Federation.Agricole.API.controller.dto.MemberDescription;
import Federation.Agricole.API.controller.dto.MemberStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticsRepository {
    @Autowired
    private DataSource dataSource;

    public List<MemberStatsDTO> findMemberStats(String collId, LocalDate start, LocalDate end) {
        List<MemberStatsDTO> stats = new ArrayList<>();
        String sql = "SELECT m.id, m.first_name, m.last_name, m.email, m.occupation, " +
                "COALESCE(SUM(t.amount), 0) as total_encaisse, " +
                "GREATEST(0, (SELECT COALESCE(SUM(mf.amount), 0) FROM membership_fee mf WHERE mf.collectivity_id = ? AND mf.status = 'ACTIVE' AND mf.eligible_from BETWEEN ? AND ?) - COALESCE(SUM(t.amount), 0)) as montant_impaye " +
                "FROM member m " +
                "LEFT JOIN transaction t ON m.id = t.member_id AND t.creation_date BETWEEN ? AND ? " +
                "WHERE m.id IN (SELECT member_id FROM collectivity_member WHERE collectivity_id = ?) " +
                "GROUP BY m.id, m.first_name, m.last_name, m.email, m.occupation";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collId);
            ps.setDate(2, Date.valueOf(start));
            ps.setDate(3, Date.valueOf(end));
            ps.setDate(4, Date.valueOf(start));
            ps.setDate(5, Date.valueOf(end));
            ps.setString(6, collId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MemberDescription desc = new MemberDescription(
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("occupation")
                );
                MemberStatsDTO dto = new MemberStatsDTO(
                        desc,
                        rs.getDouble("total_encaisse"), // earnedAmount
                        rs.getDouble("montant_impaye")  // unpaidAmount
                );
                stats.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur Repository G: " + e.getMessage());
        }
        return stats;
    }
}

