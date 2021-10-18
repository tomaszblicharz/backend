package pl.kurs.veterinaryclinic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.kurs.veterinaryclinic.model.Doctor;
import pl.kurs.veterinaryclinic.model.Pet;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class VisitDto {
    private Long id;

    @NotNull
    private Doctor doctor;

    private Pet pet;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @NotNull
    private LocalDateTime date;

    private String description;

    public VisitDto() {
    }

    public VisitDto(Long id, Doctor doctor, Pet pet, LocalDateTime date, String description) {
        this.id = id;
        this.doctor = doctor;
        this.pet = pet;
        this.date = date;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
