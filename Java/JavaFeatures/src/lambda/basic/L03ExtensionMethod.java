package lambda.basic;

import lambda.basic.myfunc.Stylist;
import lambda.basic.myfunc.TrendStylist;


public class L03ExtensionMethod
{
   public static void main (String arg[])
   {
      MyTrendStylist myStylist = new MyTrendStylist ();
      System.out.println(myStylist.getLeftStyle());
      System.out.println(myStylist.getRightStyle());
      System.out.println(myStylist.padStyles ("HelloWorld"));   
      System.out.println(myStylist.padPipe   ("Hello World"));
      
      Stylist stylist = (s) -> "Hello " + s;
      System.out.println(stylist.padWink().padSmile().doStyle("World"));
      
      /**
       * s1 body for doStyle(s) === "Hello " + s  
       */
      Stylist s1 = (s) -> "Hello " + s;                
      
      /**
       * s1.padWink()           === ";-) " + doStyle(s)  + " (-;"
       * s2 body for doStyle(s) === ";-) " + "Hello" + s + " (-;"
       */
      Stylist s2 = s1.padWink();        
      
      /**
       * s2.padSmile()          === ":-) " + doStyle(s)  + " (-:"
       * s3 body for doStyle(s) === ":-) " + ";-) " + "Hello" + s + " (-;" + " (-:"
       */
      Stylist s3 = s2.padSmile();
      
      /**
       * s3.doSylte("World")    === ":-) " + ";-) " + "Hello" + "World" + " (-;" + " (-:"     
       */
      System.out.println(s3.doStyle("World"));
   }
}

class MyTrendStylist implements TrendStylist 
{
   @Override
   public String getRightStyle()
   {
      return "-->";
   }
   
   public String padPipe (String mesg)
   {
      return "||" + mesg + "||";
   }

   @Override
   public String getLeftStyle()
   {
      return "<--";
   }
}


