/**
 * The chain pattern links handlers to perform an operation.
 * The handlers can share a common resource.
 */
package pattern.behavioral.chain;

import java.util.*;

abstract class Handler
{
   private Handler next;
   
   protected static List<String> resource = new ArrayList<String> ();
   
   public Handler setNext (Handler handler)
   {
      this.next = handler;
      return handler;
   }
   
   public void next ()
   {
      if (next == null)
      {
         System.out.println("End reached. There is no next!");
         return;
      }
      
      if (next != null)
         next.doHandle();
   }
   
   public static Handler doChain (Handler[] handler)
   {
      if (handler.length < 0)
         return null;
      
      for (int i = 0; i + 1 < handler.length; ++i)
         handler[i].next = handler[i+1];
      
      return handler[0];
   }
   
   public abstract void doHandle ();
}

class HandlerA extends Handler
{
   @Override
   public void doHandle()
   {
      resource.add("Apple");
      System.out.println("[Handler-A] Add Apple = " + resource);      
      next();
      resource.add("Air");
      System.out.println("[Handler-A] Add Air   = " + resource);      
   }
}

class HandlerB extends Handler
{
   @Override
   public void doHandle()
   {
      resource.add("Ball");
      System.out.println("[Handler-B] Add Ball  = " + resource);      
      next();
      resource.add("Bat");
      System.out.println("[Handler-B] Add Bat   = " + resource);      
   }
}

class HandlerC extends Handler
{
   @Override
   public void doHandle()
   {
      resource.add("Cat");
      System.out.println("[Handler-C] Add Cat   = " + resource);      
      next();
      resource.add("Camel");
      System.out.println("[Handler-C] Add Camel = " + resource);
   }
}

/**
 *
 * @author Raghunandan.Seshadri
 * @owner  Raghunandan.Seshadri
 */
public class Chain
{
   public static void main (String arg[])
   {
      Handler arrayHandler[]= new Handler[] {new HandlerA(), new HandlerB (), new HandlerC() };
      Handler firstHandler = Handler.doChain(arrayHandler);
      
      firstHandler.doHandle();
   }
}


















