package pl.kurs.veterinaryclinic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.kurs.veterinaryclinic.model.Owner;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class PetDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String specs;//gatunek
    @NotBlank
    private String breed;//rasa
    @NotNull
    private Owner owner;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull
    private LocalDate birthDate;

    public PetDto() {
    }


    public PetDto(Long id, String name, String specs, String breed, Owner owner, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.specs = specs;
        this.breed = breed;
        this.owner = owner;
        this.birthDate = birthDate;
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

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
