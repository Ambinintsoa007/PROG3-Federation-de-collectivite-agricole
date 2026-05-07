package Federation.Agricole.API.service;

import Federation.Agricole.API.controller.dto.MemberStatsDTO;
import Federation.Agricole.API.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Autowired

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public List<MemberStatsDTO> getMemberStats(String id, String start, String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Validation error: Start date must be before end date.");
        }

        return statisticsRepository.findMemberStats(id, startDate, endDate);
    }
}