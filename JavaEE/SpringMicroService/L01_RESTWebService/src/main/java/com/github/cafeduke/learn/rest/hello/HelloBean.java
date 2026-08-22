package com.github.cafeduke.learn.rest.hello;

public class HelloBean
{
   private String message;
   
   public HelloBean(String message)
   {
      this.message = message;
   }

   /* If getter is not present the automatic conversion will not take place. */
   
   public String getMessage()
   {
      return message;
   }
   
   public void setMessage(String message)
   {
      this.message = message;
   }  

   @Override
   public String toString()
   {
      return String.format("HelloBean [message=%s]", message);
   }   

}
