package pl.kurs.veterinaryclinic.services;

import org.springframework.stereotype.Service;
import pl.kurs.veterinaryclinic.model.MedicalSpecialization;
import pl.kurs.veterinaryclinic.repository.MedicalSpecializationRepository;
@Service
public class MedicalSpecializationService  extends GenericManagementService<MedicalSpecialization, MedicalSpecializationRepository>{
    public MedicalSpecializationService(MedicalSpecializationRepository repository) {
        super(repository);
    }
}
