package com.aldocursos.forohub.modules.topico;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping
    @Transactional
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistroTopico datos){
        service.registrar(datos);
        return ResponseEntity.ok().body("Registro exitoso");
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 10, sort = {"fechaCreacion"}, direction = Sort.Direction.ASC) Pageable pageable){
        var page = service.listarTopicos(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopico> detallesTopico(@PathVariable Long id){
        return ResponseEntity.ok(service.mostrarTopicos(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosListadoTopico> actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos){
        var topicoActualizado = service.actualizarDatosDeTopico(id, datos);
        return ResponseEntity.ok(topicoActualizado);
    }



}
