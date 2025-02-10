package exec.schedule;

import java.util.TimerTask;

import util.Util;

public class ScheduleUtil
{
   public static class FailureTask extends TimerTask
   {
      @Override
      public void run ()
      {
         Util.threadTimeStampLog ("FailureTask: Will throw runtime exception");
         throw new RuntimeException("Oops! runtime error.");
      }
   }
   
   public static  class SuccessTask extends TimerTask
   {
      @Override
      public void run ()
      {
         Util.threadLog ("SuccessTask: Hello World");
      }      
   }
   
   public static class DelayTask extends TimerTask 
   {
      private int delay = 1000;
      
      private String taskName = null;
      
      public DelayTask (String taskName, int delay)
      {
         this.taskName = taskName;
         this.delay = delay;
      }
      
      @Override
      public void run ()
      {
         Util.threadTimeStampLog  ("[DelayTask=" + taskName + "] Started execution.");
         Util.sleepInMilli(delay);
         Util.threadTimeStampLog  ("[DelayTask=" + taskName + "] Finished execution.");
      }
   }  
}
