package exec.schedule;

import java.util.*;
import util.Util;
import exec.schedule.ScheduleUtil.*;

public class TimerFlaws
{
   public static void main (String arg[])
   {
      TimerFlaws timerFlaws = new TimerFlaws ();
      timerFlaws.demoTimerWithExceptionTasks();
      timerFlaws.demoTimerWithTaskPileUp();
   }
   
   public void demoTimerWithExceptionTasks ()
   {
      Util.printHeading ("Timer Ignores Scheduled Tasks After Exception");
      Timer timer = new Timer ();
      timer.schedule(new SuccessTask (), 1000L);
      timer.schedule(new FailureTask (), 3000L);      
      timer.schedule(new SuccessTask (), 5000L);
      Util.sleep (6);
   }
   
   public void demoTimerWithTaskPileUp ()
   {
      Util.printHeading ("Lengthy Tasks Make Timer Miss Schedule");
      Timer timer = new Timer ();
      timer.schedule(new DelayTask ("TaskA", 5000), 1000L);
      timer.schedule(new DelayTask ("TaskB", 5000), 2000L);
      timer.schedule(new DelayTask ("TaskC", 5000), 3000L);
   }
}
