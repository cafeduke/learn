package concurrency.exec;

import java.util.concurrent.*;

import util.Util;
import java.io.*;
import java.nio.file.Path;

public class ExecutorService_ProducerConsumer02
{
   private BlockingQueue<DirTask> queue = new ArrayBlockingQueue<DirTask>(3);
   
   private ExecutorService serviceDir = Executors.newFixedThreadPool(2);
   
   private ExecutorService serviceFile = Executors.newFixedThreadPool(3);
   
   private Path pathBase;
   
   public static void main (String arg[]) throws Exception
   {
      ExecutorService_ProducerConsumerSubmit demo = new ExecutorService_ProducerConsumerSubmit ();
      demo.doProcessDir(new File ("C:\\Raghu\\Docs\\OTD"));
   }
  
   
   public void doProcessDir (File dir) throws Exception
   {
      pathBase = dir.toPath();
      
      ExecutorService execService = Executors.newFixedThreadPool(3);
      queue.put(new DirTask(dir));      
      
      DirTask currTask = null;
      while ((currTask = queue.poll(10, TimeUnit.SECONDS)) != null)
      {
         Util.threadLog("Submit task", currTask);
         execService.submit(currTask);
      }      
      execService.shutdown();
   }
   
   
   private class DirTask implements Callable<Void>
   {
      private File dir;
      
      public DirTask (File dir)
      {
         this.dir = dir;
      }      
      
      @Override
      public Void call() throws Exception
      {
         File file[] = dir.listFiles();
         for (File currFile : file)
         {
            if (currFile.isDirectory())
            {
               Util.threadLog("Add to queue. Dir=" + pathBase.relativize(currFile.toPath()));
               new DirTask(currFile);
            }
            else
            {
               // Process file
               Util.threadLog("File=" + pathBase.relativize(currFile.toPath()));
            }
         }
         return null;
      }
      
      public String toString ()
      {
         return "Dir=" + dir.getName();
      }      
   }
   
   private class FileTask implements Callable<Long>
   {
      private File file;
      
      public FileTask (File file)
      {
         this.file = file;
      }
      
      @Override
      public Long call() throws Exception
      {
         return file.getTotalSpace();
      }
      
   }
}

