import java.util.ArrayList;
import java.util.List;

public class NumberToWords01
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
    // 012345
    String str = "113512";
    System.out.println(toWords(str));
  }

  public static String toWords(String str)
  {
    if (str.length() > 16)
      throw new IllegalArgumentException("Max size is 16 digits. Len=" + str.length());

    if (str.length() >= 8)
    {
      int len = str.length();
      String belowCr = str.substring(len - 7);
      String afterCr = (len >= 14) ? str.substring(len - 14, len - 7) : str.substring(0, len - 7);
      String thePeak = (len >= 14) ? str.substring(0, len - 14) : "";

      String word = "";
      if (!thePeak.equals(""))
        word = toWordsTwoDigit(Integer.parseInt(thePeak)) + " Crore ";
      word = word + toWords(afterCr) + " Crore " + toWords(belowCr);
      return word;
    }

    String name[] = new String[] { "Lakh", "Thousand" };
    String word = "";
    List<String> listSegment = doSplit(str);
    System.out.println(listSegment);
    int size = listSegment.size();

    for (int i = size - 2; i >= 0; --i)
    {
      int num = Integer.parseInt(listSegment.get(i));
      if (num == 0)
        continue;
      word = toWordsTwoDigit(num) + " " + name[i] + " " + word;
      word = word.trim();
    }

    int num = Integer.parseInt(listSegment.get(size - 1));
    if (size == 1)
      word = toWordsThreeDigit(num);
    else
      word = word + ((num < 100) ? " and " : " ") + toWordsThreeDigit(num);

    return word.trim();
  }

  public static List<String> doSplit(String str)
  {
    List<String> listSegment = new ArrayList<>();
    int len = str.length();

    if (len >= 3)
    {
      listSegment.add(0, str.substring(len - 3, len));
      len -= 3;
    }

    while (len >= 2)
    {
      listSegment.add(0, str.substring(len - 2, len));
      len -= 2;
    }

    if (len > 0)
      listSegment.add(0, str.substring(0, 1));

    return listSegment;
  }

  public static String toWordsThreeDigit(int num)
  {
    assert (num < 1000);

    if (num < 100)
      return toWordsTwoDigit(num);

    String word = TEEN[num / 100] + " Hundred";
    if (num % 100 == 0)
      return word;

    return word + " and " + toWordsTwoDigit(num % 100);
  }

  public static String toWordsTwoDigit(int num)
  {
    assert (num < 100);

    if (num < 20)
      return TEEN[num];

    if (num % 10 == 0)
      return TY[num / 10];

    return TY[num / 10] + " " + TEEN[num % 10];
  }
}
