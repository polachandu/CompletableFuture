package org.learning.completablefuture.database;

import org.learning.completablefuture.dto.Employee;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class EmployeeDatabase {

    public static List<Employee> fetchEmployees() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("employees.json"), new TypeReference<List<Employee>>() {
        });
    }
}
