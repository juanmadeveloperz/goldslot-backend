package com.goldslot.goldslot_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notas_personales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private com.goldslot.goldslot_backend.entity.Usuario usuario;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, length = 2000)
    private String contenido;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}