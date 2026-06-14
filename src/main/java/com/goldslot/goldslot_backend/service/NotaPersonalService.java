package com.goldslot.goldslot_backend.service;

import com.goldslot.goldslot_backend.entity.NotaPersonal;
import com.goldslot.goldslot_backend.entity.Usuario;
import com.goldslot.goldslot_backend.repository.NotaPersonalRepository;
import com.goldslot.goldslot_backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NotaPersonalService {

    private final NotaPersonalRepository notaPersonalRepository;
    private final UsuarioRepository usuarioRepository;

    public NotaPersonalService(NotaPersonalRepository notaPersonalRepository, UsuarioRepository usuarioRepository) {
        this.notaPersonalRepository = notaPersonalRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public NotaPersonal crearOActualizar(NotaPersonal notaPersonal) {
        Usuario usuario = usuarioRepository.findById(notaPersonal.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        notaPersonal.setUsuario(usuario);
        return notaPersonalRepository.save(notaPersonal);
    }

    public Optional<NotaPersonal> obtenerPorId(Long id) {
        return notaPersonalRepository.findById(id);
    }

    public Optional<NotaPersonal> obtenerDelDia(Long usuarioId, LocalDate fecha) {
        return notaPersonalRepository.findByUsuarioIdAndFecha(usuarioId, fecha);
    }

    public List<NotaPersonal> obtenerPorUsuario(Long usuarioId) {
        return notaPersonalRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }

    public void eliminar(Long id) {
        notaPersonalRepository.deleteById(id);
    }
}