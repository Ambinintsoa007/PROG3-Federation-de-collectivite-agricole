package Federation.Agricole.API.entity;

import java.util.List;

public class CreateCollectivity {
    private String location;
    private List<String> members;
    private boolean federationApproval;
    private CreateCollectivityStructure structure;

    public CreateCollectivity(String location, List<String> members, boolean federationApproval, CreateCollectivityStructure structure ) {
        this.location = location;
        this.members = members;
        this.federationApproval = federationApproval;
        this.structure = structure;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isFederationApproval() {
        return federationApproval;
    }

    public void setFederationApproval(boolean federationApproval) {
        this.federationApproval = federationApproval;
    }

    public CreateCollectivityStructure getStructure() {
        return structure;
    }

    public void setStructure(CreateCollectivityStructure structure) {
        this.structure = structure;
    }
}
