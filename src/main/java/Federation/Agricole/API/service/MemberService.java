package Federation.Agricole.API.service;

import Federation.Agricole.API.entity.Member;
import Federation.Agricole.API.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createMember(Member member, List<String> refereeIds, boolean regPaid, boolean duesPaid){
        //Payment verification
        if(!regPaid || !duesPaid){
            throw new RuntimeException("Error 400 : inscription fees and cotisation must be paid !");
        }

        // Referees verification (Rule B-2)
        if(!member.isFounder()){
            if(refereeIds == null || refereeIds.size() < 2){
                throw new RuntimeException("Error 400 : a new member must be referred by at least two members !");
            }

            // Check provenance: Local Referees vs External Referees
            int localRefereesCount = memberRepository.countLocalReferees(member.getCollectivityId(), refereeIds);
            int externalRefereesCount = refereeIds.size() - localRefereesCount;

            if(localRefereesCount < externalRefereesCount) {
                throw new RuntimeException("Error 400 : Local referees (" + localRefereesCount +
                        ") must be greater than or equal to external referees (" + externalRefereesCount + ")!");
            }
        }

        memberRepository.save(member);

        // Link member to Collectivity (Your colleague's table)
        memberRepository. saveMemberCollectivity(member.getId(), member.getCollectivityId());

        // Save Referees links (Only for non-founders)
        if(!member.isFounder()){
            memberRepository.saveReferees(member.getId(), refereeIds);
        }
    }
}