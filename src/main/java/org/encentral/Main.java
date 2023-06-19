package org.encentral;

import org.encentral.model.Todo;
import org.encentral.model.User;

import java.util.Collection;
import java.util.Scanner;
import org.apache.commons.validator.routines.EmailValidator;

public class Main {
    public static void main(String[] args) {
        EmailValidator validator = EmailValidator.getInstance();
        TodoApp todoApp = new TodoApp();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("------- Welcome to Todo Application --------");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    if(!validator.isValid(email)){
                        System.out.println("Email is invalid");
                        break;
                    }
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    if(password.length() < 5){
                        System.out.println("Password should be greater than 4");
                        break;
                    }
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    todoApp.registerUser(email, password, name);
                    break;
                case 2:
                    System.out.print("Enter email: ");
                    String loginEmail = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    User loggedInUser = todoApp.loginUser(loginEmail, loginPassword);
                    if (loggedInUser != null) {
                        System.out.println("Logged in as " + loggedInUser.getName());
                        while (true) {
                            System.out.println("1. Add Todo");
                            System.out.println("2. Get All Todos");
                            System.out.println("3. Get Active Todos");
                            System.out.println("4. Get Completed Todos");
                            System.out.println("5. Delete Todo");
                            System.out.println("6. Update Todo Status");
                            System.out.println("7. Search Todos");
                            System.out.println("8. Search Todos By Number of days");
                            System.out.println("9. Update Password");
                            System.out.println("0. Logout");
                            System.out.print("Enter your choice: ");
                            int userChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (userChoice) {
                                case 1:
                                    System.out.print("Enter Todo name: ");
                                    String todoName = scanner.nextLine();
                                    System.out.print("Enter Todo details: ");
                                    String todoDetails = scanner.nextLine();
                                    todoApp.addTodo(loggedInUser, todoName, todoDetails);
                                    break;
                                case 2:
                                    Collection<Todo> allTodos = todoApp.getAllTodos(loggedInUser);
                                    System.out.println("All Todos:");
                                    if (allTodos.size() > 0){
                                        for (Todo todo : allTodos) {
                                            System.out.println(todo.getTitle() + " - " + todo.getDetails()
                                                    + " (" + (todo.isCompleted() ? "Completed" : "Active") + ")");
                                        }
                                    } else {
                                        System.out.println("Nothing found\n");
                                    }
                                    break;
                                case 3:
                                    Collection<Todo> activeTodos = todoApp.getActiveTodos(loggedInUser);
                                    System.out.println("Active Todos:");
                                    if (activeTodos.size() > 0){
                                        for (Todo todo : activeTodos) {
                                            System.out.println(todo.getTitle() + " - " + todo.getDetails());
                                        }
                                    } else {
                                        System.out.println("Nothing found\n");
                                    }
                                    break;
                                case 4:
                                    Collection<Todo> completedTodos = todoApp.getCompletedTodos(loggedInUser);
                                    System.out.println("Completed Todos:");
                                    if (completedTodos.size() > 0){
                                        for (Todo todo : completedTodos) {
                                            System.out.println(todo.getTitle() + " - " + todo.getDetails());
                                        }
                                    } else {
                                        System.out.println("Nothing found\n");
                                    }
                                    break;
                                case 5:
                                    System.out.print("Enter Todo name to delete: ");
                                    String todoToDelete = scanner.nextLine();
                                    todoApp.deleteTodo(loggedInUser, todoToDelete);
                                    break;
                                case 6:
                                    System.out.print("Enter Todo name to update status: ");
                                    String todoToUpdate = scanner.nextLine();
                                    System.out.print("Enter status (true for completed, false for active): ");
                                    boolean status = scanner.nextBoolean();
                                    scanner.nextLine();
                                    todoApp.updateTodoStatus(loggedInUser, todoToUpdate, status);
                                    break;
                                case 7:
                                    System.out.print("Enter search term: ");
                                    String searchTerm = scanner.nextLine();
                                    Collection<Todo> searchResults = todoApp.searchTodos(loggedInUser, searchTerm);
                                    System.out.println("Search Results:");
                                    if (searchResults.size() > 0){
                                        for (Todo todo : searchResults) {
                                            System.out.println(todo.getTitle() + " - " + todo.getDetails());
                                        }
                                    } else {
                                        System.out.println("Nothing found\n");
                                    }

                                    break;
                                case 8:
                                    System.out.print("Enter number of days: ");
                                    int numberOfDays = scanner.nextInt();
                                    Collection<Todo> searchResult = todoApp.searchTodosByDays(loggedInUser, numberOfDays);
                                    System.out.println("Search Results:");
                                    if (searchResult.size() > 0){
                                        for (Todo todo : searchResult) {
                                            System.out.println(todo.getTitle() + " - " + todo.getDetails());
                                        }
                                    } else {
                                        System.out.println("Nothing found\n");
                                    }
                                    break;
                                case 9:
                                    System.out.print("Enter new password: ");
                                    String newPassword = scanner.nextLine();
                                    todoApp.updateUserPassword(loggedInUser, newPassword);
                                    break;
                                case 0:
                                    System.out.println("Logged out successfully!");
                                    break;
                                default:
                                    System.out.println("Invalid choice!");
                                    break;
                            }

                            if (userChoice == 0) {
                                break;
                            }
                        }
                    } else {
                        System.out.println("Invalid email or password!");
                    }
                    break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }

    }
}