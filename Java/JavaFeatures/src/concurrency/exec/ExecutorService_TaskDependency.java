package concurrency.exec;

import java.util.concurrent.*;
import java.util.*;
import util.Util;

public class ExecutorService_TaskDependency
{
   private static final int DEFAULT_TASK_EXEC_TIME = 5;

   //    +-------> C1 ---+ 
   //    |               |    
   //    |               +---> B1 ----+
   //    |               |            |
   //    +-------> C2 ---+            +--> A1
   //    |         |                  | 
   //    |         +---------> B2 ----+
   // D1-----+                        |
   //    |                            +--> A2
   //    |                            |
   //    +-------> C3 -------> B3 ----+
   
   public static void main (String arg[]) throws Exception
   {
      Task taskA1 = new Task ("A1");
      Task taskA2 = new Task ("A2", 30);
      
      Task taskB1 = new Task ("B1", new Task[]{taskA1});
      Task taskB2 = new Task ("B2", new Task[]{taskA1}, 30);
      Task taskB3 = new Task ("B3", new Task[]{taskA2});
      
      Task taskC1 = new Task ("C1", new Task[]{taskB1});
      Task taskC2 = new Task ("C2", new Task[]{taskB1,taskB2});
      Task taskC3 = new Task ("C3", new Task[]{taskB3});
      
      Task taskD1 = new Task ("D1", new Task[]{taskC1, taskC2, taskC3});
      
      TaskManager manager = new TaskManager (3);
      manager.executeLeafTasks(taskA1, taskA2);
   }  
   
   static class TaskManager 
   {
      private List<Task> listExecTask = new ArrayList<> ();
      
      private List<Future<Void>> listResult = new ArrayList<> ();
      
      private Set<Task> setCompleted = new HashSet<> ();
      
      private ExecutorService executor;
      
      private int threadCount = 3;
      
      public TaskManager ()
      {
         
      }
      
      public TaskManager (int threadCount)
      {
         this.threadCount = threadCount;
      }
      
      public void executeLeafTasks (Task... task) throws Exception
      {
         executor = Executors.newFixedThreadPool(threadCount);
         
         for (Task currTask : task)
         {
            Util.threadLog("Task has zero outdegree. Executing Task=" + currTask);
            listExecTask.add(currTask); 
            listResult.add(executor.submit(currTask));
         }
         
         while (!listExecTask.isEmpty())
         {
            Set<Task> setTaskToCheck = getInDegreeOfCompletedTask ();
            execTaskWithCompletedOutDegree(setTaskToCheck);

            if (setTaskToCheck.isEmpty() && !listExecTask.isEmpty())
            {
               Util.threadLog("No task completed execution, sleeping");
               Util.sleepInMilli(DEFAULT_TASK_EXEC_TIME + 1);
            }
         }
         executor.shutdown();
      }
      
      private void execTaskWithCompletedOutDegree (Set<Task> setTaskToCheck)
      {
         for (Task currTask : setTaskToCheck)
         {
            if (setCompleted.containsAll(currTask.setOutTask))
            {
               Util.threadLog("Dependent tasks completed. Executing Task=" + currTask);
               listExecTask.add(currTask); 
               listResult.add(executor.submit(currTask));
            }
         } 
      }

      /**
       * Iterate through the completion result
       *    - Add all completed task to <b>setCompleted</b>
       *    - Add all tasks to check (if all dependencies are completed) to <b>setTaskToCheck</b>
       */
      private Set<Task> getInDegreeOfCompletedTask ()
      {
         Set<Task> setTaskToCheck = new TreeSet<> ((tA, tB) -> tA.name.compareTo(tB.name));
         Set<Task> setCompletionSubset = new TreeSet<> ((tA, tB) -> tA.name.compareTo(tB.name));
         
         for (int i = 0; i < listResult.size(); ++i)
         {
            Future<Void> currResult = listResult.get(i);
            Task currTask = listExecTask.get(i);            
            if (currResult.isDone())
            {
               setCompletionSubset.add (currTask);
               setTaskToCheck.addAll(currTask.setInTask);
               listResult.remove(i);
               listExecTask.remove(i);
            }
         }         
         setCompleted.addAll(setCompletionSubset);
         Util.threadLog("Status report - ExecutingTask=" + listExecTask + " CompletedTask=" + setCompletionSubset + " InTaskToCheck=" + setTaskToCheck);
         
         return setTaskToCheck;
      }      
   }
   
   
   static class Task implements Callable<Void>
   {
      private String name;
      
      private Set<Task> setInTask = new HashSet<> ();
      
      private Set<Task> setOutTask = new HashSet<> ();
      
      private int sleepInMilli;
      
      public Task (String name)
      {
         this (name, new Task[0]);
      }
      
      public Task (String name, int sleepInMilli)
      {
         this (name, new Task[0], sleepInMilli);
      }
      
      public Task (String name, Task outTask[])
      {
         this (name, outTask, DEFAULT_TASK_EXEC_TIME);
      }
      
      public Task (String name, Task outTask[], int sleepInMilli)
      {
         this.name = name;
         setOutTask.addAll(Arrays.asList(outTask));
         for (Task task : outTask)
            task.addInTask (this);
         this.sleepInMilli = sleepInMilli;
      }
      
      public void addInTask (Task task)
      {
         setInTask.add(task);
      }
      
      public Void call() throws Exception
      {
         Util.threadLog("Started task", name);
         Util.sleepInMilli(sleepInMilli);
         Util.threadLog("Finished task", name);
         return null;
      }
      
      @Override
      public String toString ()
      {
         StringBuilder builder = new StringBuilder ();
         builder.append(name);
         return builder.toString();
      }
      
      private static String getTaskList (Set<Task> setTask)
      {
         final StringBuilder builder = new StringBuilder ("{");
         setTask.forEach((t) -> builder.append(t.name).append(","));
         int lastComma = builder.lastIndexOf(",");
         if (lastComma != -1)
            builder.delete (lastComma, builder.length());
         builder.append("}");
         return builder.toString();
      }
      
   }
}
