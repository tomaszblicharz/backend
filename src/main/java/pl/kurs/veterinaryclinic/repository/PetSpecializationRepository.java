package pl.kurs.veterinaryclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.veterinaryclinic.model.PetSpecialization;

public interface PetSpecializationRepository extends JpaRepository<PetSpecialization, Long> {

}
