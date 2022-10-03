package four.b;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Monitor1 implements Runnable{
    private Garden garden;
//    private File file=new File("garden.txt");
    private FileWriter fileWriter;

    public Monitor1(Garden garden){
        this.garden=garden;
    }
//        try {
//            this.file.createNewFile();
//            this.fileWriter = new FileWriter(this.file);
//        } catch (IOException e){
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            try{
                garden.writeToFile();
            }catch (InterruptedException| IOException e){
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            System.out.println("Check file");
        }

    }
}
