package com.example.springapp.controllers;

import com.example.springapp.services.HorseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HorseController.class)
class HorseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HorseService horseService;

    // POST /api/horse
    @Test
    void shouldAddHorse() throws Exception {
        mockMvc.perform(post("/api/horse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "Bucefał",
                              "breed": "Arab",
                              "type": "HOT_BLOODED",
                              "status": "HEALTHY",
                              "age": 5,
                              "price": 10000,
                              "weight": 450,
                              "stableId": 1
                            }
                        """))
                .andExpect(status().isCreated());
    }

    // DELETE /api/horse/{id}
    @Test
    void shouldDeleteHorse() throws Exception {
        mockMvc.perform(delete("/api/horse/1"))
                .andExpect(status().isNoContent());
    }

    // GET /api/horse/rating/{id}
    @Test
    void shouldReturnAverageRating() throws Exception {
        mockMvc.perform(get("/api/horse/rating/1"))
                .andExpect(status().isOk());
    }

    // POST /api/horse/rating
    @Test
    void shouldAddRating() throws Exception {
        mockMvc.perform(post("/api/horse/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "horseId": 1,
                              "value": 5,
                              "description": "Świetny koń"
                            }
                        """))
                .andExpect(status().isCreated());
    }
}
