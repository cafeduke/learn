package pattern.structural.decorator;

/**
 * The Decorator Pattern attaches responsibility to objects dynamically.
 * 
 *    - Component class defines a responsibility ( defines abstract methods )
 *    
 *    - Concrete components provide implementation for the Component    
 *   
 *    - Decorator Requirement #1: A Decorator IS-A Component ( Just like a container is a component ).
 *      Apart from providing custom implementation for Component methods ( Which any ConcreteComponent
 *      does), a Decorator must be capable of utilizing the implementation of ANY Component it decorates.
 *      
 *    - Decorator Requirement #2: Decorator implementation = Decorator custom implementation + ANY given Component's implementation.      
 *      Since Decorator must be capable of utilizing ANY Component's implementation and since Decorator itself IS-A
 *      Component (from requirement #1), a Decorator is capable of utilizing a Decorator as well.     
 *      
 *    - An abstract Decorator is defined which extends Component. This satisfies requirement #1.  
 * 
 *    - Any ConcreteDecorator extends Decorator. A ConcreteDecorator accepts ANY Component in its constructor.
 *      All overridden implementations in ConcreteDecorator add custom code + utilize the component's (Accepted as constructor) 
 *      implementation. This satisfies requirement #2.
 *    
 */
public class DecoratorPattern
{
	public static void main (String arg[]) 
	{
		Decorator decorator = null;
		
		decorator = new DecoratorB (new DecoratorA(new ConcreteComponentA()));
		System.out.println(decorator.method());
		
		decorator = new DecoratorA (new DecoratorB(new ConcreteComponentA()));
		System.out.println(decorator.method());
		
	}
}

/* --------------------------------  Component ----------------------------- */

/**
 * An abstract class defines the methods.
 */
abstract class Component 
{
	public abstract String method ();
}

/* ----------------------------- Concrete Components ----------------------- */

/**
 * 
 */
class ConcreteComponentA extends Component
{
	public String method() 
	{
		return "[Concrete-A]";
	}
}

/**
 * 
 */
class ConcreteComponentB extends Component
{
	public String method() 
	{
		return "[Concrete-B]";
	}
}

/* ----------------------------- Abstract Decorator ----------------------- */

abstract class Decorator extends Component
{
	
}


/* --------------------------------  Decorator ----------------------------- */
class DecoratorA extends Decorator
{
	Component component;
	
	DecoratorA (Component component)
	{
		this.component = component;
	}
	
	public String method() 
	{
		return "[Decorator-A" + component.method() + "]";
	}
}

class DecoratorB extends Decorator
{
	Component component;
	
	DecoratorB (Component component)
	{
		this.component = component;
	}
	
	public String method() 
	{
		return "[Decorator-B" + component.method() + "]";
	}
}















