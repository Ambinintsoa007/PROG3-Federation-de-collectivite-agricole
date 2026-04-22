package Federation.Agricole.API.controller;


import Federation.Agricole.API.dto.AccountDTO;
import Federation.Agricole.API.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collectivities")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{id}/accounts")
    public ResponseEntity<?> addAccount(@PathVariable String id, @RequestBody AccountDTO accountDTO) {
        try {
            accountService.createAccount(
                    id,
                    accountDTO.getAccountType(),
                    accountDTO.getHolderName(),
                    accountDTO.getBankName(),
                    accountDTO.getMobileNumber()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Financial account created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());

        }
    }
}