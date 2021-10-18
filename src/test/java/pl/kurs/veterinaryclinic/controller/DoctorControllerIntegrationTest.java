package pl.kurs.veterinaryclinic.controller;

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
import pl.kurs.veterinaryclinic.model.Doctor;
import pl.kurs.veterinaryclinic.repository.DoctorRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class DoctorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    void shouldReturnSelectedDoctorById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/doctor/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Doctor doctor = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Doctor.class);

        assertThat(doctor).isNotNull();
        assertThat(doctor.getId()).isEqualTo(1L);
        assertThat(doctor.getStatus()).isEqualTo(true);
        assertThat(doctor.getNip()).hasSize(10);
    }

    @Test
    void shouldReturn404WhenGetHaveWrongId() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/doctor/20"))
                .andExpect(status().is(404))
                .andReturn();

        String actual = mvcResult.getResolvedException().getMessage();

        assertEquals("No entity found", actual);

    }

    @Test
    void shouldReturnGetAllDoctors() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/doctor").param("page", "0").param("size", "6"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        Doctor[] doctors = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Doctor[].class);

        assertEquals("Adam", doctors[0].getFirstName());
        assertEquals(10, doctors[5].getSalaryForHour());
    }


    @Test
    @Transactional
    @Sql({"/db.deletes/delete-rows-veterinary-clinic-test-medical-specializations.sql"})
    @Sql({"/db.deletes/delete-rows-veterinary-clinic-test-pet-specializations.sql"})
    @Sql({"/db.deletes/delete-rows-veterinary-clinic-test-visits.sql"})
    @Sql({"/db.deletes/delete-rows-veterinary-clinic-test-doctors.sql"})
    void shouldReturn404WhenEmptyGetAllDoctors() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/doctor").param("page", "0").param("size", "4"))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();

        String actual = mvcResult.getResolvedException().getMessage();

        assertEquals("There is no id in entity", actual);
    }

    @Test
    @Transactional
    void shouldSaveDoctor() throws Exception {

        Doctor newDoctor = new Doctor();
        newDoctor.setFirstName("Janek");
        newDoctor.setLastName("Barszcz");
        newDoctor.setNip("9022713412");
        newDoctor.setSalaryForHour(100D);
        newDoctor.setStatus(true);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/doctor").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDoctor)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Doctor savedDoctor = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Doctor.class);

        assertThat(savedDoctor.getNip()).isEqualTo("9022713412");
        assertThat(savedDoctor.getSalaryForHour()).isEqualTo(100D);
    }

    @Test
    @Transactional
    void shouldReturn404WhenNipIsTheSameSaveDoctor() throws Exception {

        Doctor newDoctor = new Doctor();
        newDoctor.setFirstName("Janek");
        newDoctor.setLastName("Barszcz");
        newDoctor.setNip("1234567890");
        newDoctor.setSalaryForHour(100D);
        newDoctor.setStatus(true);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/doctor").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDoctor)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        String actual = mvcResult.getResolvedException().getMessage();
        assertEquals("Doctor with same NIP exists", actual);
    }

    @Test
    @Transactional
    void shouldReturn400WhenNipSizeIsWrongSaveDoctor() throws Exception {

        Doctor newDoctor = new Doctor();
        newDoctor.setFirstName("Janek");
        newDoctor.setLastName("Barszcz");
        newDoctor.setNip("12345678901");
        newDoctor.setSalaryForHour(100D);
        newDoctor.setStatus(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDoctor)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

    }

    @Test
    @Transactional
    void shouldSoftDeleteDoctor() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/doctor/fire/{id}", 2))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        assertEquals("Changed status of given doctor, this doctor will not be able to handle any visits.", actualResponseBody);

    }

    @Test
    @Transactional
    void shouldReturn404IfIdIsWrongSoftDeleteDoctor() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/doctor/fire/{id}", 111))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();

        assertThat(mvcResult.getResolvedException().getMessage()).isEqualTo("No entity found");
    }

    @Test
    @Transactional
    void shouldReturn404IfDoctorIsAlreadyDeletedSoftDeleteDoctor() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/doctor/fire/{id}", 7))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();

        assertEquals("This doctor doesn't work anymore!", mvcResult.getResolvedException().getMessage());
    }
}