import java.util.*;

public class NumberToWords
{
  private static final String[] TEEN = new String[] {
    "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
    "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Ninteen"
  };

  private static final String[] TY = new String[] {
    "Zero", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninty", "Hundrend"
  };

  public static void main(String arg[])
  {
    //012345
    String str = "100005";
    System.out.println(toWords(str));
  }

  public static String toWords(String str)
  {
    List<String> listSegment = doSplit(str);
    StringBuilder builder = new StringBuilder();
    String[] name = new String[] { "", " Thousand ", " Lakh ", " Crore " };

    if (listSegment.size() > name.length)
      throw new IllegalArgumentException("Too large a number. Num=" + str);

    if (listSegment.size() == 1)
      return toWordsThreeDigit(str);

    int index = -1;
    for (String currSegment : listSegment)
    {
      System.out.println("--------------------------------------------------");
      System.out.println("CurrSegment=" + currSegment);
      String currWord = (++index == 0) ? toWordsThreeDigit(currSegment) : toWordsTwoDigit(currSegment);
      System.out.println("CurrWord=" + currWord);

      if (currWord.equals("Zero"))
        continue;

      if (index == 0)
        builder.insert(0, currWord).insert(0, "and ");
      else
        builder.insert(0, name[index]).insert(0, currWord);
    }

    return builder.toString();
  }

  public static List<String> doSplit(String str)
  {
    List<String> listSegment = new ArrayList<>();

    char ch[] = str.toCharArray();
    if (ch.length <= 3)
    {
      listSegment.add(str);
      return listSegment;
    }

    listSegment.add(new String(ch, ch.length - 3, 3));
    for (int i = ch.length - 4; i >= 0; i -= 2)
      listSegment.add((i - 1 >= 0) ? new String(ch, i - 1, 2) : new String(ch, i, 1));

    return listSegment;
  }

  public static String toWordsThreeDigit(String str)
  {
    int num = Integer.parseInt(str);
    assert (num < 1000);

    if (num < 100)
      return toWordsTwoDigit(str);

    String word = TEEN[num / 100] + " Hundred";
    if (num % 100 == 0)
      return word;

    return word + " and " + toWordsTwoDigit(String.valueOf(num % 100));
  }

  public static String toWordsTwoDigit(String str)
  {
    int num = Integer.parseInt(str);
    assert (num < 100);

    if (num < 20)
      return TEEN[num];

    if (num % 10 == 0)
      return TY[num / 10];

    return TY[num / 10] + " " + TEEN[num % 10];
  }
}