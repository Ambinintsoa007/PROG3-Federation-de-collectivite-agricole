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
        String sql = "INSERT INTO activities (id, collectivity_id, label, type, occupations, executive_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (CollectivityActivityDTO act : activities) {
                String id = (act.getId() != null) ? act.getId() : "ACT-" + System.currentTimeMillis();

                ps.setString(1, id);
                ps.setString(2, collId);
                ps.setString(3, act.getLabel());
                ps.setString(4, act.getActivityType());
                ps.setArray(5, conn.createArrayOf
                        ("VARCHAR", act.getMemberOccupationConcerned().toArray()));
                ps.setDate(6, Date.valueOf(act.getExecutiveDate()));
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
        String sql = "SELECT id, label, type, occupations, executive_date FROM activities WHERE collectivity_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Array sqlArray = rs.getArray("occupations");
                List<String> occupationsList = new ArrayList<>();

                if (sqlArray != null) {
                    String[] occupationsArray = (String[]) sqlArray.getArray();
                    occupationsList = Arrays.asList(occupationsArray);
                }

                CollectivityActivityDTO dto = new CollectivityActivityDTO();
                dto.setId(rs.getString("id"));
                dto.setLabel(rs.getString("label"));
                dto.setActivityType(rs.getString("type"));
                dto.setMemberOccupationConcerned(occupationsList);
                dto.setExecutiveDate(rs.getDate("executive_date").toLocalDate());

                list.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB Error (Fetch): " + e.getMessage());
        }
        return list;
    }
}