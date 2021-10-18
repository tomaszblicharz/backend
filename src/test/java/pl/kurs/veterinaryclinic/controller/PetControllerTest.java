package pl.kurs.veterinaryclinic.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.kurs.veterinaryclinic.model.Owner;
import pl.kurs.veterinaryclinic.model.Pet;
import pl.kurs.veterinaryclinic.repository.OwnerRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void shouldReturnSelectedGetPatientById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pet/{id}", 5))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Pet pet = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Pet.class);

        assertThat(pet).isNotNull();
        assertThat(pet.getId()).isEqualTo(5L);
        assertThat(pet.getOwner().getId()).isEqualTo(4L);
    }

    @Test
    void shouldReturn404WhenWrongIdGetPatientById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pet/20"))
                .andExpect(status().is(404))
                .andReturn();

        String actual = mvcResult.getResolvedException().getMessage();

        assertEquals("No entity found", actual);
    }

    @Test
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    void shouldGetAllPatients() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pet").param("page", "2").param("size", "2"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        Pet[] pets = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Pet[].class);

        assertEquals("Roger", pets[0].getName());
        assertEquals("Konstantyn", pets[0].getOwner().getFirstName());
        assertEquals("Pers", pets[1].getBreed());
        assertEquals("Zgoda", pets[1].getOwner().getLastName());
    }

    @Test
    @Transactional
    @Sql({"/db.deletes/delete-rows-veterinary-clinic-test-visits.sql"})
    @Sql({"/db.deletes/delete-rows-veterinary-clinic-test-pets.sql"})
    void shouldReturn404WhenEmptyGetAllPet() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pet").param("page", "0").param("size", "4"))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();

        String actual = mvcResult.getResolvedException().getMessage();

        assertEquals("There is no id in entity", actual);

    }

    @Test
    @Transactional
    void shouldPostSavePet() throws Exception {

        Owner newOwner = new Owner();
        newOwner.setFirstName("test");
        newOwner.setLastName("test");
        newOwner.setEmail("test@gmail.com");
        Pet newPet = new Pet();
        newPet.setName("Happy");
        newPet.setBreed("Szpic miniaturowy");
        newPet.setSpecs("Pies");
        newPet.setBirthDate(LocalDate.parse("1000-10-10"));
        newPet.setOwner(newOwner);
        Owner savedOwner = ownerRepository.save(newOwner);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/pet").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPet)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Pet savedPet = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Pet.class);

        assertThat(savedPet.getName()).isEqualTo("Happy");
        assertThat(savedPet.getBreed()).isEqualTo("Szpic miniaturowy");
        assertThat(savedPet.getOwner().getId()).isEqualTo(savedOwner.getId());

    }

    @Test
    @Transactional
    void shouldReturn404WhenFirstNameIsBlankInPostSavePet() throws Exception {

        Owner newOwner = new Owner();
        newOwner.setFirstName("test");
        newOwner.setLastName("test");
        newOwner.setEmail("test@gmail.com");
        Pet newPet = new Pet();
        newPet.setName("");
        newPet.setBreed("Szpic miniaturowy");
        newPet.setSpecs("Pies");
        newPet.setBirthDate(LocalDate.parse("1000-10-10"));
        newPet.setOwner(newOwner);

        mockMvc.perform(MockMvcRequestBuilders.post("/pet").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPet)))
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    @Transactional
    void shouldReturn404WhenOwnerIsNullInPostSavePatient() throws Exception {

        Owner newOwner = new Owner();
        newOwner.setFirstName("test");
        newOwner.setLastName("test");
        newOwner.setEmail("test@gmail.com");
        Pet newPet = new Pet();
        newPet.setName("Happy");
        newPet.setBreed("Szpic miniaturowy");
        newPet.setSpecs("Pies");
        newPet.setBirthDate(LocalDate.parse("1000-10-10"));
        newPet.setOwner(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/pet").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPet)))
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn();
    }


}