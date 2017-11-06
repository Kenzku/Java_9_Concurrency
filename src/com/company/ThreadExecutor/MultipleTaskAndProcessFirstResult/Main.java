package com.company.ThreadExecutor.MultipleTaskAndProcessFirstResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {
    String username = "test user";
    String password = "test password";

    // Create two user validator objects

    UserValidator ldapValidator = new UserValidator("LDAP");
    UserValidator dbValidator = new UserValidator("Database");

    // Create two tasks for the user validation objects
    ValidatorTask ldapTask = new ValidatorTask(ldapValidator, username, password);
    ValidatorTask dbTask = new ValidatorTask(dbValidator, username, password);

    // Add the two tasks to a list of tasks
    List<ValidatorTask> tasks = new ArrayList<>();
    tasks.add(ldapTask);
    tasks.add(dbTask);

    // Create a new Executor
    ExecutorService executor = (ExecutorService) Executors.newCachedThreadPool();
    String result;

    try {
      // send the list of tasks to the executor and waits for the results of the first task
      // that finish without throw and Exception. If all tasks throw exception, the method
      // throws an ExecutionException
      result = executor.invokeAny(tasks);
      System.out.printf("Main: Result: %s\n", result);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    // terminate the Executor
    executor.shutdown();
    System.out.printf("Main: End of the execution\n");
  }
}
