package com.goldslot.goldslot_backend.controller;

import com.goldslot.goldslot_backend.dto.NotaPersonalDTO;
import com.goldslot.goldslot_backend.entity.NotaPersonal;
import com.goldslot.goldslot_backend.entity.Usuario;
import com.goldslot.goldslot_backend.service.NotaPersonalService;
import com.goldslot.goldslot_backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/notas-personales")
public class NotaPersonalController {

    private final NotaPersonalService notaPersonalService;
    private final UsuarioService usuarioService;

    public NotaPersonalController(NotaPersonalService notaPersonalService, UsuarioService usuarioService) {
        this.notaPersonalService = notaPersonalService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<NotaPersonalDTO> crear(@RequestBody NotaPersonalDTO notaDTO) {
        Usuario usuario = usuarioService.obtenerPorId(notaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        NotaPersonal nota = new NotaPersonal();
        nota.setUsuario(usuario);
        nota.setFecha(notaDTO.getFecha());
        nota.setContenido(notaDTO.getContenido());

        NotaPersonal creada = notaPersonalService.crearOActualizar(nota);
        return ResponseEntity.ok(convertToDTO(creada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaPersonalDTO> obtenerPorId(@PathVariable Long id) {
        return notaPersonalService.obtenerPorId(id)
                .map(n -> ResponseEntity.ok(convertToDTO(n)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}/fecha/{fecha}")
    public ResponseEntity<NotaPersonalDTO> obtenerDelDia(@PathVariable Long usuarioId, @PathVariable String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return notaPersonalService.obtenerDelDia(usuarioId, localDate)
                .map(n -> ResponseEntity.ok(convertToDTO(n)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotaPersonalDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<NotaPersonalDTO> notas = notaPersonalService.obtenerPorUsuario(usuarioId)
                .stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(notas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaPersonalDTO> actualizar(@PathVariable Long id, @RequestBody NotaPersonalDTO notaDTO) {
        NotaPersonal notaExistente = notaPersonalService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));

        // Recargar el usuario explícitamente
        Usuario usuario = usuarioService.obtenerPorId(notaExistente.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        notaExistente.setUsuario(usuario);
        notaExistente.setContenido(notaDTO.getContenido());
        notaExistente.setFecha(notaDTO.getFecha());

        NotaPersonal actualizada = notaPersonalService.actualizar(notaExistente);
        return ResponseEntity.ok(convertToDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notaPersonalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private NotaPersonalDTO convertToDTO(NotaPersonal nota) {
        return new NotaPersonalDTO(nota.getId(), nota.getUsuario().getId(), nota.getFecha(), nota.getContenido());
    }
}