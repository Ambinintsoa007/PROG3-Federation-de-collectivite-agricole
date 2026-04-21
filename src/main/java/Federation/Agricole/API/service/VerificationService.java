package Federation.Agricole.API.service;

import Federation.Agricole.API.entity.CreateCollectivityDTO;
import Federation.Agricole.API.repository.CollectivityRepository;
import Federation.Agricole.API.repository.MemberRepository;

import java.util.List;

public class VerificationService {

    private final CollectivityRepository repository = new CollectivityRepository();
    private final MemberRepository memberRepository = new MemberRepository();

    public void createCollectivity(
            String location,
            List<String> memberIds,
            boolean federationApproval,
            String presidentId,
            String vicePresidentId,
            String treasurerId,
            String secretaryId
    ) {
        if (!federationApproval) {
            throw new RuntimeException("Erreur : L'approbation de la fédération est absente.");
        }

        if (memberIds == null || memberIds.size() < 10) {
            throw new RuntimeException("Erreur : Il faut au moins 10 membres pour ouvrir une collectivité.");
        }

        validateExists(presidentId, "Président");
        validateExists(vicePresidentId, "Vice-Président");
        validateExists(treasurerId, "Trésorier");
        validateExists(secretaryId, "Secrétaire");

        String generatedId = "COLL-" + System.currentTimeMillis();

        repository.save(
                generatedId,
                location,
                federationApproval,
                presidentId,
                vicePresidentId,
                treasurerId,
                secretaryId,
                memberIds
        );;

        System.out.println("Collectivité créée avec succès : " + generatedId);
    }

    private void validateExists(String id, String poste) {
        if (id == null || !memberRepository.existsById(id)) {
            throw new RuntimeException("Erreur : Le membre désigné comme " + poste + " (" + id + ") n'existe pas.");
        }
    }
}
