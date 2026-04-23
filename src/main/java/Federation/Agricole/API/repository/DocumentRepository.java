package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

@Repository
public class DocumentRepository {

    private final DataSource dataSource;

    @Autowired
    public DocumentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(String id, String collectivityId, String type, String path) {
        String sql = "INSERT INTO documents (id, collectivity_id, document_type, file_path, upload_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, collectivityId);
            ps.setString(3, type);
            ps.setString(4, path);
            ps.setObject(5, LocalDate.now());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error: Unable to register document metadata.", e);
        }
    }
}
