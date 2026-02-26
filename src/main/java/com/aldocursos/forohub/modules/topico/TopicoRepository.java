package com.aldocursos.forohub.modules.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
