package com.aldocursos.forohub.modules.topico;

import com.aldocursos.forohub.modules.Curso;
import com.aldocursos.forohub.modules.Estado;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull Estado status,
        @NotNull Long idAutor,
        @NotNull Curso curso
) {
}
