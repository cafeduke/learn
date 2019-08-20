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
      
      Stylist stylist = (s) -> "HelloWorld";
      System.out.println(stylist.padWink().padSmile().doStyle("HelloWorld"));
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


