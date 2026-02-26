package com.aldocursos.forohub.modules.topico;

import com.aldocursos.forohub.modules.Curso;
import com.aldocursos.forohub.modules.Estado;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Estado status,
        String nombreAutor,
        Curso curso
) {
    public DatosListadoTopico(Topico topico){
        this(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso()
        );
    }
}
