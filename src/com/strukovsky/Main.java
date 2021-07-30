package com.strukovsky;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Main {

    static class Shared
    {
        static int count = 0;
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        new Thread(new IncrementThread(semaphore, "Increment", 5)).start();
        new Thread(new DecrementThread(semaphore, "Decrement", 8)).start();
    }

    static class MathematicalThread
    {
        Semaphore semaphore;
        String name;
        int operationsCount;

        public MathematicalThread(Semaphore sem, String name, int operationsCount)
        {
            semaphore = sem;
            this.name = name;
            this.operationsCount = operationsCount;
        }

        protected void declareStart()
        {
            System.out.println("Start " + name);
        }
    }

    static class IncrementThread extends MathematicalThread implements Runnable
    {

        public IncrementThread(Semaphore sem, String name, int operationsCount)
        {
            super(sem, name, operationsCount);
        }

        @Override
        public void run() {
            try
            {
                declareStart();
                semaphore.acquire();
                for (int i = 0; i < operationsCount; i++) {
                    Shared.count++;
                    System.out.println(Shared.count);
                }
                semaphore.release();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

    static class DecrementThread extends MathematicalThread implements Runnable
    {

        public DecrementThread(Semaphore sem, String name, int operationsCount) {
            super(sem, name, operationsCount);
        }

        @Override
        public void run() {
            declareStart();
            try
            {
                declareStart();
                semaphore.acquire();
                for (int i = 0; i < operationsCount; i++) {
                    Shared.count--;
                    System.out.println(Shared.count);
                }
                semaphore.release();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

}
