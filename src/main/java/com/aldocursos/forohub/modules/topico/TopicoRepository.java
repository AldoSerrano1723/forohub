package com.aldocursos.forohub.modules.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findAll(Pageable pageable);

    Boolean existsByTituloAndMensaje(String titulo, String mensaje);
}
