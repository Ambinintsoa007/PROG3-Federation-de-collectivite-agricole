package Federation.Agricole.API.service;

import Federation.Agricole.API.dto.CreateMembershipFeeDTO;
import Federation.Agricole.API.repository.MembershipFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MembershipFeeService {
    private final MembershipFeeRepository membershipFeeRepository;

    @Autowired
    public MembershipFeeService(MembershipFeeRepository membershipFeeRepository) {
        this.membershipFeeRepository = membershipFeeRepository;
    }

    public void processAndSaveFee(String collectivityID, CreateMembershipFeeDTO dto) {
        if (dto.getAmount() < 0){
            throw new IllegalArgumentException("Amount must be positive");
        }

        //transform the String to LocalDate before sending to the repo
        java.time.LocalDate date = java.time.LocalDate.parse(dto.getEligibleFrom());
        //Idea of generation ID
        String feeId = "FEE-" + UUID.randomUUID().toString().substring(0, 8);

        membershipFeeRepository.save(
                feeId,
                collectivityID,
                dto.getEligibleFrom(),
                dto.getFrequency(),
                dto.getAmount(),
                dto.getLabel()
        );
    }
}
