package com.goldslot.goldslot_backend.controller;

import com.goldslot.goldslot_backend.dto.DailyNotesDTO;
import com.goldslot.goldslot_backend.entity.DailyNotes;
import com.goldslot.goldslot_backend.entity.Lesson;
import com.goldslot.goldslot_backend.service.DailyNotesService;
import com.goldslot.goldslot_backend.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/daily-notes")
public class DailyNotesController {

    @Autowired
    private DailyNotesService dailyNotesService;

    @Autowired
    private LessonService lessonService;

    @PostMapping
    public ResponseEntity<DailyNotesDTO> crear(@RequestBody DailyNotesDTO dailyNotesDTO) {
        Lesson lesson = lessonService.obtenerPorId(dailyNotesDTO.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson no encontrada"));

        DailyNotes dailyNotes = new DailyNotes();
        dailyNotes.setLesson(lesson);
        dailyNotes.setNota(dailyNotesDTO.getNota());
        dailyNotes.setCalificacion(dailyNotesDTO.getCalificacion());

        DailyNotes creada = dailyNotesService.crearNota(dailyNotes);
        return ResponseEntity.ok(convertToDTO(creada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DailyNotesDTO> obtenerPorId(@PathVariable Long id) {
        return dailyNotesService.obtenerPorId(id)
                .map(d -> ResponseEntity.ok(convertToDTO(d)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DailyNotesDTO>> obtenerTodas() {
        List<DailyNotesDTO> notas = dailyNotesService.obtenerTodas()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notas);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<DailyNotesDTO>> obtenerPorLesson(@PathVariable Long lessonId) {
        List<DailyNotesDTO> notas = dailyNotesService.obtenerPorLesson(lessonId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyNotesDTO> actualizar(@PathVariable Long id, @RequestBody DailyNotesDTO dailyNotesDTO) {
        DailyNotes notasDetails = new DailyNotes();
        notasDetails.setNota(dailyNotesDTO.getNota());
        notasDetails.setCalificacion(dailyNotesDTO.getCalificacion());

        DailyNotes actualizada = dailyNotesService.actualizar(id, notasDetails);
        return ResponseEntity.ok(convertToDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        dailyNotesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private DailyNotesDTO convertToDTO(DailyNotes dailyNotes) {
        return new DailyNotesDTO(dailyNotes.getId(), dailyNotes.getLesson().getId(),
                dailyNotes.getNota(), dailyNotes.getCalificacion());
    }
}