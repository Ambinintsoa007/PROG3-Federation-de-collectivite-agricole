package Federation.Agricole.API.controller;

import Federation.Agricole.API.controller.dto.CollectivityActivityDTO;
import Federation.Agricole.API.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities/{id}/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<?> addActivities(
            @PathVariable String id,
            @RequestBody List<CollectivityActivityDTO> activities) {
        try {
            List<CollectivityActivityDTO> saved = activityService.createActivities(id, activities);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while adding activities: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getActivities(@PathVariable String id) {
        try {
            List<CollectivityActivityDTO> activities = activityService.getActivitiesByCollectivity(id);
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Collectivity not found or error retrieving activities for ID: " + id);
        }
    }
}