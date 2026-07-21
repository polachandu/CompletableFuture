package org.learning.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Future {
    public static void multithreading() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        java.util.concurrent.Future<List<Integer>> futureObject = service.submit(() -> {
            System.out.println("Thread : "+Thread.currentThread().getName());
            return Arrays.asList(1, 2, 3, 4);
        });
        List<Integer> result = futureObject.get();

        System.out.println(result);

    }
}
