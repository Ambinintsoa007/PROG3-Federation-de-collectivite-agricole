package Federation.Agricole.API.controller;

import Federation.Agricole.API.dto.MemberRequest;
import Federation.Agricole.API.entity.Member;
import Federation.Agricole.API.service.MemberService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    
    @PostMapping("/members")
    public String registerMember(@RequestBody MemberRequest  memberRequest) {
        try {
            memberService.createMember(
                    memberRequest.member,
                    memberRequest.refereeIds,
                    memberRequest.regPaid,
                    memberRequest.duesPaid
            );
            return "Membre enregistré avec succès !";
        } catch (RuntimeException e) {
            return "Erreur : " + e.getMessage();
        }
    }
}