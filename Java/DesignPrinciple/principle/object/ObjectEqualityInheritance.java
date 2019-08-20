package principle.object;


public class ObjectEqualityInheritance
{
   public static void main (String arg[])
   {
      Animal animal = new Animal (1);
      Herbivorus herbivorus = new Herbivorus (1, 2);
      Cow cow = new Cow (1, 2, 3);      
      String s1 = "Hello";
      
      System.out.println ("Animal==Herbivorus : " + animal.equals(herbivorus));
      System.out.println ("Herbivorus==Animal : " + herbivorus.equals(animal));
      
      System.out.println ("Animal==Cow : " + animal.equals(cow));
      System.out.println ("Cow==Animal : " + cow.equals(animal));
      
      System.out.println ("Herbivorus==Cow    : " + herbivorus.equals(cow));
      System.out.println ("Cow==Herbivorus    : " + cow.equals(herbivorus));    
      System.out.println ("Herbivorus==String : " + herbivorus.equals(s1));      
      
   }
}

class Animal
{
   int x;
   
   public Animal (int x)
   {
      this.x = x;
   }
   
   @Override
   public boolean equals (Object obj)
   {
      if (obj == null)
         return false;
      
      if (this == obj)
         return true;      
      
      if (this.getClass() != obj.getClass())
         return false;
      
      Animal currA = (Animal)obj;
      return x == currA.x;         
   }
}

class Herbivorus extends Animal
{
   int y;
   
   public Herbivorus (int x, int y)
   {
      super (x);
      this.y = y;
   }
   
   @Override
   public boolean equals (Object obj)
   {
      if (obj == null)
         return false;
      
      if (this == obj)
         return true;      
      
      if (this.getClass() != obj.getClass())
         return false;
      
      return y == ((Herbivorus)obj).y;
   }
}

class Cow extends Herbivorus
{
   int z;
   
   public Cow (int x, int y, int z)
   {
      super (x, y);
      this.z = z;
   }
   
   @Override
   public boolean equals (Object obj)
   {
      if (obj == null)
         return false;
      
      if (this == obj)
         return true;      
      
      if (this.getClass() != obj.getClass())
         return false;
      
      return z == ((Cow)obj).z;
   }   
}


