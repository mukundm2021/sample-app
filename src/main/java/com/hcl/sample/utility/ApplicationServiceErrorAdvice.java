package com.hcl.sample.utility;


//import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ControllerAdvice
public class ApplicationServiceErrorAdvice {

   /*@ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }
    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(ProductNotFoundException e) {
        return error(NOT_FOUND, e);
    }
    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<String> handleDogsServiceException(ServiceException e){
        return error(INTERNAL_SERVER_ERROR, e);
    }
    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        log.error("Exception : ", e);
        return ResponseEntity.status(status).body(e.getMessage());
    }

    */

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public void handle(ResourceNotFoundException e) {}
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({AppServiceException.class, SQLException.class, NullPointerException.class})
    public void handle() {}
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AppServiceValidationException.class})
    public void handle(AppServiceValidationException e) {}


}
