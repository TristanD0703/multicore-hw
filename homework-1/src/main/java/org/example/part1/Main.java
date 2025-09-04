package org.example.part1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Create 5 Philosophers and 5 chopsticks
        int philosopherCount = Integer.parseInt(args[0]);
        Philosopher[] philosophers = new Philosopher[philosopherCount];
        Chopstick[] chopsticks = new Chopstick[philosopherCount];
        for(int i = 0; i < philosophers.length; i++) {
            chopsticks[i] = new Chopstick();
        }
        for(int i = 0; i < philosophers.length; i++) {
            Chopstick left = chopsticks[i];
            // Last philosopher's chopstick wraps around to the first
            Chopstick right = chopsticks[(i+1)%chopsticks.length];

            philosophers[i] = new Philosopher(left, right);
        }

        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i].startLoop(5, i);
        }

        long maxWaitTimeMs = 0;
        long minWaitTimeMs = Long.MAX_VALUE;
        for(int i = 0; i < philosophers.length; i++) {
            Philosopher philosopher = philosophers[i];
            long[] res = philosopher.getResults();
            maxWaitTimeMs = Math.max(maxWaitTimeMs, res[0]);
            minWaitTimeMs = Math.min(minWaitTimeMs, res[0]);
            System.out.printf("Philosopher %d results:\nTime spent waiting:%d\nTime spent eating:%d\nTime spent thinking:%d\n\n", i, res[0], res[1], res[2]);
        }
        System.out.println("Max wait time seconds: " + maxWaitTimeMs/1000.0);
        System.out.println("Min wait time seconds: " + minWaitTimeMs/1000.0);
    }
}