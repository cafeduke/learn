/**
 *
 */
package pattern.behavioral.observer.example;

import java.util.*;

/**
 * @author Raghunandan.Seshadri
 * @owner Raghunandan.Seshadri
 */
public class ObserverPatternImpl
{
    public static void main(String arg[])
    {
        CustomerNerd nerd = new CustomerNerd();
        CustomerMasti masti = new CustomerMasti();
        CustomerSarvesh sarvesh = new CustomerSarvesh();

        ReadersDelightAgency readerDelight = new ReadersDelightAgency();
        readerDelight.registerNewsPaper(nerd);
        readerDelight.registerMagazine(nerd);
        readerDelight.registerMagazine(masti);
        readerDelight.registerNewsPaper(sarvesh);
        readerDelight.notifySubscribers();

        MorningMagicAgency morningMagic = new MorningMagicAgency();
        morningMagic.registerNewsPaper(nerd);
        morningMagic.registerBreakfast(masti);
        morningMagic.registerBreakfast(sarvesh);
        morningMagic.notifySubscribers();

        System.out.println("___________________________________________________");
        System.out.println();

        readerDelight.unregisterMagazine(masti);
        readerDelight.notifySubscribers();
        morningMagic.notifySubscribers();
    }
}

/* -------------------------------- Subject ----------------------------- */

interface Agency
{
    public void notifySubscribers();
}

class ReadersDelightAgency implements Agency
{
    private String className = "[" + this.getClass().getSimpleName() + "] ";

    List<NewsPaperSubscriber> listNewsPaperSubscriber = new ArrayList<NewsPaperSubscriber>();

    List<MagazineSubscriber> listMagazineSubscriber = new ArrayList<MagazineSubscriber>();

    public void registerNewsPaper(NewsPaperSubscriber subscriber)
    {
        listNewsPaperSubscriber.add(subscriber);
    }

    public void registerMagazine(MagazineSubscriber subscriber)
    {
        listMagazineSubscriber.add(subscriber);
    }

    public void unregisterNewsPaper(NewsPaperSubscriber subscriber)
    {
        listNewsPaperSubscriber.remove(subscriber);
    }

    public void unregisterMagazine(MagazineSubscriber subscriber)
    {
        listMagazineSubscriber.remove(subscriber);
    }

    @Override
    public void notifySubscribers()
    {
        System.out.println();
        System.out.println(className + "Delivering News Paper");
        System.out.println("-----------------------------------------------");
        for (NewsPaperSubscriber subscriber : listNewsPaperSubscriber)
            subscriber.receiveDelivery(new NewsPaper());

        System.out.println();
        System.out.println(className + "Delivering Magazine");
        System.out.println("-----------------------------------------------");
        for (MagazineSubscriber subscriber : listMagazineSubscriber)
            subscriber.receiveDelivery(new Magazine());
    }
}

class MorningMagicAgency implements Agency
{
    private String className = "[" + this.getClass().getSimpleName() + "] ";

    List<NewsPaperSubscriber> listNewsPaperSubscriber = new ArrayList<NewsPaperSubscriber>();

    List<BreakfastSubscriber> listBreakfastSubscriber = new ArrayList<BreakfastSubscriber>();

    public void registerNewsPaper(NewsPaperSubscriber subscriber)
    {
        listNewsPaperSubscriber.add(subscriber);
    }

    public void registerBreakfast(BreakfastSubscriber subscriber)
    {
        listBreakfastSubscriber.add(subscriber);
    }

    public void unregisterNewsPaper(NewsPaperSubscriber subscriber)
    {
        listNewsPaperSubscriber.remove(subscriber);
    }

    public void unregisterBreakfast(BreakfastSubscriber subscriber)
    {
        listBreakfastSubscriber.remove(subscriber);
    }

    @Override
    public void notifySubscribers()
    {
        System.out.println();
        System.out.println(className + "Delivering News Paper");
        System.out.println("-----------------------------------------------");
        for (NewsPaperSubscriber subscriber : listNewsPaperSubscriber)
            subscriber.receiveDelivery(new NewsPaper());

        System.out.println();
        System.out.println(className + "Delivering Breakfast");
        System.out.println("-----------------------------------------------");
        for (BreakfastSubscriber subscriber : listBreakfastSubscriber)
            subscriber.receiveDelivery(new Breakfast());
    }
}

/* -------------------------------- Listeners ---------------------------- */

interface NewsPaperSubscriber
{
    public void receiveDelivery(NewsPaper paper);
}

interface MagazineSubscriber
{
    public void receiveDelivery(Magazine magazine);
}

interface BreakfastSubscriber
{
    public void receiveDelivery(Breakfast breakfast);
}

/* ---------------------------- Concrete Listener -------------------------- */

class CustomerNerd implements NewsPaperSubscriber, MagazineSubscriber
{
    private String className = "[" + this.getClass().getSimpleName() + "] ";

    @Override
    public void receiveDelivery(Magazine magazine)
    {
        System.out.println(className + "Received Magazine");
    }

    @Override
    public void receiveDelivery(NewsPaper paper)
    {
        System.out.println(className + "Received NewsPaper");
    }
}

class CustomerMasti implements MagazineSubscriber, BreakfastSubscriber
{
    private String className = "[" + this.getClass().getSimpleName() + "] ";

    @Override
    public void receiveDelivery(Magazine magazine)
    {
        System.out.println(className + "Received Magazine");
    }

    @Override
    public void receiveDelivery(Breakfast breakfast)
    {
        System.out.println(className + "Received Breakfast");
    }
}

class CustomerSarvesh implements NewsPaperSubscriber, MagazineSubscriber, BreakfastSubscriber
{
    private String className = "[" + this.getClass().getSimpleName() + "] ";

    @Override
    public void receiveDelivery(Magazine magazine)
    {
        System.out.println(className + "Received Magazine");
    }

    @Override
    public void receiveDelivery(NewsPaper paper)
    {
        System.out.println(className + "Received NewsPaper");
    }

    @Override
    public void receiveDelivery(Breakfast breakfast)
    {
        System.out.println(className + "Received Breakfast");
    }
}

/* ----------------------------- Event Object --------------------------- */

class NewsPaper
{
}

class Magazine
{
}

class Breakfast
{
}
