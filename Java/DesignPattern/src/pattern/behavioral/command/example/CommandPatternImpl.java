package pattern.behavioral.command.example;

import java.util.*;

public class CommandPatternImpl
{
   
   public static final String delimLine = System.getProperty("line.separator");

   public static void main(String[] args)
   {
      String argOn [] =  {"-switch", "on"};
      String argOff [] = {"-switch", "off"};
      
      String argSpeedOne  []  = {"-speed", "1"};
      String argSpeedTwo  []  = {"-speed", "2"};
      String argSpeedThree [] = {"-speed", "3"};
      
      Light lightHall    = new Light ("Hall");
      Light lightKitchen = new Light ("Kitchen");
      Fan   fanHall      = new Fan   ("Hall");
      Fan   fanRoom      = new Fan   ("Room");
      
      LightCommand cmdHallLight    = new LightCommand (lightHall);      
      LightCommand cmdKitchenLight = new LightCommand (lightKitchen);
      
      FanCommand   cmdHallFan      = new FanCommand   (fanHall);
      FanCommand   cmdRoomFan      = new FanCommand   (fanRoom);
      
      Button button [] = {
            
            // Button #0                          Button #1 
            new Button (cmdHallLight   , argOn),  new Button (cmdHallLight   , argOff),
            
            // Button #2                          Button #3
            new Button (cmdKitchenLight, argOn),  new Button (cmdKitchenLight, argOff),
            
            // Button #4                          Button #5                             
            new Button (cmdHallFan     , argOn),  new Button (cmdHallFan     , argOff),            
            // Button #6                          Button #7                             Button #8
            new Button (cmdHallFan, argSpeedOne), new Button (cmdHallFan, argSpeedTwo), new Button (cmdHallFan, argSpeedThree),
            
            // Button #9                          Button #10                            
            new Button (cmdRoomFan     , argOn),  new Button (cmdRoomFan     , argOff),            
            //Button #11                          Button #12                            Button #13
            new Button (cmdRoomFan, argSpeedOne), new Button (cmdRoomFan, argSpeedTwo), new Button (cmdRoomFan, argSpeedThree)
      };
      
      RemoteControl remoteControl = new RemoteControl (button.length);
      remoteControl.setButton(button);
      
      System.out.println ("----------------- Run ------------------");
      remoteControl.testAllButton();
      
      System.out.println ("----------------- Undo ------------------");
      remoteControl.testUndo ();
      
   }
   
   public static void die (String mesg)
   {
      System.out.println ("Error : " +  delimLine + mesg);
      System.exit(1);
   }

}

class RemoteControl
{
   List<Button> listButton = null;
   
   Stack<Integer> stackHistory = null;
   
   RemoteControl (int buttonCount)
   {
      listButton = new ArrayList<Button>();
      stackHistory = new Stack<Integer> ();
   }
   
   public void setButton (Button button[])
   {
      listButton.addAll(Arrays.asList(button));
   }
   
   public void setButton (int index, Button button)
   {
      listButton.set(index, button);
   }
   
   public void pressButton (int index)
   {
      if (index >= listButton.size())
         CommandPatternImpl.die("Invalid Button Index " + index);
      
      stackHistory.push(index);
      listButton.get(index).press();
   }
   
   public void testAllButton  ()
   {
      for (int i = 0; i < listButton.size(); ++i)
         pressButton (i);
   }
   
   public void testUndo ()
   {
      while (!stackHistory.isEmpty())
         listButton.get(stackHistory.pop()).undo();
   }
}

class Button
{
   Command command = null;
   
   String arg[] = null;
   
   Button ()
   {
      this (new NoCommand(), null);
   }
   
   Button (Command command)
   {
      this (command, null);
   }
   
   Button (Command command, String arg[])
   {
      this.command = command;
      this.arg = arg;
   }
   
   public void press ()
   {
      command.execute(arg);
   }
   
   public void undo ()
   {
      command.undo();
   }
}


interface Command
{
   public void execute (String arg[]);
   public void undo ();
}

/* ------------------ Concrete Command --------------------- */

class LightCommand implements Command
{
   private static final String USAGE = "Light Command Usage : " + CommandPatternImpl.delimLine + 
   "   -switch <on|off> ";
   
   private Light light = null;
   
   private Stack<String> stackHistory = null;
   
   LightCommand (Light light)
   {
      this.light = light;
      stackHistory = new Stack<String> ();
   }
   
   private void execute  (String arg[], boolean addToHistory)
   {
      Map<String,String> mapArg = CommandUtility.parseArg(arg, USAGE);
      
      if (addToHistory)
         addToHistory (mapArg);
      
      for (String currKey : mapArg.keySet())
      {
         if (currKey.equals("switch"))
         {
            if (mapArg.get("switch").equals("on"))
               light.on();
            else
               light.off();
         }
      }      
   }

