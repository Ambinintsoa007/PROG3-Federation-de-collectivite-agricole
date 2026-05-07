package Federation.Agricole.API.service;

import Federation.Agricole.API.controller.dto.GlobalStatisticsDTO;
import Federation.Agricole.API.repository.GlobalStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GlobalStatisticsService {

    @Autowired
    private GlobalStatisticsRepository repository;

    public List<GlobalStatisticsDTO> calculateGlobalPerformance(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin.");
        }

        return repository.fetchAllCollectivitiesStats(startDate, endDate);
    }
}