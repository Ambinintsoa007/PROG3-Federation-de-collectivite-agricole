package Federation.Agricole.API.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@Setter
public class Member {
    private String id;
    private  String firstName;
    private  String lastName;
    private LocalDate birthday;
    private Gender gender;
    private String address;
    private String profession;
    private int phoneNumber;
    private String email;
    private MemberOccupation occupation;
    private boolean isFounder;

    public Member(){}
}
