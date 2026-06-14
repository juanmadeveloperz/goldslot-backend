package com.goldslot.goldslot_backend.repository;

import com.goldslot.goldslot_backend.entity.NotaPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotaPersonalRepository extends JpaRepository<NotaPersonal, Long> {
    List<NotaPersonal> findByUsuarioId(Long usuarioId);
    Optional<NotaPersonal> findByUsuarioIdAndFecha(Long usuarioId, LocalDate fecha);
    List<NotaPersonal> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
}