package four.b;

public class Main {

    public static void main(String[] args)throws InterruptedException{
        Garden garden=new Garden(5,5);

        Thread natureThread=new Thread(new Nature(garden));
        natureThread.setDaemon(true);
        natureThread.start();

        Thread gardenerThread=new Thread(new Gardener(garden));
        gardenerThread.setDaemon(true);
        gardenerThread.start();

        Thread monitor1thread=new Thread(new Monitor1(garden));
        monitor1thread.setDaemon(true);
        monitor1thread.start();

        Thread monitor2thread=new Thread(new Monitor2(garden));
        monitor2thread.setDaemon(true);
        monitor2thread.start();

        natureThread.join();
    }
}
