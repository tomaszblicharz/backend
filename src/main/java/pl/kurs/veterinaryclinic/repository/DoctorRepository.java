package pl.kurs.veterinaryclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.veterinaryclinic.model.Doctor;

import java.util.Optional;


public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    Optional<Doctor> findByNip(String nip);

    @Modifying
    @Query("update Doctor d set d.status = ?2 where d.id = ?1")
    void updateStateByID(Long doctorId, boolean status);


}
