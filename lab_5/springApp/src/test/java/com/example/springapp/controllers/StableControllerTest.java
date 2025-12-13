package com.example.springapp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllStables_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/stable"))
                .andExpect(status().isOk());
    }

    @Test
    void getCsv_shouldReturn404_whenStableNotFound() throws Exception {
        mockMvc.perform(get("/api/stable/999/csv"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStable_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(delete("/api/stable/999"))
                .andExpect(status().isNotFound());
    }
}
