package com.github.cafeduke.learn.rest.exception;

import java.util.Date;

/**
 * This class is a bean that provides custom response, having custom fields, to an exception. 
 * 
 * The custom exception format should ideally be a standard across the organization.
 *    - The fields used in the class that form the structure is important
 *    - Irrespective of the implementation (Java, C#, Python), in case of exception, the response should standard (organization level) fields.  
 */
public class DukeExceptionResponse
{
   private Date timestamp;
   
   private String message;
   
   private String detail;

   public DukeExceptionResponse(Date timestamp, String message, String detail)
   {
      super();
      this.timestamp = timestamp;
      this.message = message;
      this.detail = detail;
   }

   public Date getTimestamp()
   {
      return timestamp;
   }

   public String getMessage()
   {
      return message;
   }

   public String getDetail()
   {
      return detail;
   }   
   
}
