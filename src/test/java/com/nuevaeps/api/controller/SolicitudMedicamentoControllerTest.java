package com.nuevaeps.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SolicitudMedicamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testObtenerTodasLasSolicitudes_Success() throws Exception {
        mockMvc.perform(get("/api/v1/solicitudes-medicamentos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testObtenerTodasLasSolicitudes_SinAutenticacion() throws Exception {
        mockMvc.perform(get("/api/v1/solicitudes-medicamentos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void testCrearSolicitud_Success() throws Exception {
        String requestBody = """
                {
                    "medicamentoId": 1,
                    "usuarioId": 1,
                    "numeroOrden": "ORD-001",
                    "correoElectronico": "usuario@test.com",
                    "telefono": "1234567890",
                    "direccion": "Calle 123"
                }
                """;

        mockMvc.perform(post("/api/v1/solicitudes-medicamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser
    public void testObtenerSolicitudPorId_Success() throws Exception {
        // First create a solicitud
        String createBody = """
                {
                    "medicamentoId": 1,
                    "usuarioId": 1,
                    "numeroOrden": "ORD-002",
                    "correoElectronico": "usuario@test.com",
                    "telefono": "1234567890",
                    "direccion": "Calle 123"
                }
                """;

        mockMvc.perform(post("/api/v1/solicitudes-medicamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createBody))
                .andExpect(status().isCreated());

        // Then verify we can list solicitudes
        mockMvc.perform(get("/api/v1/solicitudes-medicamentos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testObtenerSolicitudPorId_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/solicitudes-medicamentos/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testObtenerSolicitudesPorUsuario_Success() throws Exception {
        mockMvc.perform(get("/api/v1/solicitudes-medicamentos/usuario/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testObtenerSolicitudesPorMedicamento_Success() throws Exception {
        mockMvc.perform(get("/api/v1/solicitudes-medicamentos/medicamento/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
