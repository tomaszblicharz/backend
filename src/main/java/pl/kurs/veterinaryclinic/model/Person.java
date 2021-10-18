package pl.kurs.veterinaryclinic.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person extends BaseEntity{

    private String firstName;
    private String lastName;

    public Person() {
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

}
