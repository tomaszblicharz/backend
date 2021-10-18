package pl.kurs.veterinaryclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.veterinaryclinic.model.Pet;

public interface PetRepository extends JpaRepository<Pet,Long> {
}
