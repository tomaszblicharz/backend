package pl.kurs.veterinaryclinic.services;

import org.springframework.stereotype.Service;
import pl.kurs.veterinaryclinic.exceptions.*;
import pl.kurs.veterinaryclinic.model.Doctor;
import pl.kurs.veterinaryclinic.repository.DoctorRepository;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Service
@Transactional
public class DoctorManagementService extends GenericManagementService<Doctor, DoctorRepository> {

    public DoctorManagementService(DoctorRepository repository) {
        super(repository);
    }

    @Override
    public Doctor add(Doctor entity) throws NoEntityException, WrongIdException, DuplicateNipException, MessagingException, DateException {
        if (repository.findByNip(entity.getNip()).isPresent())
            throw new DuplicateNipException("Doctor with same NIP exists");
        return super.add(entity);
    }

    public void softDelete(Long id) throws WrongIdException, NoEntityException {
        repository.findById(id).orElseThrow(() -> new NoEntityException("No entity found"));
        if (!repository.getById(id).getStatus())
            throw new WrongIdException("This doctor doesn't work anymore!");
        repository.updateStateByID(id, false);
    }
}
