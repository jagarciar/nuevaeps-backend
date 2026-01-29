package com.nuevaeps.api.service;

import com.nuevaeps.api.model.Medicamento;
import com.nuevaeps.api.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    public List<Medicamento> obtenerTodosMedicamentos() {
        return medicamentoRepository.findAll();
    }

    public Page<Medicamento> obtenerTodosMedicamentosPaginados(Pageable pageable) {
        return medicamentoRepository.findAll(pageable);
    }

    public Optional<Medicamento> obtenerMedicamentoPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    public Medicamento crearMedicamento(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    public Medicamento actualizarMedicamento(Long id, Medicamento medicamento) {
        return medicamentoRepository.findById(id).map(m -> {
            m.setNombre(medicamento.getNombre());
            return medicamentoRepository.save(m);
        }).orElse(null);
    }

    public void eliminarMedicamento(Long id) {
        medicamentoRepository.deleteById(id);
    }
}
