package four.b;

import java.util.concurrent.ThreadLocalRandom;

public class Gardener implements Runnable {
    public Garden garden;

    public Gardener(Garden garden){
        this.garden=garden;
    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            int i= ThreadLocalRandom.current().nextInt(0,garden.getRows());
            int j=ThreadLocalRandom.current().nextInt(0,garden.getColumns());

            try{
                if(garden.Faded(i,j)){
                    garden.watering(i,j,30);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            System.out.println("Watered "+ i +" "+ j+ " cell");
        }
    }
}
