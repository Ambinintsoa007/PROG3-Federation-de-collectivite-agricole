package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
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
public class CollectivityRepository {

    private final DataSource dataSource = new DataSource();

    public void save(
            String generatedId,
            String location,
            boolean federationApproval,
            String presidentId,
            String vicePresidentId,
            String treasurerId,
            String secretaryId,
            List<String> memberIds
    ) {
        Connection connection = dataSource.getConnection();

        try {
            connection.setAutoCommit(false);

            String sqlCollectivity = "INSERT INTO collectivities (id, location, federation_approval) VALUES (?, ?, ?);";
            try (PreparedStatement ps = connection.prepareStatement(sqlCollectivity)) {
                ps.setString(1, generatedId);
                ps.setString(2, location);
                ps.setBoolean(3, federationApproval);
                ps.executeUpdate();
            }

            String sqlStructure = "INSERT INTO collectivity_structures " +
                    "(collectivity_id, president_id, vice_president_id, treasurer_id, secretary_id) " +
                    "VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement ps = connection.prepareStatement(sqlStructure)) {
                ps.setString(1, generatedId);
                ps.setString(2, presidentId);
                ps.setString(3, vicePresidentId);
                ps.setString(4, treasurerId);
                ps.setString(5, secretaryId);
                ps.executeUpdate();
            }

            String sqlMembers = "INSERT INTO member_collectivity (member_id, collectivity_id) VALUES (?, ?);";
            try (PreparedStatement ps = connection.prepareStatement(sqlMembers)) {
                for (String memberId : memberIds) {
                    ps.setString(1, memberId);
                    ps.setString(2, generatedId);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erreur lors du rollback", ex);
            }
            throw new RuntimeException("Erreur SQL lors de l'enregistrement de la collectivité", e);
        } finally {
            dataSource.closeConnection(connection);
        }
    }

    public boolean isIdentitySet(String id) {
        String sql = "SELECT 1 FROM collectivities WHERE id = ? AND (identification_number IS NOT NULL OR unique_name IS NOT NULL);";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (var rs = ps.executeQuery()) {
                return rs.next(); // Retourne true si une ligne existe
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'identité", e);
        }
    }

    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM collectivities WHERE unique_name = ?;";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (var rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du nom unique", e);
        }
    }

    public void updateIdentity(String id, String number, String name) {
        String sql = "UPDATE collectivities SET identification_number = ?, unique_name = ? WHERE id = ?;";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, number);
            ps.setString(2, name);
            ps.setString(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'identité", e);
        }
    }

    public Map<String, Object> findCollectivityWithMembers(String id) {
        String sqlColl = "SELECT id, location, unique_name, identification_number FROM collectivities WHERE id = ?";
        String sqlMembers = "SELECT m.id, m.first_name, m.last_name, m.occupation " +
                "FROM members m " +
                "JOIN member_collectivity mc ON m.id = mc.member_id " +
                "WHERE mc.collectivity_id = ?";

        Map<String, Object> result = new HashMap<>();

        try (Connection conn = dataSource.getConnection()) {
            // 1. Fetch basic collectivity details
            try (PreparedStatement ps = conn.prepareStatement(sqlColl)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    result.put("id", rs.getString("id"));
                    result.put("location", rs.getString("location"));
                    result.put("uniqueName", rs.getString("unique_name"));
                    result.put("identificationNumber", rs.getString("identification_number"));
                } else {
                    return null; // Return null if collectivity doesn't exist
                }
            }

            // 2. Fetch associated members list
            List<Map<String, Object>> members = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(sqlMembers)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Map<String, Object> member = new HashMap<>();
                    member.put("id", rs.getString("id"));
                    member.put("firstName", rs.getString("first_name"));
                    member.put("lastName", rs.getString("last_name"));
                    member.put("occupation", rs.getString("occupation"));
                    members.add(member);
                }
            }
            result.put("members", members);

        } catch (SQLException e) {
            throw new RuntimeException("Database retrieval error: " + e.getMessage());
        }
        return result;
    }
}
