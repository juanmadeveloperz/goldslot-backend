package com.goldslot.goldslot_backend.service;

import com.goldslot.goldslot_backend.entity.DailyNotes;
import com.goldslot.goldslot_backend.entity.Lesson;
import com.goldslot.goldslot_backend.repository.DailyNotesRepository;
import com.goldslot.goldslot_backend.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DailyNotesService {

    @Autowired
    private DailyNotesRepository dailyNotesRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public DailyNotes crearNota(DailyNotes dailyNotes) {
        Lesson lesson = lessonRepository.findById(dailyNotes.getLesson().getId())
                .orElseThrow(() -> new RuntimeException("Lesson no encontrada"));
        dailyNotes.setLesson(lesson);
        return dailyNotesRepository.save(dailyNotes);
    }

    public Optional<DailyNotes> obtenerPorId(Long id) {
        return dailyNotesRepository.findById(id);
    }

    public List<DailyNotes> obtenerPorLesson(Long lessonId) {
        return dailyNotesRepository.findByLessonId(lessonId);
    }

    public List<DailyNotes> obtenerTodas() {
        return dailyNotesRepository.findAll();
    }

    public DailyNotes actualizar(Long id, DailyNotes notasDetails) {
        DailyNotes dailyNotes = dailyNotesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));
        dailyNotes.setNota(notasDetails.getNota());
        dailyNotes.setCalificacion(notasDetails.getCalificacion());
        return dailyNotesRepository.save(dailyNotes);
    }

    public void eliminar(Long id) {
        dailyNotesRepository.deleteById(id);
    }
}