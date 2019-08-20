package principle.enumeration;

import java.util.*;

/**
 * Using EnumSet Instead Of Bit Fields
 * ----------------------------------- 
 *
 */
public class EnumSetVsBitField
{
   public static void main (String arg[])
   {
      System.out.println("Traditional Bit Field");
      System.out.println("---------------------");
      
      TraditionalText text = new TraditionalText();
      text.addStyle(TraditionalText.BOLD);
      text.addStyle(TraditionalText.UNDERLINE);
      text.addStyle(TraditionalText.ITALIC);
      System.out.println(text);
      
      text.removeStyle(TraditionalText.UNDERLINE);
      text.removeStyle(TraditionalText.ITALIC);
      System.out.println(text);
      
      System.out.println(text.hasStyle(TraditionalText.BOLD));
      System.out.println();

      /**
       * Note: Create a EnumSet and use it to add Style
       * Since EnumSet implements Set, EnumSet can be passed for methods that accept Set 
       */
      System.out.println("EnumSet");
      System.out.println("-------");
      
      EnumText enumText = new EnumText();      
      EnumSet<EnumText.Style> currStyle = EnumSet.of(EnumText.Style.BOLD, EnumText.Style.UNDERLINE, EnumText.Style.ITALIC);
      enumText.addStyle(currStyle);
      System.out.println(enumText);
      
      enumText.removeStyle(EnumSet.of(EnumText.Style.UNDERLINE, EnumText.Style.ITALIC));
      System.out.println(text);
      
      System.out.println(enumText.hasStyle(EnumText.Style.BOLD));
      System.out.println();
   }
}

class EnumText
{
   public enum Style {BOLD, ITALIC, UNDERLINE};
   
   public Set<Style> setStyle = new TreeSet<Style>();
   
   public void addStyle (Set<Style> currSetStyle)
   {
      setStyle.addAll(currSetStyle);
   }
   
   public void removeStyle (Set<Style> currSetStyle)
   {
      setStyle.removeAll(currSetStyle);
   }
   
   public boolean hasStyle (Style style)
   {
      return setStyle.contains(style);
   }
   
   @Override
   public String toString ()
   {
      return setStyle.toString();
   }
}


class TraditionalText
{
   public static final int BOLD = 1;
   
   public static final int ITALIC = 2;
   
   public static final int UNDERLINE = 4;
   
   int currStyle = 0;
   
   public void addStyle (int style)
   {
      currStyle = currStyle | style;
   }
   
   public void removeStyle (int style)
   {
      currStyle = currStyle & ~style;
   }
   
   public boolean hasStyle (int style)
   {
      return ((currStyle & style) != 0);
   }
   
   public int getStyle ()
   {
      return currStyle;
   }
   
   @Override
   public String toString ()
   {
      StringBuilder builder = new StringBuilder ("[ ");
      int styleVal []    = { BOLD  , ITALIC  , UNDERLINE};
      String styleName[] = { "BOLD", "ITALIC", "UNDERLINE"};
      
      for (int i = 0; i < styleVal.length; ++i)
      {
         if ((currStyle & styleVal[i]) != 0)
            builder.append(styleName[i] + " ");
      }
      builder.append ("]");
      return builder.toString();
   }
}
