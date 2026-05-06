package Federation.Agricole.API.controller.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GlobalStatisticsDTO {
    private String collectivityId;
    private String uniqueName;
    private Double pourcentageAJour;
    private Integer totalMembres;

    public GlobalStatisticsDTO(String collectivityId, String uniqueName, Double pourcentageAJour, Integer totalMembres) {
        this.collectivityId = collectivityId;
        this.uniqueName = uniqueName;
        this.pourcentageAJour = pourcentageAJour;
        this.totalMembres = totalMembres;
    }
}
