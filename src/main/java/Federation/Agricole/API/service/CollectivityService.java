package Federation.Agricole.API.service;

import Federation.Agricole.API.repository.CollectivityRepository;
import Federation.Agricole.API.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CollectivityService {

    @Autowired
    private CollectivityRepository collectivityRepository;

    @Autowired
    private MemberRepository memberRepository;

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
            throw new RuntimeException("Error: Federation approval is missing.");
        }

        if (memberIds == null || memberIds.size() < 10) {
            throw new RuntimeException("Error: At least 10 members are required to open a collectivity.");
        }

        // Validate that all board members exist
        validateExists(presidentId, "President");
        validateExists(vicePresidentId, "Vice-President");
        validateExists(treasurerId, "Treasurer");
        validateExists(secretaryId, "Secretary");

        String generatedId = "COLL-" + System.currentTimeMillis();

        collectivityRepository.save(
                generatedId,
                location,
                federationApproval,
                presidentId,
                vicePresidentId,
                treasurerId,
                secretaryId,
                memberIds
        );

        System.out.println("Collectivity created successfully: " + generatedId);
    }

    /**
     * Internal validation to check if a member exists before assigning to a role.
     */
    private void validateExists(String id, String role) {
        if (id == null || !memberRepository.existsById(id)) {
            throw new RuntimeException("Error: Member designated as " + role + " (" + id + ") does not exist.");
        }
    }

    /**
     * Assigns a unique name and number to a collectivity (Immutable after first set).
     */
    public void assignIdentity(String id, String number, String name) {
        if (collectivityRepository.isIdentitySet(id)) {
            throw new RuntimeException("IMMUTABLE_ERROR: This collectivity already has a name and number.");
        }

        if (collectivityRepository.existsByName(name)) {
            throw new RuntimeException("CONFLICT_ERROR: The name '" + name + "' is already used.");
        }

        collectivityRepository.updateIdentity(id, number, name);
    }

    /**
     * Fetches the collectivity details including the full list of members.
     */
    public Map<String, Object> getFullCollectivityData(String id) {
        // This now matches the @Autowired field name
        Map<String, Object> data = collectivityRepository.findCollectivityWithMembers(id);
        if (data == null) {
            throw new RuntimeException("Collectivity not found: " + id);
        }
        return data;
    }
}