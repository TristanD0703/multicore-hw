package part2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RedBlue {
    private final Lock lock = new ReentrantLock();
    private int redAcquires;
    private int blueAcquires;
    Condition redCondition = lock.newCondition();
    Condition blueCondition = lock.newCondition();

    public void acquireRed() throws InterruptedException {
        try {
            lock.lock();
            redAcquires++;
            while (blueAcquires > 0) {
                redCondition.await();
            }
        } finally {
            lock.unlock();
        }

    }

    public void releaseRed(){
        lock.lock();
        try {
            redAcquires--;
            if (redAcquires == 0) {
                blueCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public void acquireBlue() throws InterruptedException{
        try {
            lock.lock();
            blueAcquires++;
            while (redAcquires > 0) {
                blueCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public void releaseBlue(){
        lock.lock();
        try {
            blueAcquires--;
            if (blueAcquires == 0) {
                redCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
