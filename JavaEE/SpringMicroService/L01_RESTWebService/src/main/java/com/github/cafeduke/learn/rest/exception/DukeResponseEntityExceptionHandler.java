package com.github.cafeduke.learn.rest.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Methods shared across several controller classes. Eg: UserController, HelloController
//    - Exception handling falls in this category
//    - Here were common exception handling for all controllers.
@ControllerAdvice 
@RestController
public class DukeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
   /**
    * Response to be returned when a DukeResourceNotFoundException is raised by any controller in this application.
    * The response is constructed using the DukeExceptionResponse object.
    * 
    * @param e Exception raised
    * @param request Request received
    * @return A response entity constructed using DukeExceptionResponse.
    */
   @ExceptionHandler(DukeResourceNotFoundException.class)
   public final ResponseEntity<Object> dukeHandleNotFoundException(DukeResourceNotFoundException  e, WebRequest request) throws Exception
   {
      DukeExceptionResponse exceptionResponse = new DukeExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
      return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
   }   
   
   /**
    * Response to be returned when an exception is raised by any controller in this application.
    * The response is constructed using the DukeExceptionResponse object.
    * 
    * @param e Exception raised
    * @param request Request received
    * @return A response entity constructed using DukeExceptionResponse.
    */
   @ExceptionHandler(Exception.class)
   public final ResponseEntity<Object> dukeHandleAllException(Exception e, WebRequest request) throws Exception
   {
      DukeExceptionResponse exceptionResponse = new DukeExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
      return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
