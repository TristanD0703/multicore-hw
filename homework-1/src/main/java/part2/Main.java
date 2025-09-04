package part2;

public class Main {
    public static void main(String[] args) {
        RedBlue redBlue = new RedBlue();
        Thread[] redThreads = new Thread[10];
        Thread[] blueThreads = new Thread[10];

        for (int i = 0; i < redThreads.length; i++) {
            redThreads[i] = new Thread(() -> {
                try {
                    System.out.println("Waiting for red...");
                    redBlue.acquireRed();
                    System.out.println(Thread.currentThread().getName() + " acquired red");
                    Thread.sleep(1000);
                    redBlue.releaseRed();
                    System.out.println(Thread.currentThread().getName() + " released red");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });

            blueThreads[i] = new Thread(() -> {
                try {
                    Thread.sleep(500);
                    System.out.println("Waiting for blue...");
                    redBlue.acquireBlue();
                    System.out.println(Thread.currentThread().getName() + " acquired blue");
                    Thread.sleep(1000);
                    redBlue.releaseBlue();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
            redThreads[i].start();
            blueThreads[i].start();
        }


    }
}
