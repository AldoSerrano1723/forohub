package com.aldocursos.forohub.modules.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacion(
        @NotBlank String email,
        @NotBlank String password
) {
}
