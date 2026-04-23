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
    public ResponseEntity<?> addAccount(@PathVariable String id, @RequestBody AccountDTO dto) {
        try {
            accountService.createAccount(id, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Financial account created.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error : " + e.getMessage());
        }
    }

    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<?> getFinancialAccounts(@PathVariable String id, @RequestParam(required = false) String at) {
        try {
            // Use current date if 'at' parameter is missing
            String targetDate = (at != null) ? at : java.time.LocalDate.now().toString();
            return ResponseEntity.ok(accountService.getAccountsBalanceAt(id, targetDate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date format or request: " + e.getMessage());
        }
    }
}