import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class HotSpotDemo
{
   public static void main(String arg[])
   {
      Manager manager = (arg.length == 1) ? new Manager(arg[0]) : new Manager ("");
      new Thread (manager).start();
   }
}

class Manager implements Runnable
{
   String title;
   MyFrame mf;
   long starttime, stoptime;
   public static boolean start = false;
   

   Manager (String title)
   {
      starttime = stoptime = 0;
      mf = new MyFrame("");
      this.title = title;
   }

   public void run ()
   {
      mf.setTitle(title);
      while (true)
      {
         // System.out.println("Start=" + start);
         Global.delay (1);
         if (start)
         {
//            System.out.println("In start");

            starttime = System.currentTimeMillis();
            
//            System.out.println("Calling remove all");
            mf.removeAll();
            stoptime = System.currentTimeMillis();
            break;
         }
      }
      String msg1 = "Time Taken           : " + (stoptime - starttime) + " milli seconds";

      long sec = (long) ((stoptime - starttime) / 1000);
      long min = (long) (sec / 60);
      sec = sec % 60;
      String msg2 = "Time Taken (min:sec) : " + min + " : " + sec;

      JOptionPane.showMessageDialog(mf, msg1 + "\n" + msg2, "Report", JOptionPane.PLAIN_MESSAGE);
   }
};

class MyFrame extends Frame implements ActionListener
{
   BufferedImage buffer;
   BufferedImage bgbuffer;
   Image imgBrick;
   Button b;

   public static final int X = 10;
   public static final int Y = 25;

   MyFrame(String title)
   {
      super(title);
      setLayout(null);

      b = new Button("Start");
      buffer = new BufferedImage(306, 459, BufferedImage.TYPE_INT_ARGB);
      bgbuffer = new BufferedImage(306, 459, BufferedImage.TYPE_INT_ARGB);

      Image temp = Global.loadImage("Duke.jpg", this);
      imgBrick = Global.loadImage("iconblueglass.gif", this);
      setSize(buffer.getWidth() + X, buffer.getHeight() + Y);

      Graphics g = buffer.getGraphics();
      g.drawImage(temp, 0, 0, null);
      g.dispose();

      g = bgbuffer.getGraphics();
      g.drawImage(temp, 0, 0, null);
      g.dispose();

      b.setBounds(125, 213, 50, 25);
      b.addActionListener(this);
      add(b);

      Brick.init(this);
      drawAll();

      setResizable(false);
      setVisible(true);
      addWindowListener(new WindowAdapter()
         {
            public void windowClosing(WindowEvent we)
            {
               dispose();
               System.exit(0);
            }
         });
   }

   public void actionPerformed(ActionEvent ae)
   {
      Manager.start = true;
      remove(b);
   }

   public void drawAll()
   {
      int r, c;
      for (r = 0; r < 25; ++r)
         for (c = 0; c < 17; ++c)
            Brick.draw(1, r, c);
      repaint();
   }

   public void removeAll()
   {
      int r, c;
      for (r = 24; r >= 0; --r)
         for (c = 16; c >= 0; --c)
         {
            // System.out.println("At r=" + r + " c=" + c);
            Brick.draw(0, r, c);
            heavyLoad();
         }
   }

   public void heavyLoad()
   {
      int i, j;
      for (i = 0; i < 100000; ++i)
         Global.random(100);
   }

   public void update(Graphics g)
   {
      paint(g);
   }

   public void paint(Graphics g)
   {
      if (buffer != null)
         g.drawImage(buffer, X / 2, Y, null);
   }
};

class Brick
{
   private static MyFrame mf;
   public static final byte BRKSIZE = 17;
   public static int STARTX = 0;
   public static int STARTY = 0;

   public static void init(MyFrame mf)
   {
      Brick.mf = mf;
      STARTX = 0;
      STARTY = mf.buffer.getHeight();
   }

   public static int getX(int c)
   {
      return Brick.STARTX + c * BRKSIZE + c;
   }

   public static int getY(int r)
   {
      return Brick.STARTY - (r + 1) * BRKSIZE - r;
   }

   public static void draw(int flag, int r, int c)
   {
      int x = Brick.getX(c);
      int y = Brick.getY(r);
      int i;

      Graphics2D g = mf.buffer.createGraphics();
      if (flag == 0)
      {
         Image temp = mf.bgbuffer.getSubimage(x, y, BRKSIZE, BRKSIZE);
         g.drawImage(temp, x, y, null);
         mf.repaint();
         return;
      }
      g.drawImage(mf.imgBrick, x, y, null);
   }
}

class Global
{
   private static Image img;

   public static Image loadImage(String s, Container t)
   {
      Toolkit tk = t.getToolkit();
      img = tk.getImage(s);
      MediaTracker tracker = new MediaTracker(t);
      tracker.addImage(img, 0);
      try
      {
         tracker.waitForID(0);
      }
      catch (Exception e)
      {
         Global.die("Interrupted while loading" + e);
      }
      return img;
   }

   public static void delay(int x)
   {
      try
      {
         if (x != 0)
            Thread.sleep(x);
         else
            Thread.sleep(5);
      }
      catch (Exception e)
      {
         System.out.println("Oh God Interrupt");
         System.exit(0);
      }
   }

   public static void die(String s)
   {
      System.out.println(s);
      System.exit(0);
   }

   public static int random (int x)
   {
      int count = 0, y = x;
      double d;
      while (x != 0)
      {
         count++;
         x = x / 10;
      }
      count = (int) (Math.random() * Math.pow(10, count));
      return count % y;
   }
};
