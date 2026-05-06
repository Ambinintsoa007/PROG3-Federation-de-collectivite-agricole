package Federation.Agricole.API.controller;

import Federation.Agricole.API.dto.GlobalStatisticsDTO;
import Federation.Agricole.API.service.GlobalStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics/global")
public class GlobalStatisticsController {

    @Autowired
    private GlobalStatisticsService service;

    @GetMapping
    public ResponseEntity<?> getGlobalStats(@RequestParam String start, @RequestParam String end) {
        try {
            List<GlobalStatisticsDTO> data = service.calculateGlobalPerformance(start, end);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur de calcul global : " + e.getMessage());
        }
    }
}