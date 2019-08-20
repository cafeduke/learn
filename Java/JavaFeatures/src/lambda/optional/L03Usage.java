package lambda.optional;

import java.util.Optional;
import java.util.function.Function;

public class L03Usage
{
   public static void main (String arg[])
   {
      /**
       * Computer with SoundCard
       * SoundCard with USB
       */
      USB usbA = new USB ("VersionA");
      SoundCard soundCardA = new SoundCard (usbA);
      Computer compA = new Computer (soundCardA);
      displayUSBVersion (compA);

      /**
       * Computer with SoundCard
       * SoundCard WITHOUT USB
       */
      SoundCard soundCardB = new SoundCard (null);
      Computer compB = new Computer (soundCardB);
      displayUSBVersion (compB);
      
     
      /**
       * Can force null :)
       */
      displayUSBVersion (null);
   }

   public static void displayUSBVersion (Computer comp)
   {
      String usbVersion = Optional.ofNullable(comp)
          .flatMap ((c) -> c.getSoundCard())
          .flatMap (SoundCard::getUSB)
          .map (USB::getVersion)
          .orElse("UNKNOWN");
      
      System.out.println(usbVersion);
   }
 
   static class Computer
   {
      private Optional<SoundCard> soundCard = Optional.empty();
      
      public Computer (SoundCard card)
      {
         soundCard = Optional.ofNullable(card);
      }
      
      /**
       * @return Returns Optional with soundcard or empty optional. (NEVER null)
       */
      public Optional<SoundCard> getSoundCard ()
      {
         return soundCard;
      }
   }   
   
   static class SoundCard
   {
      private Optional<USB> usb = Optional.empty ();
      
      public SoundCard (USB usb)
      {
         this.usb = Optional.ofNullable (usb);
      }

      /**
       * @return Returns Optional with USB or empty optional. (NEVER null)
       */
      public Optional<USB> getUSB ()
      {
         return usb;
      }
   }
   
   static class USB
   {
      private String version = "";
      
      public USB (String version)
      {
         this.version = version;
      }
      
      /**
       * @return Version string or null;
       */
      public String getVersion ()
      {
         return version;
      }
   }
}
