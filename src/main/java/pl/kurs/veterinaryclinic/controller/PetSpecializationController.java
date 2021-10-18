package pl.kurs.veterinaryclinic.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.veterinaryclinic.dto.PetSpecializationDto;
import pl.kurs.veterinaryclinic.exceptions.*;
import pl.kurs.veterinaryclinic.model.PetSpecialization;
import pl.kurs.veterinaryclinic.services.PetSpecializationService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/petspecialization")
@Validated
public class PetSpecializationController {

    private final PetSpecializationService service;
    private final ModelMapper mapper;

    public PetSpecializationController(PetSpecializationService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PetSpecializationDto> savePetSpec(@RequestBody @Valid PetSpecializationDto petSpecializationDto) throws WrongIdException, NoEntityException, DuplicateNipException, VisitException, MessagingException, DateException {
        PetSpecialization savedPetSpec = service.add(mapper.map(petSpecializationDto, PetSpecialization.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(savedPetSpec, PetSpecializationDto.class));
    }

}