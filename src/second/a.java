package second;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class a {
    private int[][] forest;
    private final AtomicBoolean FoundBear;
    private final AtomicInteger CurrentRow;
    private final Integer ForestSize;
    private final Integer ThreadsCount;
    private Thread[] threads;
    private class Bees extends Thread {
        public Bees() {
        }

        public void run() {
            while (!FoundBear.get() && CurrentRow.get() < ForestSize) {
                checkRow(CurrentRow.get());
                CurrentRow.set(CurrentRow.get() + 1);
            }
        }
    }

    public a(Integer ForestSize) {
        this.ForestSize = ForestSize;
        this.ThreadsCount = (int)Math.sqrt(ForestSize);
        this.threads = new Thread[ThreadsCount];
        forest = new int[ForestSize][ForestSize];
        for (int i = 0; i < ForestSize; i++) {
            for (int j = 0; j < ForestSize; j++) {
                forest[i][j] = 0;
            }
        }

        int column = (int)(Math.random() * ForestSize);
        int row = (int)(Math.random() * ForestSize);
        forest[row][row] = 1;
        System.out.println("Vinnie is in row:" + row + "column" + column);
        FoundBear = new AtomicBoolean(false);
        CurrentRow = new AtomicInteger(0);
    }

    private void checkForest() {
        for (int i = 0; i < ThreadsCount; i++) {
            threads[i]=new Bees();
            threads[i].start();
        }
        for(int i=0; i<ThreadsCount;i++){
            try{
                threads[i].join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }


private void checkRow(int row){
    if(FoundBear.get()){return;}
    System.out.println(Thread.currentThread().getName()+" group of bees in row"+ row);
    for(int i=0; i<ForestSize;i++){
        if(forest[row][i]==1){
            System.out.println(Thread.currentThread().getName()+" Vinnie was founded in row" + row);
            FoundBear.set(true);
            break;
        }
    }
}


public static void main(String[] args){
    a func= new a(100);
    func.checkForest();
}
}
