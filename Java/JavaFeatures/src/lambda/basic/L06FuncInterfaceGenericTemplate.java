package lambda.basic;

import lambda.basic.myfunc.*;

public class L06FuncInterfaceGenericTemplate
{
   public static void main (String arg[])
   {
      Converter<String, Integer> converter = from -> Integer.valueOf (from);
      System.out.println(converter.doConvert("456"));
   }
}



