package com.goldslot.goldslot_backend.service;

import com.goldslot.goldslot_backend.entity.Lesson;
import com.goldslot.goldslot_backend.entity.Usuario;
import com.goldslot.goldslot_backend.entity.Alumno;
import com.goldslot.goldslot_backend.repository.LessonRepository;
import com.goldslot.goldslot_backend.repository.UsuarioRepository;
import com.goldslot.goldslot_backend.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    public Lesson crearLesson(Lesson lesson) {
        Usuario usuario = usuarioRepository.findById(lesson.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Alumno alumno = alumnoRepository.findById(lesson.getAlumno().getId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        lesson.setUsuario(usuario);
        lesson.setAlumno(alumno);
        return lessonRepository.save(lesson);
    }

    public Optional<Lesson> obtenerPorId(Long id) {
        return lessonRepository.findById(id);
    }

    public List<Lesson> obtenerPorUsuario(Long usuarioId) {
        return lessonRepository.findByUsuarioId(usuarioId);
    }

    public List<Lesson> obtenerPorAlumno(Long alumnoId) {
        return lessonRepository.findByAlumnoId(alumnoId);
    }

    public List<Lesson> obtenerTodas() {
        return lessonRepository.findAll();
    }

    public Lesson actualizar(Long id, Lesson lessonDetails) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson no encontrada"));
        lesson.setFecha(lessonDetails.getFecha());
        lesson.setDescripcion(lessonDetails.getDescripcion());
        return lessonRepository.save(lesson);
    }

    public void eliminar(Long id) {
        lessonRepository.deleteById(id);
    }
}