package org.learning.completablefuture;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

import static org.learning.completablefuture.Future.multithreading;


@SpringBootApplication
public class CompletableFutureApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        multithreading();
    }

}
