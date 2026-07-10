package io.github.cafeduke.dukecart.config.exception;

import java.util.function.Supplier;

public class DukeExceptionUtil
{
  public static Supplier<RuntimeException> notFound (int id)
  {
    return () -> new DukeResourceNotFoundException(String.format("Resource with ID=%d not found.", id));
  }
  
  public static Supplier<RuntimeException> notFound (String mesg)
  {
    return () -> new DukeResourceNotFoundException(mesg);
  }  
}
