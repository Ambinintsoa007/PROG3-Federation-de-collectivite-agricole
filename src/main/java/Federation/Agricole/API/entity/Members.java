package Federation.Agricole.API.entity;

public class Members {
    private String id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Genre genre;
    private String address;
    private String profession;
    private int phoneNumber;
    private String email;
    private MemberOccupation memberOccupation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MemberOccupation getMemberOccupation() {
        return memberOccupation;
    }

    public void setMemberOccupation(MemberOccupation memberOccupation) {
        this.memberOccupation = memberOccupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
