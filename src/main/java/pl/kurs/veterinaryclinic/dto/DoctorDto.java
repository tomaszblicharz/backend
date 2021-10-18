package pl.kurs.veterinaryclinic.dto;

import javax.persistence.*;
import javax.validation.constraints.*;

public class DoctorDto {
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Positive
    private Double salaryForHour;
    @NotBlank
    @Size(min = 10, max = 10)
    private String nip;
    @Column(columnDefinition = "boolean default true")
    private Boolean status;

    public DoctorDto() {
    }

    public DoctorDto(Long id, String firstName, String lastName, Double salaryForHour, String nip, Boolean status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salaryForHour = salaryForHour;
        this.nip = nip;
        this.status = status;
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

    public Double getSalaryForHour() {
        return salaryForHour;
    }

    public void setSalaryForHour(Double salaryForHour) {
        this.salaryForHour = salaryForHour;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
