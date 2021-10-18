package pl.kurs.veterinaryclinic.services;

import org.springframework.stereotype.Service;
import pl.kurs.veterinaryclinic.model.PetSpecialization;
import pl.kurs.veterinaryclinic.repository.PetSpecializationRepository;
@Service
public class PetSpecializationService extends GenericManagementService<PetSpecialization, PetSpecializationRepository> {
    public PetSpecializationService(PetSpecializationRepository repository) {
        super(repository);
    }
}
