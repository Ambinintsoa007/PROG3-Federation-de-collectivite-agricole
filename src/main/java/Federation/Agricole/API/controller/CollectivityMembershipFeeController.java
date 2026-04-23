package Federation.Agricole.API.controller;

import Federation.Agricole.API.dto.CreateMembershipFeeDTO;
import Federation.Agricole.API.dto.MembershipFeeDTO;
import Federation.Agricole.API.service.MembershipFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CollectivityMembershipFeeController {
    private final MembershipFeeService membershipFeeService;

    @Autowired
    public CollectivityMembershipFeeController(MembershipFeeService membershipFeeService) {
        this.membershipFeeService = membershipFeeService;
    }

    @PostMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> createMembershipFees(@PathVariable String id, @RequestBody List<CreateMembershipFeeDTO> body) {
        try {
            for(CreateMembershipFeeDTO dto : body){
                membershipFeeService.processAndSaveFee(id, dto);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Fees created successfully");
        } catch (IllegalArgumentException g){
            return ResponseEntity.badRequest().body(g.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> getMembershipFees(@PathVariable String id) {
        try {

            List<MembershipFeeDTO> fees = membershipFeeService.getFeesByCollectivity(id);

            if (fees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No membership fees found for collectivity: " + id);
            }

            return ResponseEntity.ok(fees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving fees: " + e.getMessage());
        }
    }
}
