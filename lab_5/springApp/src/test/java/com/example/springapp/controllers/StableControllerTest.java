package com.example.springapp.controllers;

import com.example.springapp.services.StableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StableController.class)
class StableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StableService stableService;

    // GET /api/stable
    @Test
    void shouldGetAllStables() throws Exception {
        mockMvc.perform(get("/api/stable"))
                .andExpect(status().isOk());
    }

    // GET /api/stable/{id}
    @Test
    void shouldGetHorsesInStable() throws Exception {
        mockMvc.perform(get("/api/stable/1"))
                .andExpect(status().isOk());
    }

    // GET /api/stable/{id}/csv
    @Test
    void shouldReturnCsv() throws Exception {
        mockMvc.perform(get("/api/stable/1/csv"))
                .andExpect(status().isOk());
    }

    // POST /api/stable
    @Test
    void shouldAddStable() throws Exception {
        mockMvc.perform(post("/api/stable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "name": "Stajnia AGH",
                              "capacity": 10
                            }
                        """))
                .andExpect(status().isCreated());
    }

    // DELETE /api/stable/{id}
    @Test
    void shouldDeleteStable() throws Exception {
        mockMvc.perform(delete("/api/stable/1"))
                .andExpect(status().isNoContent());
    }

    // GET /api/stable/{id}/fill
    @Test
    void shouldReturnFill() throws Exception {
        mockMvc.perform(get("/api/stable/1/fill"))
                .andExpect(status().isOk());
    }
}
