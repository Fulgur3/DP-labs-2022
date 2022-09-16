package second;


import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;

class first implements Runnable{
    SynchronousQueue<Integer> queue;

    public first(SynchronousQueue<Integer> queue){
        this.queue=queue;
        new Thread(this,"first").start();
    }

    @Override
    public void run(){
        while(b.item.get()<b.warehouseSize){
            try{
                b.item.getAndIncrement();
                queue.put(b.item.get());
                System.out.println("first " + b.item.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class second implements Runnable{
    SynchronousQueue<Integer> queue1;
    SynchronousQueue<Integer> queue2;

    public second(    SynchronousQueue<Integer> queue1,    SynchronousQueue<Integer> queue2){
        this.queue1=queue1;
        this.queue2=queue2;
        new Thread(this,"second").start();
    }

    @Override
    public void run(){
        while(queue1.isEmpty() || queue2.isEmpty()){
            try{
                int temp=queue1.take();
                queue2.remove(temp);
                queue2.put(temp);
                System.out.println("second" + temp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
        }

        class third implements Runnable{
    SynchronousQueue<Integer> queue;

    public third(SynchronousQueue<Integer> queue){
        this.queue=queue;
        new Thread(this, "third").start();
    }

    @Override
            public void run(){
        while(queue.isEmpty()){
            try{
                int temp=queue.take();
                queue.remove(temp);
                System.out.println("third"+ temp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
        }

public class b {
    public static int warehouseSize=50;
    public static AtomicInteger item= new AtomicInteger(0);

    public static void main(String[] args){
        SynchronousQueue<Integer> queue1 = new SynchronousQueue<Integer>();
        SynchronousQueue<Integer> queue2 = new SynchronousQueue<Integer>();
        new first(queue1);
        new second(queue1,queue2);
        new third(queue2);
    }
}
