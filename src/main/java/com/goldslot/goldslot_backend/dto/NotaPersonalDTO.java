package com.goldslot.goldslot_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaPersonalDTO {
    private Long id;
    private Long usuarioId;
    private LocalDate fecha;
    private String contenido;
}