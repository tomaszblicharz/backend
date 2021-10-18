package pl.kurs.veterinaryclinic.services;

import org.springframework.stereotype.Service;
import pl.kurs.veterinaryclinic.model.Owner;
import pl.kurs.veterinaryclinic.repository.OwnerRepository;

@Service
public class OwnerManagementService extends GenericManagementService<Owner,OwnerRepository> {
    public OwnerManagementService(OwnerRepository repository) {
        super(repository);
    }
}
