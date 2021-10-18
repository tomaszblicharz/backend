package pl.kurs.veterinaryclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.kurs.veterinaryclinic.model.Doctor;
import pl.kurs.veterinaryclinic.model.MedicalSpecialization;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class PetSpecializationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @Transactional
    void shouldRet8urn400WhenSetDoctorIsNullPostSaveMedSpec() throws Exception {

        Doctor newDoctor = new Doctor();
        newDoctor.setFirstName("test");
        newDoctor.setLastName("test");
        newDoctor.setNip("1231231231");
        newDoctor.setSalaryForHour(10D);
        MedicalSpecialization newMedSpec = new MedicalSpecialization();
        newMedSpec.setName("test");
        newMedSpec.setDoctor(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/petspecialization").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newMedSpec)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

}