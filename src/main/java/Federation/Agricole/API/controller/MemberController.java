package Federation.Agricole.API.controller;

import Federation.Agricole.API.dto.MemberRequest;
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
    public String registerMember(@RequestBody List<MemberRequest>  memberRequest) {
        try {
            for (MemberRequest memberRequest1 : memberRequest){
                memberService.createMember(
                        memberRequest1.member,
                        memberRequest1.refereeIds,
                        memberRequest1.regPaid,
                        memberRequest1.duesPaid
                );
            }
            return "Membre enregistré avec succès !";
        } catch (RuntimeException e) {
            return "Erreur : " + e.getMessage();
        }
    }
}