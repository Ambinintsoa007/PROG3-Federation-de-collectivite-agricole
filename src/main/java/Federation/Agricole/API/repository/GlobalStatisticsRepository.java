package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import Federation.Agricole.API.dto.GlobalStatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GlobalStatisticsRepository {
    @Autowired
    private DataSource dataSource;

    public List<GlobalStatisticsDTO> fetchAllCollectivitiesStats(LocalDate start, LocalDate end) {
        List<GlobalStatisticsDTO> stats = new ArrayList<>();

        String sql = "SELECT c.id, c.unique_name, " +
                "COALESCE((COUNT(DISTINCT CASE WHEN (SELECT COALESCE(SUM(t.amount), 0) FROM transactions t WHERE t.member_id = mc.member_id AND t.creation_date BETWEEN ? AND ?) >= " +
                "(SELECT COALESCE(SUM(mf.amount), 0) FROM membership_fees mf WHERE mf.collectivity_id = c.id AND mf.status = 'ACTIVE' AND mf.eligible_from BETWEEN ? AND ?) " +
                "THEN mc.member_id END) * 100.0 / NULLIF(COUNT(DISTINCT mc.member_id), 0)), 0) as pourcentage_a_jour, " +
                "COUNT(DISTINCT mc.member_id) as total_membres " +
                "FROM collectivities c " +
                "LEFT JOIN member_collectivity mc ON c.id = mc.collectivity_id " +
                "GROUP BY c.id, c.unique_name";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(start));
            ps.setDate(2, Date.valueOf(end));
            ps.setDate(3, Date.valueOf(start));
            ps.setDate(4, Date.valueOf(end));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                stats.add(new GlobalStatisticsDTO(
                        rs.getString("id"),
                        rs.getString("unique_name"),
                        rs.getDouble("pourcentage_a_jour"),
                        rs.getInt("total_membres")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur Repository H: " + e.getMessage());
        }
        return stats;
    }
}