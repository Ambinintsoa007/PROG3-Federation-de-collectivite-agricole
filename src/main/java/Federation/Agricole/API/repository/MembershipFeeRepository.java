package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
