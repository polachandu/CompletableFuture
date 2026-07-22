package org.learning.completablefuture.util;

import org.learning.completablefuture.dto.Employee;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.learning.completablefuture.database.EmployeeDatabase.fetchEmployees;

public class Async {
    public static void multithreading() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        java.util.concurrent.Future<List<Integer>> futureObject = service.submit(() -> {
            System.out.println("Thread : " + Thread.currentThread().getName());
            return Arrays.asList(1, 2, 3, 4);
        });
        List<Integer> result = futureObject.get();

        System.out.println(result);

    }

    public static Void saveEmployeesWithPlainRunAsync(File jsonFile) {
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
            });
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println(employees.size());
            System.out.println("Available Processors: " + Runtime.getRuntime().availableProcessors());
        });
        try {
            return runAsync.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Void saveEmployeesWithExecutorRunAsync(File jsonFile) {
        ObjectMapper mapper = new ObjectMapper();
        ExecutorService service = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
            });
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println(employees.size());
            System.out.println("RunAsync Processors: " + Runtime.getRuntime().availableProcessors());
        }, service);
        try {
            return runAsync.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Employee> getEmployeesWithPlainSupplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Employee>> listCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Supply Async Executed by : " + Thread.currentThread().getName());
            return fetchEmployees();
        });
        return listCompletableFuture.get();
    }

    public static List<Employee> getEmployeesWithExecutorSupplyAsync() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletableFuture<List<Employee>> listCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Executor Supply Async Executed by : " + Thread.currentThread().getName());
            return fetchEmployees();
        }, executorService);
        return listCompletableFuture.get();
    }
}
