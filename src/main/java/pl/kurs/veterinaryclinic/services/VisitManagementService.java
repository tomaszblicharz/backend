package pl.kurs.veterinaryclinic.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.kurs.veterinaryclinic.dto.DoctorCheckDto;
import pl.kurs.veterinaryclinic.exceptions.NoEntityException;
import pl.kurs.veterinaryclinic.exceptions.VisitException;
import pl.kurs.veterinaryclinic.exceptions.WrongIdException;
import pl.kurs.veterinaryclinic.model.Token;
import pl.kurs.veterinaryclinic.model.Visit;
import pl.kurs.veterinaryclinic.repository.VisitRepository;

@Service
@Transactional
public class VisitManagementService {

    private final TokenManagementService tokenManagementService;
    private final PetManagementService petManagementService;
    private final DoctorManagementService doctorManagementService;
    private final MailService mailService;
    private final VisitRepository visitRepository;

    public VisitManagementService(TokenManagementService tokenManagementService, PetManagementService petManagementService,
                                  DoctorManagementService doctorManagementService, MailService mailService, VisitRepository visitRepository) {
        this.tokenManagementService = tokenManagementService;
        this.petManagementService = petManagementService;
        this.doctorManagementService = doctorManagementService;
        this.mailService = mailService;
        this.visitRepository = visitRepository;
    }

    public Visit getById(Long id) throws NoEntityException {
        return visitRepository.findById(id)
                .orElseThrow(() -> new NoEntityException("Visit not found."));
    }

    public Page<Visit> getAll(Pageable pageable) throws WrongIdException {
        if (visitRepository.findAll(pageable).getNumberOfElements() == 0) {
            throw new WrongIdException("There is no id in entity");
        }
        return visitRepository.findAll(pageable);
    }

    public Visit add(Visit entity) throws VisitException, NoEntityException {
        if (!doctorManagementService.getById(entity.getDoctor().getId()).getStatus())
            throw new VisitException("This doctor doesn't work here anymore");

        if (visitRepository.findByDate(entity.getDate()).isPresent())
            throw new VisitException("Doctor or patient already has visit that date");

        if (visitRepository.findVisitByDateBetween(entity.getDate().minusHours(1).plusNanos(1000), entity.getDate()) != null)
            throw new VisitException("Doctor or patient already has visit that date");

        if (entity.getDate().isBefore(LocalDateTime.now()))
            throw new VisitException("That date has already passed");

        if (entity.getDate() == null)
            throw new VisitException("Patient can not be null");


        Visit visit = visitRepository.save(entity);
        Token token = tokenManagementService.add(visit);
        sendEmail(entity, token);
        return visit;
    }

    public void acceptVisit(String token) throws VisitException {
        Token savedToken = tokenManagementService.findByValue(token);
        if (savedToken == null)
            throw new VisitException("No visit assigned to this token, wrong token");

        if (Instant.now().isAfter(savedToken.getInstantTo()))
            throw new VisitException("The token has expired, book a new visit");

        if (savedToken.getStatus())
            throw new VisitException("The visit has already been confirmed");

        tokenManagementService.updateStateByToken(savedToken.getId());
    }

    public void cancelVisit(String name) throws VisitException {
        try {
            visitRepository.deleteById(tokenManagementService.findByValue(name).getVisit().getId());
        } catch (NullPointerException e) {
            throw new VisitException("No visit assigned to this token, wrong token");
        }
    }

    public List<DoctorCheckDto> findVisitByParameters(String medicalSpec, String animalTypeSpec, LocalDateTime from,
                                                      LocalDateTime to) throws VisitException {
        if (from.isAfter(to))
            throw new VisitException("Wrong date");


        if (from.isAfter(to))
            throw new VisitException("Wrong date");

        List<Visit> check = visitRepository.findVisitByDoctor_MedicalSpecializationsNameAndDoctor_PetSpecializationsNameAndDateBetween(medicalSpec, animalTypeSpec, from, to);
        List<DoctorCheckDto> doctorCheckDtoList = new ArrayList<>();

        for (Visit visit : check) {
            if (visit.getPet() == null) {
                Long id = visit.getDoctor().getId();
                String firstName = visit.getDoctor().getFirstName();
                String lastName = visit.getDoctor().getLastName();
                LocalDateTime date = visit.getDate();
                doctorCheckDtoList.add(new DoctorCheckDto(id, firstName, lastName, date));
            }
        }
        if (doctorCheckDtoList.isEmpty())
            throw new VisitException("No visit available");
        return doctorCheckDtoList;
    }


    private void sendEmail(Visit visitAdded, Token token) throws NoEntityException, VisitException {
        String emailAddress = petManagementService.getById(visitAdded.getPet().getId()).getOwner().getEmail();
        mailService.sendMailWithActivationToken(emailAddress, token.getValue());
    }

}
