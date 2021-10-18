package pl.kurs.veterinaryclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.veterinaryclinic.model.MedicalSpecialization;


public interface MedicalSpecializationRepository extends JpaRepository<MedicalSpecialization,Long> {
}
