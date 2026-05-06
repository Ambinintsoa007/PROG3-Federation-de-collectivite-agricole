package Federation.Agricole.API.controller.mapper;

import Federation.Agricole.API.controller.dto.ActivityStatus;
import Federation.Agricole.API.controller.dto.CreateMembershipFee;
import Federation.Agricole.API.controller.dto.Frequency;
import Federation.Agricole.API.controller.dto.MembershipFee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MembershipFeeDtoMapper {
    public MembershipFee mapToDto(Federation.Agricole.API.entity.MembershipFee membershipFee) {
        return MembershipFee.builder()
                .id(membershipFee.getId())
                .label(membershipFee.getLabel())
                .amount(membershipFee.getAmount())
                .frequency(membershipFee.getFrequency() == null ? null : Frequency.valueOf(membershipFee.getFrequency().name()))
                .status(membershipFee.getStatus() == null ? null : ActivityStatus.valueOf(membershipFee.getStatus().name()))
                .eligibleFrom(membershipFee.getEligibleFrom())
                .build();
    }

    public Federation.Agricole.API.entity.MembershipFee mapToEntity(CreateMembershipFee createMembershipFee) {
        return Federation.Agricole.API.entity.MembershipFee.builder()
                .label(createMembershipFee.getLabel())
                .amount(createMembershipFee.getAmount())
                .frequency(createMembershipFee.getFrequency() == null ? null : Federation.Agricole.API.entity.Frequency.valueOf(createMembershipFee.getFrequency().name()))
                .eligibleFrom(createMembershipFee.getEligibleFrom())
                .build();
    }
}
