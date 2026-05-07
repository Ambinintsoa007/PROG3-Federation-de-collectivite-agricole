package Federation.Agricole.API.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class MemberDescription {
    private String id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String occupation;
}
