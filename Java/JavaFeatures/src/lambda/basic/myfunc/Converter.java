package lambda.basic.myfunc;

@FunctionalInterface
public interface Converter<F,T>
{
   public abstract T doConvert (F from);
}
