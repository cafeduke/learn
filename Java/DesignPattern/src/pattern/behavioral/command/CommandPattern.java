package pattern.behavioral.command;

/**
 * Command pattern encapsulate requests as an object, there by, allowing to 
 * parameterize clients ( Receiver ) with different actions.
 */
public class CommandPattern
{
   public static void main(String[] args)
   {
      ReceiverA receiverA = new ReceiverA ();
      ReceiverB receiverB = new ReceiverB ();
      
      ConcreteCommandA commandA = new ConcreteCommandA(receiverA);
      ConcreteCommandB commandB = new ConcreteCommandB(receiverB);
      
      Invoker invoker = new Invoker (new Command [] {commandA, commandB});
      invoker.invoke();
   }
}

/* ---------- Invoker ---------- */

class Invoker
{
   private Command command[] = null;
   
   Invoker (Command[] command)
   {
      this.command = command;
   }
   
   public void invoke ()
   {
      for (Command currCommand : command)
         currCommand.execute();
   }
}

/* ---------- Command ---------- */

interface Command
{
   public void execute ();
}

class ConcreteCommandA implements Command
{
   private ReceiverA receiver = null;
   
   ConcreteCommandA (ReceiverA receiver)
   {
      this.receiver = receiver;
   }
   
   public void execute ()
   {
      receiver.actionA1();
      receiver.actionA2();
   }
}

class ConcreteCommandB implements Command
{
   private ReceiverB receiver = null;
   
   ConcreteCommandB (ReceiverB receiver)
   {
      this.receiver = receiver;
   }
   
   public void execute ()
   {
      receiver.actionB();
   }
}

/* ---------- Receiver ---------- */

class ReceiverA
{
   public void actionA1 ()
   {
      System.out.println ("ReceiverA performing actionA1");
   }
   
   public void actionA2 ()
   {
      System.out.println ("ReceiverA performing actionA2");
   }   
}

class ReceiverB
{
   public void actionB ()
   {
      System.out.println ("ReceiverB performing actionB");
   }
}



