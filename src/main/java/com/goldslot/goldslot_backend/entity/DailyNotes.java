package com.goldslot.goldslot_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private com.goldslot.goldslot_backend.entity.Lesson lesson;

    @Column(nullable = false, length = 1000)
    private String nota;

    @Column(nullable = false)
    private Integer calificacion; // 1-10

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}