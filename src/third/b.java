package third;

import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;


public class b {
    private static final LinkedBlockingDeque<Integer> visit = new LinkedBlockingDeque<>();
    private static final Random RANDOM = new Random();


    @SuppressWarnings("BusyWait")
    static class DoHair {
        private final Integer index;
        private static Integer nextIndex = 1;

        {
            index = nextIndex++;
        }

        public void produce() {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(500 + RANDOM.nextInt(1000));
                    visit.add(index);
                    System.out.println("visitor"+index+"in queue");

                    Thread.sleep(2000 + RANDOM.nextInt(3500));

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void consume() throws InterruptedException {
            while (!Thread.interrupted()) {
                if (visit.isEmpty())
                    System.out.println("Hairdresser sleeps in chair");
                Integer visitor = visit.take();
                System.out.println("Visitor" + visitor + "is having a haircut");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread hairdresser = new Thread(() -> {
            try {
                new DoHair().consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread[] visitArr = new Thread[3];
        for (int i = 0; i < 3; i++) {
            visitArr[i] = new Thread(() ->
                    new DoHair().produce());
        }
        hairdresser.start();
        Arrays.stream(visitArr).forEach(Thread::start);

        hairdresser.join();
    }


}
