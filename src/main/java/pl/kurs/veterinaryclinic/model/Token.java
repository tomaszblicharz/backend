package pl.kurs.veterinaryclinic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "tokens")
public class Token extends BaseEntity {

    private String value;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Visit visit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Instant instantTo;
    private Boolean status;

    public Token() {
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
