package pl.kurs.veterinaryclinic.dto;

import pl.kurs.veterinaryclinic.model.Doctor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PetSpecializationDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Doctor doctor;

    public PetSpecializationDto() {
    }

    public PetSpecializationDto(String name, Doctor doctor) {
        this.name = name;
        this.doctor = doctor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
