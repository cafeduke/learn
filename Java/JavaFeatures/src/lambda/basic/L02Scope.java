package lambda.basic;

import lambda.basic.myfunc.Stylist;
import util.Util;

public class L02Scope
{
   private int varInstance = 1;
   
   private static int varStatic = 1;
   
   public static void main (String arg[])
   {
      /**
       * Use variable inside lambda
       */
      Util.printHeading("Variable Inside Lambda");
      // Access final variables
      String localVar = "-)";
      Stylist stylistC = (mesg) ->  mesg + localVar;
      System.out.println(stylistC.doStyle(":"));
      
      /**
       * ERROR: Variable used in lambda should be final or effectively final!
       * localVar = null;
       */
      
      /**
       * Instance and static variables can be accessed/modified within lambda
       */
      new L02Scope ().testlambdaScope();
   }
   
   public void testlambdaScope ()
   {
      Stylist stylist = (mesg) -> mesg + " InstanceVariable=" + ++varInstance + " StaticVariable=" + ++varStatic;
      System.out.println(stylist.doStyle("lambda:"));
   }
}
