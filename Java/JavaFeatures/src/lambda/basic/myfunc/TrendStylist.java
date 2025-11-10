package lambda.basic.myfunc;

public interface TrendStylist
{
   public abstract String getLeftStyle  ();
   
   public abstract String getRightStyle ();
   
   default String padStyles (String mesg)
   {
      return getLeftStyle() + mesg + getRightStyle ();
   }
}
