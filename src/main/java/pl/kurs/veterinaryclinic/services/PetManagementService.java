package pl.kurs.veterinaryclinic.services;

import org.springframework.stereotype.Service;
import pl.kurs.veterinaryclinic.exceptions.*;
import pl.kurs.veterinaryclinic.model.Owner;
import pl.kurs.veterinaryclinic.model.Pet;
import pl.kurs.veterinaryclinic.repository.OwnerRepository;
import pl.kurs.veterinaryclinic.repository.PetRepository;

import javax.mail.MessagingException;
import java.time.LocalDate;

@Service
public class PetManagementService extends GenericManagementService<Pet, PetRepository> {

    private OwnerManagementService ownerManagementService;

    public PetManagementService(PetRepository repository, OwnerManagementService ownerManagementService) {
        super(repository);
        this.ownerManagementService = ownerManagementService;
    }

    @Override
    public Pet add(Pet entity) throws NoEntityException, WrongIdException, DuplicateNipException, MessagingException, DateException {
        if(entity.getBirthDate().isAfter(LocalDate.now()))
            throw new DateException("Wrong date");
        ownerManagementService.repository.findById(entity.getOwner().getId()).orElseThrow(() -> new NoEntityException("No entity found of owner"));
        return super.add(entity);
    }
}
