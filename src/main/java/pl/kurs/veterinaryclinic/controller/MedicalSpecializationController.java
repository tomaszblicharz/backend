package pl.kurs.veterinaryclinic.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.veterinaryclinic.dto.MedicalSpecializationDto;
import pl.kurs.veterinaryclinic.exceptions.*;
import pl.kurs.veterinaryclinic.model.MedicalSpecialization;
import pl.kurs.veterinaryclinic.services.MedicalSpecializationService;
import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/medspecialization")
@Validated
public class MedicalSpecializationController {

    private final MedicalSpecializationService service;
    private final ModelMapper mapper;

    public MedicalSpecializationController(MedicalSpecializationService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<MedicalSpecializationDto> saveMedSpec(@RequestBody @Valid MedicalSpecializationDto medicalSpecializationDto) throws WrongIdException, NoEntityException, DuplicateNipException, MessagingException, DateException {
        MedicalSpecialization savedMedSpec = service.add(mapper.map(medicalSpecializationDto, MedicalSpecialization.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(savedMedSpec, MedicalSpecializationDto.class));
    }
}


