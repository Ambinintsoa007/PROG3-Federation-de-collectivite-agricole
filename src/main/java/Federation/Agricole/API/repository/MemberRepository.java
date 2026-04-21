package Federation.Agricole.API.repository;

import Federation.Agricole.API.config.DataSource;
import Federation.Agricole.API.entity.Member;
import Federation.Agricole.API.entity.MemberOccupation;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberRepository {
    private DataSource dataSource;

    public MemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM members WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);){
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return false;
    }

    public void save(Member member) {
        String sql = """
                INSERT INTO members 
                (id, first_name, last_name, birth_date, gender, address, 
                profession, phone_number, email, occupation, is_founder)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
          pstmt.setString(1, member.getId());
          pstmt.setString(2, member.getFirstName());
          pstmt.setString(3, member.getLastName());
          pstmt.setObject(4, member.getBirthday());
          pstmt.setString(5, member.getGender().name());
          pstmt.setString(6, member.getAddress());
          pstmt.setString(7, member.getProfession());
          pstmt.setInt(8, member.getPhoneNumber());
          pstmt.setString(9, member.getEmail());
          pstmt.setString(10, member.getOccupation().name());
          pstmt.setBoolean(11, member.isFounder());

          pstmt.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void saveReferees(String memberId, List<String> refereeIds) {
        String sql = "INSERT INTO member_referees (member_id, referee_id) VALUES (?, ?)";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            for (String refereeId : refereeIds) {
                pstmt.setString(1, memberId);
                pstmt.setString(2, refereeId);
                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (Exception e) {
                throw new RuntimeException(e);
        }
    }
}
