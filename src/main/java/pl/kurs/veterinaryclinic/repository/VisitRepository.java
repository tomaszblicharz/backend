package pl.kurs.veterinaryclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.veterinaryclinic.model.Visit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Visit findVisitByDateBetween(LocalDateTime from, LocalDateTime to);

    Optional<Visit> findByDate(LocalDateTime date);

    List<Visit> findVisitByDoctor_MedicalSpecializationsNameAndDoctor_PetSpecializationsNameAndDateBetween(String medicalSpec,
                                                                           String animalTypeSpec,
                                                                           LocalDateTime from,
                                                                           LocalDateTime to);

}
