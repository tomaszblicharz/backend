package pl.kurs.veterinaryclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.veterinaryclinic.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner,Long> {
}

