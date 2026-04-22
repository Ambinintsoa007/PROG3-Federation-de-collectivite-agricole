package Federation.Agricole.API.dto;

import Federation.Agricole.API.entity.Member;
import java.util.List;

public class MemberRequest {
    public Member member;
    public List<String> refereeIds;
    public boolean regPaid;
    public boolean duesPaid;
}