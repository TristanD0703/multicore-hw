package org.example.part1;

public class Chopstick {
    private boolean acquired = false;
    private int acquiredCount = 0;

    // Should be a shared object to acquire. A Philosopher acquiring the lock will prevent
    // any other Philosopher from acquiring the lock
    public synchronized void acquire() throws RuntimeException{
        while(acquired){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        acquired = true;
        acquiredCount++;
        if (acquiredCount > 1) {
            throw new RuntimeException("Chopstick acquired more than one time");
        }
    }

    public synchronized void release() {
        acquiredCount--;
        if(acquired) {
            acquired = false;
            notifyAll();
        }
    }

    public boolean isAvailable() {
        return !acquired;
    }
}
