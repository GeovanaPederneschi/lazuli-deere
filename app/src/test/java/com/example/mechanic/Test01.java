package com.example.mechanic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Test01 {

        private static AtomicBoolean init = new AtomicBoolean();

        public static void init() {
            if (init.compareAndSet(false, true)) {
                System.out.println("initializing");
            }
        }

        public static void main(String[] args) throws InterruptedException {
            int c = 5;
            ExecutorService executorService = Executors.newFixedThreadPool(c);
            for (int i = 0; i < c; i++) {
                executorService.execute(Test01::init);
            }
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        }

}
