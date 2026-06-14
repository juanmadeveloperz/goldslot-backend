package com.goldslot.goldslot_backend.controller;

import com.goldslot.goldslot_backend.dto.AlumnoDTO;
import com.goldslot.goldslot_backend.entity.Alumno;
import com.goldslot.goldslot_backend.entity.Usuario;
import com.goldslot.goldslot_backend.service.AlumnoService;
import com.goldslot.goldslot_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<AlumnoDTO> crear(@RequestBody AlumnoDTO alumnoDTO) {
        Usuario usuario = usuarioService.obtenerPorId(alumnoDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Alumno alumno = new Alumno();
        alumno.setUsuario(usuario);
        alumno.setNombre(alumnoDTO.getNombre());
        alumno.setTipoLicencia(alumnoDTO.getTipoLicencia());

        Alumno creado = alumnoService.crearAlumno(alumno);
        return ResponseEntity.ok(convertToDTO(creado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDTO> obtenerPorId(@PathVariable Long id) {
        return alumnoService.obtenerPorId(id)
                .map(a -> ResponseEntity.ok(convertToDTO(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> obtenerTodos() {
        List<AlumnoDTO> alumnos = alumnoService.obtenerTodos()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(alumnos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AlumnoDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<AlumnoDTO> alumnos = alumnoService.obtenerPorUsuario(usuarioId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(alumnos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoDTO> actualizar(@PathVariable Long id, @RequestBody AlumnoDTO alumnoDTO) {
        Alumno alumnoDetails = new Alumno();
        alumnoDetails.setNombre(alumnoDTO.getNombre());
        alumnoDetails.setTipoLicencia(alumnoDTO.getTipoLicencia());

        Alumno actualizado = alumnoService.actualizar(id, alumnoDetails);
        return ResponseEntity.ok(convertToDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alumnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private AlumnoDTO convertToDTO(Alumno alumno) {
        return new AlumnoDTO(alumno.getId(), alumno.getUsuario().getId(), alumno.getNombre(), alumno.getTipoLicencia());
    }
}