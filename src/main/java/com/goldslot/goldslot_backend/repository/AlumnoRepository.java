package com.goldslot.goldslot_backend.repository;

import com.goldslot.goldslot_backend.entity.Alumno;
import com.goldslot.goldslot_backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    List<Alumno> findByUsuario(Usuario usuario);
    List<Alumno> findByUsuarioId(Long usuarioId);
}