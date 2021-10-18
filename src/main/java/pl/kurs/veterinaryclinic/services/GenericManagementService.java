package pl.kurs.veterinaryclinic.services;


import java.util.List;
import javax.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.veterinaryclinic.exceptions.*;
import pl.kurs.veterinaryclinic.interfaces.Identificationable;
import pl.kurs.veterinaryclinic.interfaces.ManagementService;

public class GenericManagementService<T extends Identificationable, U extends JpaRepository<T, Long>> implements ManagementService<T> {

    protected U repository;

    public GenericManagementService(U repository) {
        this.repository = repository;
    }

    @Override
    public T add(T entity) throws NoEntityException, WrongIdException, DuplicateNipException, MessagingException,
                                  DateException {
        if (entity == null) {
            throw new NoEntityException("No entity for add!");
        }
        if (entity.getId() != null) {
            throw new WrongIdException("There is an id in entity");
        }
        return repository.save(entity);
    }

    @Override
    public T getById(Long id) throws NoEntityException {
        return repository.findById(id)
                         .orElseThrow(() -> new NoEntityException("No entity found"));
    }

    @Override
    public Page<T> showAll(Pageable pageable) throws WrongIdException {
        if (repository.findAll(pageable).getNumberOfElements() == 0) {
            throw new WrongIdException("There is no id in entity");
        }
        return repository.findAll(pageable);

    }


}
