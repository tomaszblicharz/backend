package pl.kurs.veterinaryclinic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import pl.kurs.veterinaryclinic.model.Visit;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;


public class TokenDto {
    private Long id;

    @NotBlank
    private String name;
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Visit visit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Instant instantTo;
    @Column(columnDefinition = "boolean default false")
    private Boolean status;

    public TokenDto() {
    }

    public TokenDto(String name, Visit visit, Instant instantTo, Boolean status) {
        this.name = name;
        this.visit = visit;
        this.instantTo = instantTo;
        this.status = status;
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

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public Instant getInstantTo() {
        return instantTo;
    }

    public void setInstantTo(Instant instantTo) {
        this.instantTo = instantTo;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
