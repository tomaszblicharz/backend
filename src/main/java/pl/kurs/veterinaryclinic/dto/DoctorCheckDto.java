package pl.kurs.veterinaryclinic.dto;

import java.time.LocalDateTime;

public class DoctorCheckDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime time;

    public DoctorCheckDto(Long id, String firstName, String lastName, LocalDateTime time) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.time = time;
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

    public LocalDateTime getLocalDateTime() {
        return time;
    }

    public void setLocalDateTime(LocalDateTime time) {
        this.time = time;
    }
}
