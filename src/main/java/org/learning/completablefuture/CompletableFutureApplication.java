package org.learning.completablefuture;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static org.learning.completablefuture.EmployeeReminderService.sendReminder;
import static org.learning.completablefuture.util.Async.*;

@SpringBootApplication
public class CompletableFutureApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        multithreading();
//        saveEmployeesWithPlainRunAsync(new File("employees.json"));
//        saveEmployeesWithExecutorRunAsync(new File("employees.json"));
//        getEmployeesWithPlainSupplyAsync();
//        getEmployeesWithExecutorSupplyAsync();
        sendReminder().get();
    }

}
