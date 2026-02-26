package com.aldocursos.forohub.shared.exceptions;

import com.aldocursos.forohub.modules.ValidacionException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GestorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity gestionarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity TratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream().map(DatosErrorValidacion::new).toList());
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity TratarErrorDeValidacion(ValidacionException e){
        var error = e.getMessage();
        System.out.println(error);
       return ResponseEntity.badRequest().body(e.getMessage());
    }

    public record DatosErrorValidacion(
            String campo,
            String mensaje
    ){
        public DatosErrorValidacion(FieldError fieldError){
            this(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }
    }
}
