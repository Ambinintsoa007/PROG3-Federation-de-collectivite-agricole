package Federation.Agricole.API.service;

import Federation.Agricole.API.controller.dto.CollectivityActivityDTO;
import Federation.Agricole.API.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository repository;

    public List<CollectivityActivityDTO> createActivities(String id, List<CollectivityActivityDTO> activities) {
        if (activities == null || activities.isEmpty()) {
            throw new IllegalArgumentException("Activity list cannot be empty.");
        }
        repository.saveAll(id, activities);
        return activities;
    }

    public List<CollectivityActivityDTO> getActivitiesByCollectivity(String id) {
        List<CollectivityActivityDTO> results = repository.findAllByCollectivity(id);
        if (results.isEmpty()) {
            System.out.println("No activities found for collectivity: " + id);
        }
        return results;
    }
}