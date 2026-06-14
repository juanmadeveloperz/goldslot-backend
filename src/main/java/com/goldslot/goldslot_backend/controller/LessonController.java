package com.goldslot.goldslot_backend.controller;

import com.goldslot.goldslot_backend.dto.LessonDTO;
import com.goldslot.goldslot_backend.entity.Lesson;
import com.goldslot.goldslot_backend.entity.Usuario;
import com.goldslot.goldslot_backend.entity.Alumno;
import com.goldslot.goldslot_backend.service.LessonService;
import com.goldslot.goldslot_backend.service.UsuarioService;
import com.goldslot.goldslot_backend.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AlumnoService alumnoService;

    @PostMapping
    public ResponseEntity<LessonDTO> crear(@RequestBody LessonDTO lessonDTO) {
        Usuario usuario = usuarioService.obtenerPorId(lessonDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Alumno alumno = alumnoService.obtenerPorId(lessonDTO.getAlumnoId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Lesson lesson = new Lesson();
        lesson.setUsuario(usuario);
        lesson.setAlumno(alumno);
        lesson.setFecha(lessonDTO.getFecha());
        lesson.setDescripcion(lessonDTO.getDescripcion());

        Lesson creada = lessonService.crearLesson(lesson);
        return ResponseEntity.ok(convertToDTO(creada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> obtenerPorId(@PathVariable Long id) {
        return lessonService.obtenerPorId(id)
                .map(l -> ResponseEntity.ok(convertToDTO(l)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<LessonDTO>> obtenerTodas() {
        List<LessonDTO> lessons = lessonService.obtenerTodas()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<LessonDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<LessonDTO> lessons = lessonService.obtenerPorUsuario(usuarioId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<LessonDTO>> obtenerPorAlumno(@PathVariable Long alumnoId) {
        List<LessonDTO> lessons = lessonService.obtenerPorAlumno(alumnoId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lessons);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> actualizar(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {
        Lesson lessonDetails = new Lesson();
        lessonDetails.setFecha(lessonDTO.getFecha());
        lessonDetails.setDescripcion(lessonDTO.getDescripcion());

        Lesson actualizada = lessonService.actualizar(id, lessonDetails);
        return ResponseEntity.ok(convertToDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        lessonService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private LessonDTO convertToDTO(Lesson lesson) {
        return new LessonDTO(lesson.getId(), lesson.getUsuario().getId(), lesson.getAlumno().getId(),
                lesson.getFecha(), lesson.getDescripcion());
    }
}