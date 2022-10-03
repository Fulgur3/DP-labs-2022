package four.b;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Garden {
    private int rows;
    private int columns;
    private int[][] matrix;

    private ReentrantReadWriteLock readWriteLock= new ReentrantReadWriteLock();

    private Lock readLock=readWriteLock.readLock();
    private Lock writeLock=readWriteLock.writeLock();

    public Garden(int rows, int columns){
        this.rows=rows;
        this.columns=columns;
        this.matrix=new int[rows][columns];
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                matrix[i][j]= ThreadLocalRandom.current().nextInt(0,100);
            }
        }
    }

    public int getRows(){
        return rows;
    }

    public int getColumns(){
        return columns;
    }

    public void setMatrix(int i, int j, int numb)throws InterruptedException{
        writeLock.lock();
        matrix[i][j]=numb;
        Thread.sleep(1000);
        writeLock.unlock();
    }

    public boolean Faded(int i, int j){
        readLock.lock();
        boolean fade=matrix[i][j]<30;
        readLock.unlock();
        return fade;
    }

    public void watering(int i, int j, int water) throws InterruptedException{
        writeLock.lock();
        matrix[i][j]+=water;
        Thread.sleep(1000);
        writeLock.unlock();
    }

    public void printMatrix() throws InterruptedException{
        readLock.lock();
        Thread.sleep(1000);
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                System.out.print(matrix[i][j]+ " ");
            }
            System.out.println();
        }
        readLock.unlock();
    }

    public void writeToFile() throws IOException, InterruptedException{
        readLock.lock();
        Thread.sleep(1000);
        FileWriter fileWriter=new FileWriter("./src/four/b/garden.txt");
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++) {
                fileWriter.write(matrix[i][j]+" ");
           }
            fileWriter.write("\n");
            }

        fileWriter.close();
        readLock.unlock();
    }

}
