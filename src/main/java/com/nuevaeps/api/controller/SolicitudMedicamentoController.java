package com.nuevaeps.api.controller;

import com.nuevaeps.api.model.SolicitudMedicamento;
import com.nuevaeps.api.service.SolicitudMedicamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/solicitudes-medicamentos")
@Tag(name = "Solicitudes de Medicamentos", description = "Endpoints para gestionar solicitudes de medicamentos")
public class SolicitudMedicamentoController {

    @Autowired
    private SolicitudMedicamentoService solicitudService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Obtener todas las solicitudes", description = "Retorna una lista de todas las solicitudes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de solicitudes obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = SolicitudMedicamento.class)))
    })
    public ResponseEntity<List<SolicitudMedicamento>> obtenerTodasLasSolicitudes() {
        return ResponseEntity.ok(solicitudService.obtenerTodasLasSolicitudes());
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Obtener solicitudes paginadas", description = "Retorna solicitudes con paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de solicitudes obtenida exitosamente")
    })
    public ResponseEntity<Page<SolicitudMedicamento>> obtenerSolicitudesPaginadas(Pageable pageable) {
        return ResponseEntity.ok(solicitudService.obtenerTodasLasSolicitudesPaginadas(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Obtener solicitud por ID", description = "Retorna una solicitud específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    public ResponseEntity<SolicitudMedicamento> obtenerSolicitudPorId(@PathVariable Long id) {
        return solicitudService.obtenerSolicitudPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Obtener solicitudes por usuario", description = "Retorna solicitudes de un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitudes obtenidas exitosamente")
    })
    public ResponseEntity<List<SolicitudMedicamento>> obtenerSolicitudesPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(solicitudService.obtenerSolicitudesPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/paginated")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Obtener solicitudes por usuario (paginado)", description = "Retorna solicitudes de un usuario con paginación")
    public ResponseEntity<Page<SolicitudMedicamento>> obtenerSolicitudesPorUsuarioPaginadas(
            @PathVariable Long usuarioId, Pageable pageable) {
        return ResponseEntity.ok(solicitudService.obtenerSolicitudesPorUsuarioPaginadas(usuarioId, pageable));
    }

    @GetMapping("/medicamento/{medicamentoId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Obtener solicitudes por medicamento", description = "Retorna solicitudes de un medicamento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitudes obtenidas exitosamente")
    })
    public ResponseEntity<List<SolicitudMedicamento>> obtenerSolicitudesPorMedicamento(@PathVariable Long medicamentoId) {
        return ResponseEntity.ok(solicitudService.obtenerSolicitudesPorMedicamento(medicamentoId));
    }

    @GetMapping("/medicamento/{medicamentoId}/paginated")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Obtener solicitudes por medicamento (paginado)", description = "Retorna solicitudes de un medicamento con paginación")
    public ResponseEntity<Page<SolicitudMedicamento>> obtenerSolicitudesPorMedicamentoPaginadas(
            @PathVariable Long medicamentoId, Pageable pageable) {
        return ResponseEntity.ok(solicitudService.obtenerSolicitudesPorMedicamentoPaginadas(medicamentoId, pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Crear nueva solicitud", description = "Crea una nueva solicitud de medicamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud creada exitosamente",
                    content = @Content(schema = @Schema(implementation = SolicitudMedicamento.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<SolicitudMedicamento> crearSolicitud(@RequestBody SolicitudMedicamento solicitud) {
        SolicitudMedicamento solicitudCreada = solicitudService.crearSolicitud(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudCreada);
    }

}
