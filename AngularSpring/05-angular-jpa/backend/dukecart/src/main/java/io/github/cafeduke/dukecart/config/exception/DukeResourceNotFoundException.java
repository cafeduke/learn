package io.github.cafeduke.dukecart.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DukeResourceNotFoundException extends RuntimeException
{
   private static final long serialVersionUID = 1L;

   public DukeResourceNotFoundException(String message)
   {
      super(message);
   }
}

