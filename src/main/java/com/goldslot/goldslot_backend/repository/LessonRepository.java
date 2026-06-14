package com.goldslot.goldslot_backend.repository;

import com.goldslot.goldslot_backend.entity.Lesson;
import com.goldslot.goldslot_backend.entity.Usuario;
import com.goldslot.goldslot_backend.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByUsuario(Usuario usuario);
    List<Lesson> findByAlumno(Alumno alumno);
    List<Lesson> findByUsuarioId(Long usuarioId);
    List<Lesson> findByAlumnoId(Long alumnoId);
}