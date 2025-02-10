package pattern.creational.factory;
/**
 * <pre>
 * Factory Method defers instantiation to sub classes. Factory creates a family of 
 * related (mostly composition) objects without specifying the concrete classes.
 * 
 * - A factory is made up of several types of Products (Eg: ProductA, ProductB)
 * - Each type of Product has different variations (Eg: ProductA has ProductA1, ProductA2)
 * - Products form the building block of factories.
 * 
 * Abstract Factory
 *   - Defines abstract methods to create Products (ProductA, ProductB) 
 *   - Defines concrete operational methods that call the above abstract methods to obtain
 *     references to Products and operate on them.   
 * 
 * ConcreteFactory
 *   - Extends an Abstract Factory
 *   - Provides implementation for abstract methods by creating Products
 *   - Has the liberty to choose appropriate concrete Product, for example 
 *       - ProductA1 for type ProductA
 *       - ProductB2 for type ProductB
 *   - The operation to be performed using the Products is taken care by the Abstract Factory
 *     implementation. The job of the concrete factory is to provide the concrete building blocks.
 *     
 *   Analogy: The concrete factory specify which brick to use and which cement to use, the abstract
 *   factory has the code to build the house.
 *    
 *</pre>
 */

public class FactoryPattern 
{
    public static void main(String arg[]) 
    {
        Factory f = new FactoryB ();
        System.out.println (f.operaton());
    }
}


/* ----------------------- Product A ------------------------- */

abstract class ProductA
{
    
}

class ProductA1 extends ProductA
{
   public String toString ()
   {
       return "[Product-A1]";
   }
}

class ProductA2 extends ProductA
{
   public String toString ()
   {
       return "[Product-A2]";
   }
}

/* ----------------------- Product B ------------------------- */

abstract class ProductB
{
    
}

class ProductB1 extends ProductB
{
   public String toString ()
   {
       return "[Product-B1]";
   }
}

class ProductB2 extends ProductB
{
   public String toString ()
   {
       return "[Product-B2]";
   }
}

/* ----------------------- Factory ------------------------- */

abstract class Factory
{
    
    protected abstract ProductA factoryMethodCreateProductA ();
    
    protected abstract ProductB factoryMethodCreateProductB ();
    
    public String operaton ()
    {
        ProductA productA = factoryMethodCreateProductA ();
        ProductB productB = factoryMethodCreateProductB ();
        return "Machine with products: " + productA + " " + productB;
    }
}

class FactoryA extends Factory
{
    protected ProductA factoryMethodCreateProductA () 
    {
        return new ProductA1 ();
    }
    
    protected ProductB factoryMethodCreateProductB () 
    {
        return new ProductB2 ();
    }

}

class FactoryB extends Factory
{
    protected ProductA factoryMethodCreateProductA () 
    {
        return new ProductA2 ();
    }
    
    protected ProductB factoryMethodCreateProductB () 
    {
        return new ProductB1 ();
    }
}

