package Federation.Agricole.API.controller;

import Federation.Agricole.API.controller.dto.GlobalStatisticsDTO;
import Federation.Agricole.API.service.GlobalStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GlobalStatisticsController {

    @Autowired
    private GlobalStatisticsService service;

    @GetMapping("/collectivities/statistics")
    public ResponseEntity<?> getGlobalStats(
            @RequestParam("from") String start,
            @RequestParam("to") String end) {
        try {
            List<GlobalStatisticsDTO> data = service.calculateGlobalPerformance(start, end);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error global calcul : " + e.getMessage());
        }
    }
}