package Federation.Agricole.API.controller;

import Federation.Agricole.API.entity.CreateCollectivity;
import Federation.Agricole.API.service.CollectivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    @Autowired
    private CollectivityService collectivityService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<CreateCollectivity> body) {
        try {
            for (CreateCollectivity dto : body) {
                collectivityService.createCollectivity(
                        dto.getLocation(),
                        dto.getMembers(),
                        dto.isFederationApproval(),
                        dto.getStructure().getPresident(),
                        dto.getStructure().getVicePresident(),
                        dto.getStructure().getTreasurer(),
                        dto.getStructure().getSecretary()
                );
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Collectivités créées avec succès.");

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur : " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/identity")
    public ResponseEntity<?> patchIdentity(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            String number = body.get("identificationNumber");
            String name = body.get("uniqueName");

            collectivityService.assignIdentity(id, number, name);

            return ResponseEntity.ok("Identité attribuée avec succès à la collectivité " + id);

        } catch (RuntimeException e) {

            if (e.getMessage().contains("IMMUTABLE_ERROR")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage()); // 403
            } else if (e.getMessage().contains("CONFLICT_ERROR")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());  // 409
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        try {
            // Return collectivity details and the 'members' attribute as required
            return ResponseEntity.ok(collectivityService.getFullCollectivityData(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
