package pl.kurs.veterinaryclinic.model;


import javax.persistence.*;


@Entity
@Table(name = "owners")
public class Owner extends Person{

    private String email;

    public Owner() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
