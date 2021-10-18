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
import pl.kurs.veterinaryclinic.dto.DoctorCheckDto;
import pl.kurs.veterinaryclinic.model.*;
import pl.kurs.veterinaryclinic.repository.*;
import javax.transaction.Transactional;

import java.time.Instant;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class VisitControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    VisitRepository visitRepository;

    @Test
    void shouldReturnSelectedGetVisitById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/visit/{id}", 5))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Visit visit = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Visit.class);

        assertThat(visit).isNotNull();
        assertThat(visit.getId()).isEqualTo(5L);
        assertThat(visit.getDoctor().getId()).isEqualTo(5L);
        assertThat(visit.getPet().getId()).isEqualTo(5L);
    }

    @Test
    void shouldReturn404WhenGetHaveWrongId() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/visit/{id}", 200))
                .andExpect(status().is(404))
                .andReturn();

        String actual = mvcResult.getResolvedException().getMessage();

        assertEquals("Visit not found.", actual);
    }

    @Test
    void shouldReturnGetAllVisits() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/visit").param("page", "1").param("size", "3"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        Visit[] visit = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Visit[].class);

        assertEquals(4, visit[0].getDoctor().getId());
        assertEquals(4, visit[0].getPet().getId());
    }

    @Test
    @Transactional
    @Sql({"/db.deletes/delete-rows-veterinary-clinic-test-visits.sql"})
    void shouldReturn404WhenListIsEmptyGetAllVisits() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/visit").param("page", "1").param("size", "3"))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();

        String actual = mvcResult.getResolvedException().getMessage();

        assertEquals("There is no id in entity", actual);
    }


    @Test
    void shouldConfirmVisitByToken() throws Exception {

        Token token = new Token();
        Random random = new Random();
        token.setValue(String.valueOf(random.nextDouble()));
        token.setStatus(false);
        token.setInstantTo(Instant.now().plusSeconds(10000000L));
        Token savedToken = tokenRepository.save(token);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/visit/confirm/{value}", savedToken.getValue()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

    }

    @Test
    void shouldReturn400WhenTokenIsAlreadyConfirmedVisitByToken() throws Exception {

        Token token = new Token();
        Random random = new Random();
        token.setValue(String.valueOf(random.nextDouble()));
        token.setStatus(false);
        token.setInstantTo(Instant.now().plusSeconds(10000000L));
        Token savedToken = tokenRepository.save(token);
        tokenRepository.updateStateByToken(tokenRepository.findByValue(savedToken.getValue()).getId());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/visit/confirm/{value}", savedToken.getValue()))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        String actual = mvcResult.getResolvedException().getMessage();
        assertEquals(actual,"The visit has already been confirmed");

    }

    @Test
    void shouldDeleteTokenByName() throws Exception {

        Token token = new Token();
        Random random = new Random();
        token.setValue(String.valueOf(random.nextDouble()));
        token.setStatus(false);
        token.setInstantTo(Instant.now().plusSeconds(10000000L));
        token.setVisit(visitRepository.save(new Visit()));
        Token savedToken = tokenRepository.save(token);

        mockMvc.perform(MockMvcRequestBuilders.get("/visit/cancel/{value}", savedToken.getValue()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    @Transactional
    void shouldReturn400WhenTokenNoExistDeleteTokenByName() throws Exception {

        Token token = new Token();
        Random random = new Random();
        token.setValue(String.valueOf(random.nextDouble()));
        token.setStatus(false);
        token.setInstantTo(Instant.now().plusSeconds(10000000L));
        token.setVisit(visitRepository.save(new Visit()));
        Token savedToken = tokenRepository.save(token);

        mockMvc.perform(MockMvcRequestBuilders.get("/visit/cancel/{value}",savedToken.getValue()+"test"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
    @Test
    void shouldReturnFreeVisitPostCheckVisit() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/visit/check")
                .param("medicalSpec", "Neurolog")
                .param("animalTypeSpec", "Pies")
                .param("from", "2021-11-10 08:00:00")
                .param("to", "2021-11-20 20:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        DoctorCheckDto[] doctorCheckDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DoctorCheckDto[].class);

        assertThat(doctorCheckDto[0].getFirstName()).isEqualTo("Marietta");
        assertThat(doctorCheckDto[0].getLastName()).isEqualTo("Kubera");
        assertThat(doctorCheckDto[0].getId()).isEqualTo(5L);
    }

    @Test
    void shouldReturn400WhenVisitNoExistPostCheckVisit() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/visit/check")
                .param("medicalSpec", "Alergolog")
                .param("animalTypeSpec", "Pies")
                .param("from", "2021-11-10 08:00:00")
                .param("to", "2021-11-20 20:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400))
                .andReturn();

        String actual = mvcResult.getResolvedException().getMessage();

        assertEquals("No visit available", actual);
    }
}