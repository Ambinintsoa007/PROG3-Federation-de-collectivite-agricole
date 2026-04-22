package Federation.Agricole.API.controller;

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
import java.util.Map;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    @Autowired
    private CollectivityService collectivityService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<Map<String, Object>> body) {
        try {
            for (Map<String, Object> data : body) {

                Map<String, String> struct = (Map<String, String>) data.get("structure");


                collectivityService.createCollectivity(
                        (String) data.get("location"),
                        (List<String>) data.get("members"),
                        (boolean) data.get("federationApproval"),
                        struct.get("president"),
                        struct.get("vicePresident"),
                        struct.get("treasurer"),
                        struct.get("secretary")
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
