package pl.kurs.veterinaryclinic.services;

import org.assertj.core.api.SoftAssertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import pl.kurs.veterinaryclinic.exceptions.*;
import pl.kurs.veterinaryclinic.model.Doctor;
import pl.kurs.veterinaryclinic.repository.DoctorRepository;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class DoctorManagementServiceTest {

    @Autowired
    private DoctorManagementService service;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void shouldGetDoctorById() throws NoEntityException {
        Doctor show = service.getById(1L);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(show).isNotNull();
        softAssertions.assertThat(show.getId()).isEqualTo(1L);
        softAssertions.assertThat(show.getFirstName()).isEqualTo("Adam");
        softAssertions.assertThat(show.getNip()).isEqualTo("1234567891");
        softAssertions.assertAll();
    }

    @Test
    void shouldThrowNoEntityExceptionWhenIdNotExistGetDoctorById(){

        Throwable expectedException = new NoEntityException("No entity found");

        try{
            service.getById(100L);
        }catch (Throwable e){

            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(e.getMessage()).isEqualTo("No entity found");
            softAssertions.assertThat(e.getClass()).isEqualTo(expectedException.getClass());
            softAssertions.assertAll();
        }

    }

    @Test
    @Transactional
    void shouldAddDoctor() throws NoEntityException, DuplicateNipException, MessagingException, DateException, WrongIdException {

        Doctor newDoctor = new Doctor();
        newDoctor.setFirstName("Janek");
        newDoctor.setLastName("Barszcz");
        newDoctor.setNip("12345678901");
        newDoctor.setSalaryForHour(100D);
        newDoctor.setStatus(true);

        Doctor addedDoctor = service.add(newDoctor);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(addedDoctor.getNip()).isEqualTo("12345678901");
        softAssertions.assertThat(addedDoctor.getStatus()).isEqualTo(true);
        softAssertions.assertAll();
    }

    @Test
    void shouldThrowDuplicateNipAddDoctor() {

        Doctor newDoctor = new Doctor();
        newDoctor.setFirstName("Janek");
        newDoctor.setLastName("Barszcz");
        newDoctor.setNip("1234567891");
        newDoctor.setSalaryForHour(100D);
        newDoctor.setStatus(true);
        Throwable expectedException = new DuplicateNipException("Doctor with same NIP exists");

        try{
            service.add(newDoctor);
        }catch (Throwable e){

            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(e.getMessage()).isEqualTo("Doctor with same NIP exists");
            softAssertions.assertThat(e.getClass()).isEqualTo(expectedException.getClass());
            softAssertions.assertAll();
        }
    }


    @Test
    @Transactional
    void shouldThrowExceptionSoftDelete() {

        Doctor doctor = doctorRepository.getById(7L);
        Throwable expectedException = new WrongIdException("This doctor doesn't work anymore!");

        try{
            service.softDelete(doctor.getId());
        }catch (Throwable e){

            SoftAssertions softAssertions = new SoftAssertions();
            softAssertions.assertThat(e.getMessage()).isEqualTo("This doctor doesn't work anymore!");
            softAssertions.assertThat(e.getClass()).isEqualTo(expectedException.getClass());
            softAssertions.assertAll();
        }

    }
}