   @Override
   public void execute (String arg[])
   {
      execute (arg, true);
   }
   
   public void addToHistory (Map<String,String> mapArg)
   {
      StringBuffer buffer = new StringBuffer ();
      for (String currKey : mapArg.keySet())
      {
         buffer.append(" -" + currKey + " ");
         if (currKey.equals("switch"))
         {
            buffer.append (light.getStatus() ? "on" : "off");
         }
      }
      // System.out.println ("\nAdding to History :" + buffer.toString());
      stackHistory.push(buffer.toString().trim());
   }   

   @Override
   public void undo()
   {
      if (stackHistory.isEmpty())
      {
         System.out.println ("Nothing to undo");
         return;
      }
      execute (stackHistory.pop().split(" "), false);
   }
}

class FanCommand implements Command
{
   private Fan fan;
   
   private static final String USAGE = "Fan Command Usage : " + CommandPatternImpl.delimLine + 
           "   -switch <on|off> " + CommandPatternImpl.delimLine +
           "   -speed <rotation speed[1-5]>";
   
   private Stack<String> stackHistory = null;
   
   FanCommand (Fan fan)
   {
      this.fan = fan;
      stackHistory = new Stack<String> ();
   }
   
   
   private void execute (String arg[], boolean addToHistory)
   {
      Map<String,String> mapArg = CommandUtility.parseArg(arg, USAGE);
      
      if (addToHistory)
         addToHistory (mapArg);
      
      for (String currKey : mapArg.keySet())
      {
         if (currKey.equals("switch"))
         {
            if (mapArg.get("switch").equals("on"))
               fan.on();
            else
               fan.off();
         }
         else if (currKey.equals("speed"))
         {
            Integer speed = new Integer (mapArg.get("speed"));
            fan.setSpeed(speed);
         }
      }      
   }

   @Override
   public void execute (String arg[])
   {
      execute (arg, true);
   }
   
   public void addToHistory (Map<String,String> mapArg)
   {
      StringBuffer buffer = new StringBuffer ();
      for (String currKey : mapArg.keySet())
      {
         buffer.append(" -" + currKey + " ");
         if (currKey.equals("switch"))
         {
            buffer.append (fan.getStatus() ? "on" : "off");
         }
         else if (currKey.equals("speed"))
         {
            buffer.append (fan.getSpeed());
         }
      }
      // System.out.println ("\nAdding to History :" + buffer.toString());
      stackHistory.push(buffer.toString().trim());
   }

   @Override
   public void undo()
   {
      if (stackHistory.size() == 0)
      {
         System.out.println ("Nothing to undo");
         return;
      }
      execute (stackHistory.pop().split(" "), false);
   }
}

class NoCommand implements Command
{
   @Override
   public void execute (String arg[])
   {
      
   }
   
   @Override
   public void undo()
   {
   }   
}

class CommandUtility
{
   public static Map<String,String> parseArg (String arg[], String usage)
   {
      Map<String,String> map = new HashMap<String,String> ();
      for (int i = 0; i < arg.length; ++i)
      {
         String currOption = arg[i];
         if (!currOption.startsWith("-"))
            CommandPatternImpl.die("Invalid option " + currOption + usage);
         
         currOption = currOption.substring(1);
         String currValue  = CommandUtility.getOptionValue (++i, arg, usage);
         map.put(currOption, currValue);
      }
      return map;
   }
   
   private static String getOptionValue (int index, String arg[], String usage)
   {
      if (index >= arg.length)
         CommandPatternImpl.die(usage);
      return arg[index];
   }
   
   public static String join (String arg[], String sep)
   {
      StringBuffer buffer = new StringBuffer ();
      for (String currElement : arg)
      {
         buffer.append (currElement);
         buffer.append (sep);
      }
      return buffer.toString();
   }

   public static String join (String array[])
   {
      return join (array, " ");
   }

}

/* ----------------------- Appliances ---------------------- */

abstract class Appliance
{
   String location;
   
   String type;
   
   boolean status = false;
   
   Appliance (String location, String type)
   {
      this.type = type;
      this.location = location;
   }
   
   public void on ()
   {
      status = true;
      System.out.println (type + " in " + location + " is on");
   }
   
   public void off ()
   {
      status = false;
      System.out.println (type + " in " + location + " is off");
   }
   
   public boolean getStatus ()
   {
      return status;
   }
}

class Light extends Appliance 
{
   Light (String location)
   {
      super (location, "Light");
   }
}

class Fan extends Appliance 
{
   int speed = 1;
   
   Fan (String location)
   {
      super (location, "Fan");
   }
   
   public void setSpeed (int speed)
   {
      this.speed = speed;
      System.out.println ("Fan in " + location + " speed set to " + speed);
   }
   
   public int getSpeed ()
   {
      return speed;
   }
}
