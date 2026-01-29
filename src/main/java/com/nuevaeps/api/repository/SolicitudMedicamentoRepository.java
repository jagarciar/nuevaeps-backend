package com.nuevaeps.api.repository;

import com.nuevaeps.api.model.SolicitudMedicamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SolicitudMedicamentoRepository extends JpaRepository<SolicitudMedicamento, Long> {
    List<SolicitudMedicamento> findByUsuarioId(Long usuarioId);
    Page<SolicitudMedicamento> findByUsuarioId(Long usuarioId, Pageable pageable);
    List<SolicitudMedicamento> findByMedicamentoId(Long medicamentoId);
    Page<SolicitudMedicamento> findByMedicamentoId(Long medicamentoId, Pageable pageable);
    Optional<SolicitudMedicamento> findByNumeroOrden(String numeroOrden);
}
