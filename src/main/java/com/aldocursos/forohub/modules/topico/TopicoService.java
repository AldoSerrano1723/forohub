package com.aldocursos.forohub.modules.topico;

import com.aldocursos.forohub.modules.ValidacionException;
import com.aldocursos.forohub.modules.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void registrar(DatosRegistroTopico datos){
        var usuario = usuarioRepository.findById(datos.idAutor());

        if (usuario.isEmpty()){
            throw new ValidacionException("Id del usuario no existe");
        }

        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionException("Topico con ese titulo y mensaje ya existe");
        }


        Topico topico = new Topico(null, datos.titulo(), datos.mensaje(), LocalDateTime.now(), datos.status(), usuario.get(), datos.curso());
        topicoRepository.save(topico);
    }

    public Page<DatosListadoTopico> listarTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable).map(DatosListadoTopico::new);
    }

    public DatosListadoTopico mostrarTopicos(Long id) {
        var topico = topicoRepository.findById(id);

        if (topico.isEmpty()) {
            throw new EntityNotFoundException("Topico no existe con ese ID");
        }

        return new DatosListadoTopico(topico.get());

    }

    public DatosListadoTopico actualizarDatosDeTopico(Long id, DatosActualizacionTopico datos) {

        var topicoABuscar = topicoRepository.findById(id);

        if (topicoABuscar.isEmpty()){
            throw new EntityNotFoundException("Topico no existe con ID");
        }

        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())){
            throw new ValidacionException("Topico con ese titulo y mensaje ya existe");
        }

        var topico =  topicoABuscar.get();
        topico.actualizarInformacion(datos);
        return new DatosListadoTopico(topico);
    }

    public void eliminar(Long id) {
        var topicoABuscar = topicoRepository.findById(id);

        if (topicoABuscar.isEmpty()){
            throw new EntityNotFoundException("Topico no existe con ID");
        }

        topicoRepository.deleteById(id);
    }
}
