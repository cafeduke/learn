package pattern.structural.adapter;

public class AdapterPattern
{
   public static void main(String[] args)
   {
      Target target = new Adapter (new Adaptee ());
      target.methodA();
      target.methodB();
   }
}

interface Target
{
   public void methodA ();
   public void methodB ();
}

class Adapter implements Target
{
   Native nativeObj = null;
   
   Adapter (Native nativeObj)
   {
      this.nativeObj = nativeObj;
   }

   @Override
   public void methodA()
   {
      nativeObj.methodX();
   }

   @Override
   public void methodB()
   {
      nativeObj.methodY();
   }
   
}

/* ---------- Adaptee ---------- */

interface Native
{
   public void methodX ();
   public void methodY ();
}

class Adaptee implements Native
{

   @Override
   public void methodX()
   {
      System.out.println ("Native X");
   }

   @Override
   public void methodY()
   {
      System.out.println ("Native Y");
   }
}
