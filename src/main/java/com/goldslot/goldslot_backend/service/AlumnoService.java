package com.goldslot.goldslot_backend.service;

import com.goldslot.goldslot_backend.entity.Alumno;
import com.goldslot.goldslot_backend.entity.Lesson;
import com.goldslot.goldslot_backend.entity.Usuario;
import com.goldslot.goldslot_backend.repository.AlumnoRepository;
import com.goldslot.goldslot_backend.repository.LessonRepository;
import com.goldslot.goldslot_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DailyNotesService dailyNotesService;

    @Autowired
    private LessonRepository lessonRepository;

    public Alumno crearAlumno(Alumno alumno) {
        Usuario usuario = usuarioRepository.findById(alumno.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        alumno.setUsuario(usuario);
        return alumnoRepository.save(alumno);
    }

    public Optional<Alumno> obtenerPorId(Long id) {
        return alumnoRepository.findById(id);
    }

    public List<Alumno> obtenerPorUsuario(Long usuarioId) {
        return alumnoRepository.findByUsuarioId(usuarioId);
    }

    public List<Alumno> obtenerTodos() {
        return alumnoRepository.findAll();
    }

    public Alumno actualizar(Long id, Alumno alumnoDetails) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        alumno.setNombre(alumnoDetails.getNombre());
        alumno.setTipoLicencia(alumnoDetails.getTipoLicencia());
        return alumnoRepository.save(alumno);
    }

    public void eliminar(Long id) {
        // Primero elimina las clases del alumno
        List<Lesson> lessons = lessonRepository.findByAlumnoId(id);
        for (Lesson lesson : lessons) {
            dailyNotesService.eliminarPorLesson(lesson.getId());
        }
        lessonRepository.deleteAll(lessons);

        // Después elimina el alumno
        alumnoRepository.deleteById(id);
    }
}