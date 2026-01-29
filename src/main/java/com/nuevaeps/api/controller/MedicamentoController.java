package com.nuevaeps.api.controller;

import com.nuevaeps.api.model.Medicamento;
import com.nuevaeps.api.service.MedicamentoService;
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
@RequestMapping("/api/v1/medicamentos")
@Tag(name = "Medicamentos", description = "Endpoints para gestionar medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Obtener todos los medicamentos", description = "Retorna una lista de todos los medicamentos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de medicamentos obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Medicamento.class)))
    })
    public ResponseEntity<List<Medicamento>> obtenerTodosMedicamentos() {
        return ResponseEntity.ok(medicamentoService.obtenerTodosMedicamentos());
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Obtener medicamentos paginados", description = "Retorna medicamentos con paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de medicamentos obtenida exitosamente")
    })
    public ResponseEntity<Page<Medicamento>> obtenerTodosMedicamentosPaginados(Pageable pageable) {
        return ResponseEntity.ok(medicamentoService.obtenerTodosMedicamentosPaginados(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Obtener medicamento por ID", description = "Retorna un medicamento específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado")
    })
    public ResponseEntity<Medicamento> obtenerMedicamentoPorId(@PathVariable Long id) {
        return medicamentoService.obtenerMedicamentoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
