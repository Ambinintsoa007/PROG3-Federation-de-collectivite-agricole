package Federation.Agricole.API.controller;

import Federation.Agricole.API.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatisticsController {

    private StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/collectivities/{id}/statistics")
    public ResponseEntity<?> getG(@PathVariable String id, @RequestParam("from") String start, @RequestParam("to") String end) {
        try {
            return ResponseEntity.ok(statisticsService.getMemberStats(id, start, end));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error G: " + e.getMessage());
        }
    }
}