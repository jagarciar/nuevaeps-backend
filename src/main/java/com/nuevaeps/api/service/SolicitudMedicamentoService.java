package com.nuevaeps.api.service;

import com.nuevaeps.api.model.SolicitudMedicamento;
import com.nuevaeps.api.repository.SolicitudMedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudMedicamentoService {

    @Autowired
    private SolicitudMedicamentoRepository solicitudRepository;

    public List<SolicitudMedicamento> obtenerTodasLasSolicitudes() {
        return solicitudRepository.findAll();
    }

    public Page<SolicitudMedicamento> obtenerTodasLasSolicitudesPaginadas(Pageable pageable) {
        return solicitudRepository.findAll(pageable);
    }

    public Optional<SolicitudMedicamento> obtenerSolicitudPorId(Long id) {
        return solicitudRepository.findById(id);
    }

    public List<SolicitudMedicamento> obtenerSolicitudesPorUsuario(Long usuarioId) {
        return solicitudRepository.findByUsuarioId(usuarioId);
    }

    public Page<SolicitudMedicamento> obtenerSolicitudesPorUsuarioPaginadas(Long usuarioId, Pageable pageable) {
        return solicitudRepository.findByUsuarioId(usuarioId, pageable);
    }

    public List<SolicitudMedicamento> obtenerSolicitudesPorMedicamento(Long medicamentoId) {
        return solicitudRepository.findByMedicamentoId(medicamentoId);
    }

    public Page<SolicitudMedicamento> obtenerSolicitudesPorMedicamentoPaginadas(Long medicamentoId, Pageable pageable) {
        return solicitudRepository.findByMedicamentoId(medicamentoId, pageable);
    }

    public Optional<SolicitudMedicamento> obtenerSolicitudPorNumeroOrden(String numeroOrden) {
        return solicitudRepository.findByNumeroOrden(numeroOrden);
    }

    public SolicitudMedicamento crearSolicitud(SolicitudMedicamento solicitud) {
        return solicitudRepository.save(solicitud);
    }

}
