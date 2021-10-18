package pl.kurs.veterinaryclinic.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.veterinaryclinic.dto.DoctorDto;
import pl.kurs.veterinaryclinic.exceptions.*;
import pl.kurs.veterinaryclinic.model.Doctor;
import pl.kurs.veterinaryclinic.services.DoctorManagementService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctor")
@Validated
public class DoctorController {

    private final DoctorManagementService service;
    private final ModelMapper mapper;

    public DoctorController(DoctorManagementService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable long id) throws NoEntityException {
        Doctor loadedDoctor = service.getById(id);
        return ResponseEntity.ok(mapper.map(loadedDoctor, DoctorDto.class));
    }

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "4") int size) throws WrongIdException {
        List<DoctorDto> doctorDtoList = service.showAll(PageRequest.of(page, size))
                .stream()
                .map(x -> mapper.map(x, DoctorDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctorDtoList);
    }

    @PostMapping
    public ResponseEntity<DoctorDto> saveDoctor(@RequestBody @Valid DoctorDto doctorDto) throws WrongIdException, NoEntityException,
            DuplicateNipException, VisitException, MessagingException, DateException {

        Doctor savedDoctor = service.add(mapper.map(doctorDto, Doctor.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(savedDoctor, DoctorDto.class));
    }

    @PutMapping("/fire/{id}")
    public ResponseEntity<String> softDeleteDoctor(@PathVariable Long id) throws WrongIdException, NoEntityException {
        service.softDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Changed status of given doctor, this doctor will not be able to handle any visits.");
    }
}
