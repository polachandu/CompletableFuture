package org.learning.completablefuture;

import org.learning.completablefuture.database.EmployeeDatabase;
import org.learning.completablefuture.dto.Employee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EmployeeReminderService {

    public static CompletableFuture<Void> sendReminder(){

        ExecutorService executors = Executors.newFixedThreadPool(5);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetch Employee Thread Name: " + Thread.currentThread().getName());
            return EmployeeDatabase.fetchEmployees();
        },executors).thenApplyAsync((employees) -> {
            System.out.println("Filter New Joiner Thread Name: " + Thread.currentThread().getName());
            return employees.stream().filter(employee -> "TRUE".equals(employee.getNewJoiner())).collect(Collectors.toList());
        },executors).thenApplyAsync((employees) -> {
            System.out.println("Filter Training Pending Thread Name: " + Thread.currentThread().getName());
            return employees.stream().filter(employee -> "TRUE".equals(employee.getLearningPending())).collect(Collectors.toList());
        },executors).thenApplyAsync((employees -> {
            System.out.println("Get Emails Thread Name: " + Thread.currentThread().getName());
            return employees.stream().map(Employee::getEmail).toList();
        }),executors).thenAcceptAsync(
                (emails) ->{
                        System.out.println("Email Thread Name: "+Thread.currentThread().getName());
                        emails.forEach(email -> sendEmail(email));
                },executors);
        return voidCompletableFuture;
    }

    private static void sendEmail(String email){
        System.out.println("Sending Pending Training email : "+email);
    }
}
