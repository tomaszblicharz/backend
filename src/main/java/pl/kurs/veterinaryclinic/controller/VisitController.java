package pl.kurs.veterinaryclinic.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.veterinaryclinic.dto.DoctorCheckDto;
import pl.kurs.veterinaryclinic.dto.VisitDto;
import pl.kurs.veterinaryclinic.exceptions.NoEntityException;
import pl.kurs.veterinaryclinic.exceptions.VisitException;
import pl.kurs.veterinaryclinic.exceptions.WrongIdException;
import pl.kurs.veterinaryclinic.model.Visit;
import pl.kurs.veterinaryclinic.services.VisitManagementService;


@RestController
@RequestMapping("/visit")
@Validated
public class VisitController {

    private final VisitManagementService visitService;
    private final ModelMapper mapper;

    public VisitController(VisitManagementService visitService, ModelMapper mapper) {
        this.visitService = visitService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitDto> getVisitById(@PathVariable long id) throws NoEntityException {
        Visit loadedVisit = visitService.getById(id);
        return ResponseEntity.ok(mapper.map(loadedVisit, VisitDto.class));
    }

    @GetMapping
    public ResponseEntity<List<VisitDto>> getAllVisits(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "4") int size) throws WrongIdException {
        List<VisitDto> visitDtoList = visitService.getAll(PageRequest.of(page, size))
                                                  .stream()
                                                  .map(x -> mapper.map(x, VisitDto.class))
                                                  .collect(Collectors.toList());

        return ResponseEntity.ok(visitDtoList);
    }

    @PostMapping
    public ResponseEntity<VisitDto> saveVisit(@RequestBody @Valid VisitDto visitDto) throws NoEntityException, VisitException {
        Visit savedVisit = visitService.add(mapper.map(visitDto, Visit.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(savedVisit, VisitDto.class));
    }

    @GetMapping("/confirm/{name}")
    public ResponseEntity<String> confirmVisitByToken(@PathVariable String name) throws VisitException {
        visitService.acceptVisit(name);
        return ResponseEntity.status(HttpStatus.OK).body("Visit confirmed!");
    }

    @GetMapping("/cancel/{name}")
    public ResponseEntity<String> deleteTokenByName(@PathVariable String name) throws VisitException {
        visitService.cancelVisit(name);
        return ResponseEntity.status(HttpStatus.OK).body("Visit deleted!");
    }

    @PostMapping("/check")
    public ResponseEntity<List<DoctorCheckDto>> checkVisit(@RequestParam String medicalSpec, String animalTypeSpec, LocalDateTime from,
                                                           LocalDateTime to) throws VisitException {
        return ResponseEntity.ok(visitService.findVisitByParameters(medicalSpec, animalTypeSpec, from, to));
    }

}
