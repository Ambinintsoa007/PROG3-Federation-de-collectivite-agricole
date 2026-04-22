package Federation.Agricole.API.controller;

import Federation.Agricole.API.entity.CreateCollectivity;
import Federation.Agricole.API.service.CollectivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
