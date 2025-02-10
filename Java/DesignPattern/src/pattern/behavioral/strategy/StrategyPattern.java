package pattern.behavioral.strategy;

/**
 * Strategy Pattern
 * ----------------
 * The Strategy pattern defines a family of algorithms, encapsulates each one,
 * make them interchangeable. 
 * 
 * Strategy allows algorithms to vary from the clients that use them.
 */
public class StrategyPattern
{
   public static void main (String arg[])
   {
      
      System.out.println ("Current algorithm");
      System.out.println ("---------------------------------");
      Client client = new Client (new ImplA1(), new ImplB2());
      client.doTask();
      
      System.out.println();
      System.out.println ("Dyanmically interchange algorithm");
      System.out.println ("---------------------------------");
      
      client.setSpecA(new ImplA2());
      client.doTask();
   }
}

class Client
{
   SpecA specA = null;
   
   SpecB specB = null;
   
   public Client ()
   {
      
   }
   
   public Client (SpecA specA, SpecB specB)
   {
      this.specA = specA;
      this.specB = specB;
   }
   
   public void doTask ()
   {
      specA.algorithm();
      specB.algorithm();
   }
   
   public void setSpecA (SpecA specA)
   {
      this.specA = specA;
   }
   
   public void setSpecB (SpecB specB)
   {
      this.specB = specB;
   }
}

/* --- A family Of Algorithms --- */

interface SpecA
{
   public void algorithm ();
}

class ImplA1 implements SpecA
{
   @Override
   public void algorithm()
   {
      System.out.println("Implementation-A1 of Spec-A");
   }
}

class ImplA2 implements SpecA
{
   @Override
   public void algorithm()
   {
      System.out.println("Implementation-A2 of Spec-A");
   }
}


/* --- B family Of Algorithms --- */

interface SpecB
{
   public void algorithm ();
}

class ImplB1 implements SpecB
{
   @Override
   public void algorithm()
   {
      System.out.println("Implementation-B1 of Spec-B");
   }
}

class ImplB2 implements SpecB
{
   @Override
   public void algorithm()
   {
      System.out.println("Implementation-B2 of Spec-B");
   }
}
















