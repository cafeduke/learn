package pattern.behavioral.observer;

/**
 * The Observer Pattern
 * --------------------
 * The Observer pattern defines a one-to-many dependency between objects, so that, when one object
 * changes state ( Subject ), all of its dependents are notified and updated automatically.
 */
public class ObserverPattern
{
  public static void main(String arg[])
  {
    ConcreteSubjectB subject1 = new ConcreteSubjectB();
    subject1.addListenerB(new HandlerAB());
    subject1.notifyListener();

    ConcreteSubjectABC subject2 = new ConcreteSubjectABC();
    subject2.addListenerB(new HandlerBC());
    subject2.addListenerC(new HandlerBC());
    subject2.notifyListener();
  }
}

/* -------------------  Subject --------------------- */

interface Subject {
  public void notifyListener();
}

class ConcreteSubjectB implements Subject
{
  ListenerB listenerB = null;

  public ConcreteSubjectB()
  {
  }

  public void addListenerB(ListenerB listenerB)
  {
    this.listenerB = listenerB;
  }

  @Override
  public void notifyListener()
  {
    if (listenerB != null)
    {
      listenerB.eventB1();
      listenerB.eventB2();
      listenerB.eventB3();
    }
  }
}

class ConcreteSubjectABC implements Subject
{
  ListenerA listenerA = null;

  ListenerB listenerB = null;

  ListenerC listenerC = null;

  public ConcreteSubjectABC()
  {
  }

  public void addListenerA(ListenerA listenerA)
  {
    this.listenerA = listenerA;
  }

  public void addListenerB(ListenerB listenerB)
  {
    this.listenerB = listenerB;
  }

  public void addListenerC(ListenerC listenerC)
  {
    this.listenerC = listenerC;
  }

  @Override
  public void notifyListener()
  {
    if (listenerA != null)
    {
      listenerA.eventA1();
      listenerA.eventA2();
    }

    if (listenerB != null)
    {
      listenerB.eventB1();
      listenerB.eventB2();
      listenerB.eventB3();
    }

    if (listenerC != null)
    {
      listenerC.eventC();
    }
  }
}

/* ------------------- Listener --------------------- */

interface Listener {
}

interface ListenerA extends Listener {
  public void eventA1();
  public void eventA2();
}

interface ListenerB extends Listener {
  public void eventB1();
  public void eventB2();
  public void eventB3();
}

interface ListenerC {
  public void eventC();
}

/* ------------- Concrete Listener ------------------ */

class HandlerAB implements ListenerA, ListenerB
{

  @Override
  public void eventA1()
  {
    System.out.println("HandlerAB handling eventA1");
  }

  @Override
  public void eventA2()
  {
    System.out.println("HandlerAB handling eventA2");
  }

  @Override
  public void eventB1()
  {
    System.out.println("HandlerAB handling eventB1");
  }

  @Override
  public void eventB2()
  {
    System.out.println("HandlerAB handling eventB2");
  }

  @Override
  public void eventB3()
  {
    System.out.println("HandlerAB handling eventB3");
  }
}

class HandlerBC implements ListenerB, ListenerC
{
  @Override
  public void eventB1()
  {
    System.out.println("HandlerBC handling eventB1");
  }

  @Override
  public void eventB2()
  {
    System.out.println("HandlerBC handling eventB2");
  }

  @Override
  public void eventB3()
  {
    System.out.println("HandlerBC handling eventB3");
  }

  @Override
  public void eventC()
  {
    System.out.println("HandlerBC handling eventC");
  }
}
