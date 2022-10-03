package four.b;


import java.util.concurrent.ThreadLocalRandom;

public class Nature implements Runnable{
    private Garden garden;

    public Nature(Garden garden){
        this.garden=garden;
    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            int i= ThreadLocalRandom.current().nextInt(0,garden.getRows());
            int j= ThreadLocalRandom.current().nextInt(0,garden.getColumns());
            int numb=ThreadLocalRandom.current().nextInt(0,100);
            try{
                garden.setMatrix(i,j,numb);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            System.out.println("Nature did "+i + " "+ j+ "watering lelel to "+ numb);
        }
    }
}
