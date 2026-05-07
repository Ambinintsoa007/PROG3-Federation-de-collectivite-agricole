package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import Federation.Agricole.API.controller.dto.CollectivityActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ActivityRepository {
    @Autowired
    private DataSource dataSource;

    public List<CollectivityActivityDTO> saveAll(String collId, List<CollectivityActivityDTO> activities) {

        String sql = "INSERT INTO activities (id, collectivity_id, label, type, occupations, executive_date, week_ordinal, day_of_week) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (CollectivityActivityDTO act : activities) {
                String id = (act.getId() != null) ? act.getId() : "ACT-" + System.currentTimeMillis();

                ps.setString(1, id);
                ps.setString(2, collId);
                ps.setString(3, act.getLabel());
                ps.setString(4, act.getActivityType());
                ps.setArray(5, conn.createArrayOf("VARCHAR", act.getMemberOccupationConcerned().toArray()));


                if (act.getExecutiveDate() != null) {
                    ps.setDate(6, Date.valueOf(act.getExecutiveDate()));
                    ps.setNull(7, Types.INTEGER);
                    ps.setNull(8, Types.VARCHAR);
                } else {
                    ps.setNull(6, Types.DATE);
                    ps.setInt(7, act.getRecurrenceRule().getWeekOrdinal());
                    ps.setString(8, act.getRecurrenceRule().getDayOfWeek());
                }

                ps.addBatch();
            }
            ps.executeBatch();
            return findAllByCollectivity(collId);
        } catch (SQLException e) {
            throw new RuntimeException("DB Error (Save): " + e.getMessage());
        }
    }

    public List<CollectivityActivityDTO> findAllByCollectivity(String collId) {
        List<CollectivityActivityDTO> list = new ArrayList<>();

        String sql = "SELECT id, label, type, occupations, executive_date, week_ordinal, day_of_week FROM activities WHERE collectivity_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CollectivityActivityDTO dto = new CollectivityActivityDTO();
                dto.setId(rs.getString("id"));
                dto.setLabel(rs.getString("label"));
                dto.setActivityType(rs.getString("type"));

                Array sqlArray = rs.getArray("occupations");
                if (sqlArray != null) {
                    dto.setMemberOccupationConcerned(Arrays.asList((String[]) sqlArray.getArray()));
                }

                Date sqlDate = rs.getDate("executive_date");
                if (sqlDate != null) {
                    dto.setExecutiveDate(sqlDate.toLocalDate());
                }

                int weekOrd = rs.getInt("week_ordinal");
                if (!rs.wasNull()) {
                    CollectivityActivityDTO.MonthlyRecurrenceRule rule = new CollectivityActivityDTO.MonthlyRecurrenceRule();
                    rule.setWeekOrdinal(weekOrd);
                    rule.setDayOfWeek(rs.getString("day_of_week"));
                    dto.setRecurrenceRule(rule);
                }

                list.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB Error (Fetch): " + e.getMessage());
        }
        return list;
    }
}