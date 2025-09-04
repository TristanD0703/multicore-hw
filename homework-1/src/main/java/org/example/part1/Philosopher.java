package org.example.part1;

public class Philosopher {
    private final Chopstick left;
    private final Chopstick right;
    private Thread thread;

    /* How long this Philosopher spent in each task in ms
       0 - waiting
       1 - eating
       2 - thinking
     */
    private final long[] results = new long[3];
    // A Philosopher will try to acquire chopsticks to the left, and to the right,
    // Think for a bit (probably like 1 second), then release both the chopsticks
    public Philosopher(Chopstick left, Chopstick right) {
        this.left = left;
        this.right = right;
    }

    public void startLoop(int timesToEat, int id) {
        if(timesToEat <= 0) {
            throw new IllegalArgumentException("timesToEat must be greater than 0");
        }

        thread = new Thread(() -> {
            System.out.println("Philosopher " + id + " started");
            for (int i = 0; i < timesToEat; i++) {
                // Attempt to grab chopsticks
                long waitTime = System.currentTimeMillis();
                if(!left.isAvailable()) {
                    right.acquire();
                    left.acquire();
                } else {
                    left.acquire();
                    right.acquire();
                }
                long waitTimeEnd = System.currentTimeMillis();
                results[0] += waitTimeEnd - waitTime;
                System.out.println("Philosopher " + id + " is eating...");
                // Eat...
                long eatTime = System.currentTimeMillis();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long eatTimeEnd = System.currentTimeMillis();
                left.release();
                right.release();
                results[1] += eatTimeEnd - eatTime;

                System.out.println("Philosopher " + id + " is thinking...");
                //Think...
                long thinkTime = System.currentTimeMillis();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long thinkTimeEnd = System.currentTimeMillis();
                results[2] += thinkTimeEnd - thinkTime;
            }
        });

        thread.start();
    }

    public long[] getResults() throws InterruptedException {
        thread.join();
        return results;
    }

}
