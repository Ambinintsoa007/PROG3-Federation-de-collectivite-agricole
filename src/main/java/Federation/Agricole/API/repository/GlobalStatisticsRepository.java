package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import Federation.Agricole.API.controller.dto.CollectivityInformation;
import Federation.Agricole.API.controller.dto.GlobalStatisticsDTO;
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

        String sql = """
                    
                SELECT 
                        c.name as unique_name, 
                        c.number as collectivity_id,
                        --  Cotisations ACTIVE only
                        COALESCE(
                            (COUNT(DISTINCT CASE WHEN 
                                (SELECT COALESCE(SUM(t.amount), 0) FROM transaction t 
                                 WHERE t.member_id = cm.member_id 
                                 AND t.creation_date BETWEEN ? AND ?) 
                                >= 
                                (SELECT COALESCE(SUM(mf.amount), 0) FROM membership_fee mf 
                                 WHERE mf.collectivity_id = c.id 
                                 AND mf.status = 'ACTIVE' 
                                 AND mf.eligible_from BETWEEN ? AND ?) 
                            THEN cm.member_id END) * 100.0 / NULLIF(COUNT(DISTINCT cm.member_id), 0))
                        , 0) as percentage,
                        -- Nombre de nouveaux adherents sur la periode
                        (SELECT COUNT(*) FROM collectivity_member cm2 
                         JOIN member m ON cm2.member_id = m.id 
                         WHERE cm2.collectivity_id = c.id 
                         AND m.creation_date BETWEEN ? AND ?) as new_members
                    FROM collectivity c
                    LEFT JOIN collectivity_member cm ON c.id = cm.collectivity_id
                    GROUP BY c.id, c.name, c.number
                    """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Date dStart = Date.valueOf(start);
            Date dEnd = Date.valueOf(end);

            ps
            .setDate(1, dStart); ps.setDate(2, dEnd);
            ps.setDate(3, dStart); ps.setDate(4, dEnd);
            ps.setDate(5, dStart); ps.setDate(6, dEnd);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CollectivityInformation info = new CollectivityInformation();
                info.setName(rs.getString("unique_name"));
                info.setNumber(rs.getInt("collectivity_id"));

                stats.add(new GlobalStatisticsDTO(
                        info,
                        rs.getInt("new_members"),
                        rs.getDouble("percentage")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur Repository : " + e.getMessage());
        }
        return stats;
    }
}