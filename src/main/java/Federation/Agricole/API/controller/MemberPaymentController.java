package Federation.Agricole.API.controller;

import Federation.Agricole.API.dto.CreateMemberPaymentDTO;
import Federation.Agricole.API.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberPaymentController {

    private final PaymentService paymentService;

    public MemberPaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Creates multiple payments for a specific member.
     * Each payment automatically triggers a collectivity transaction.
     */
    @PostMapping("/members/{id}/payments")
    public ResponseEntity<?> recordMemberPayments(
            @PathVariable String id,
            @RequestBody List<CreateMemberPaymentDTO> paymentDTOs) {
        try {
            for (CreateMemberPaymentDTO dto : paymentDTOs) {
                paymentService.processMemberPayment(id, dto);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Payments successfully recorded");
        } catch (RuntimeException e) {
            // Return 400 or 404 based on business logic exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}