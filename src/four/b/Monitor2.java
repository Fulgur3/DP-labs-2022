package four.b;

public class Monitor2 implements Runnable{
    private Garden garden;

    public Monitor2(Garden garden){
        this.garden=garden;
    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                garden.printMatrix();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}
