package pl.kurs.veterinaryclinic.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor extends Person {

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    private Set<MedicalSpecialization> medicalSpecializations = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    private Set<PetSpecialization> petSpecializations = new HashSet<>();

    private Double salaryForHour;
    private String nip;
    private Boolean status;

    public Doctor() {
    }

    public Set<MedicalSpecialization> getMedicalSpecializations() {
        return medicalSpecializations;
    }

    public void setMedicalSpecializations(Set<MedicalSpecialization> medicalSpecializations) {
        this.medicalSpecializations = medicalSpecializations;
    }

    public Set<PetSpecialization> getPetSpecializations() {
        return petSpecializations;
    }

    public void setPetSpecializations(Set<PetSpecialization> petSpecializations) {
        this.petSpecializations = petSpecializations;
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
