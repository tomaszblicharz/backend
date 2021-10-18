package pl.kurs.veterinaryclinic.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class OwnerDto {
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Email(message = "Wrong email address")
    @NotBlank
    private String email;

    public OwnerDto() {
    }

    public OwnerDto(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
