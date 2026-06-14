package com.goldslot.goldslot_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private Long id;
    private Long usuarioId;
    private Long alumnoId;
    private LocalDateTime fecha;
    private String descripcion;
}