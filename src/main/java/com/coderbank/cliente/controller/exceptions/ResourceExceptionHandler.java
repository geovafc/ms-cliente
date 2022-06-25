package com.coderbank.cliente.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

//Permite criar um componente global de tratamento de erros que pode ser usado por todos os controller
@ControllerAdvice
public class ResourceExceptionHandler {

//    Filtro que intercepta todas as exceções do tipo ObjectNotFoundException. Sempre que ocorrer uma
//    exceção do tipo ObjectNotFoundException, o retorno será redirecionado para esse método.

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ResponseException> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request) {
        var error = new ResponseException(HttpStatus.NOT_FOUND.value(), System.currentTimeMillis(), exception.getMessage());

        return ResponseEntity.
                status(HttpStatus.NOT_FOUND).
                body(error);
    }
}
