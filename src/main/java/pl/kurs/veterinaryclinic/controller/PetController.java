package pl.kurs.veterinaryclinic.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.veterinaryclinic.dto.PetDto;
import pl.kurs.veterinaryclinic.exceptions.*;
import pl.kurs.veterinaryclinic.model.Pet;
import pl.kurs.veterinaryclinic.services.PetManagementService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pet")
@Validated
public class PetController {
    private final PetManagementService service;
    private final ModelMapper mapper;

    public PetController(PetManagementService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPetById(@PathVariable long id) throws NoEntityException {
        Pet loadedPet = service.getById(id);
        return ResponseEntity.ok(mapper.map(loadedPet, PetDto.class));
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "4") int size) throws WrongIdException {
        List<PetDto> petDtoList = service.showAll(PageRequest.of(page, size))
                .stream()
                .map(x -> mapper.map(x, PetDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(petDtoList);
    }

    @PostMapping
    public ResponseEntity<PetDto> savePet(@RequestBody @Valid PetDto petDto) throws WrongIdException, NoEntityException, DuplicateNipException, VisitException, MessagingException, DateException {
        Pet savedPet = service.add(mapper.map(petDto, Pet.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(savedPet, PetDto.class));
    }

}
