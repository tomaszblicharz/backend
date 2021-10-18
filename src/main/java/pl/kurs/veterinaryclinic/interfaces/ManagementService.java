package pl.kurs.veterinaryclinic.interfaces;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.kurs.veterinaryclinic.exceptions.*;

import javax.mail.MessagingException;


public interface ManagementService<T> {

    T add(T entity) throws NoEntityException, WrongIdException, DuplicateNipException, VisitException, MessagingException, DateException;
    T getById(Long id) throws WrongIdException, NoEntityException;
    Page<T> showAll(Pageable pageable) throws WrongIdException;

}
