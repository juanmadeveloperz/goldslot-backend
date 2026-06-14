package com.goldslot.goldslot_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoDTO {
    private Long id;
    private Long usuarioId;
    private String nombre;
    private String tipoLicencia;
}