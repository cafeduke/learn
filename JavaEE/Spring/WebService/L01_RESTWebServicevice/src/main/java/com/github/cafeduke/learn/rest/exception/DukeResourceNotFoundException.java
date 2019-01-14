package com.github.cafeduke.learn.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DukeResourceNotFoundException extends RuntimeException
{
   public DukeResourceNotFoundException(String message)
   {
      super(message);
   }
}
