package pl.kurs.veterinaryclinic.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.veterinaryclinic.dto.OwnerDto;
import pl.kurs.veterinaryclinic.exceptions.*;
import pl.kurs.veterinaryclinic.model.Owner;
import pl.kurs.veterinaryclinic.services.OwnerManagementService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/owner")
@Validated
public class OwnerController {
    private final OwnerManagementService service;
    private final ModelMapper mapper;

    public OwnerController(OwnerManagementService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<OwnerDto> saveOwner(@RequestBody @Valid OwnerDto ownerDto) throws WrongIdException, NoEntityException, DuplicateNipException, VisitException, MessagingException, DateException {
        Owner savedOwner = service.add(mapper.map(ownerDto, Owner.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(savedOwner, OwnerDto.class));
    }

}
