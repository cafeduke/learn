package pattern.behavioral.state;

/**
 * State Pattern
 * -------------
 * Allows to alter the behavior when the internal state changes.
 *
 * State Diagram
 * -------------
 * Q = { Q1, Q2, F1 }
 * I = {  A,  B }
 *
 *
 * Regular Expression a*bb*
 *
 *       A     B
 * ---+------------
 * Q1 |  Q1    Q2
 * Q2 |  -     Q2
 */
public class StatePattern
{
  public static void main(String arg[])
  {
    StateMachine state = new StateMachine();
    state.processInput("aaabb");
  }
}

class StateMachine
{
  private State currState = null;

  private static ConcreteStateQ1 concreteStateQ1 = null;

  private static ConcreteStateQ2 concreteStateQ2 = null;

  public StateMachine()
  {
    if (concreteStateQ1 == null)
    {
      concreteStateQ1 = new ConcreteStateQ1();
      concreteStateQ2 = new ConcreteStateQ2();
    }
    currState = concreteStateQ1;
  }

  public void processInput(String input)
  {
    char ch[] = input.toCharArray();
    for (char currCh : ch)
    {
      if (currCh == 'a')
        currState.inputA();
      else
        currState.inputB();
    }
  }

  protected interface State {
    public void inputA();
    public void inputB();
  }

  protected abstract class AbstractState implements State
  {
    public void inputA()
    {
      throw new IllegalStateException();
    }

    public void inputB()
    {
      throw new IllegalStateException();
    }
  }

  protected class ConcreteStateQ1 extends AbstractState
  {
    @Override
    public void inputA()
    {
      currState = concreteStateQ1;
      System.out.println("(Q1, a) --> Q1");
    }

    @Override
    public void inputB()
    {
      currState = concreteStateQ2;
      System.out.println("(Q1, b) --> Q2");
    }
  }

  protected class ConcreteStateQ2 extends AbstractState
  {
    @Override
    public void inputB()
    {
      currState = concreteStateQ2;
      System.out.println("(Q2, b) --> Q2");
    }
  }
}
