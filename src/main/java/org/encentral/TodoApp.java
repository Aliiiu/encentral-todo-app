package org.encentral;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encentral.model.Todo;
import org.encentral.model.User;

import java.util.*;

public class TodoApp {
    private static Logger logger = LogManager.getLogger(TodoApp.class);
    private final Map<String, User> users;
    private final MultiValuedMap<String, Todo> todos;

    public TodoApp() {
        users = new LinkedMap<>();
        todos = new ArrayListValuedHashMap<>();
    }

    public void registerUser(String email, String password, String name) {
        if (users.containsKey(email)) {
            logger.warn("User with this email already exists!");
            return;
        }

        User user = new User(email, password, name);
        users.put(email, user);
        logger.info("User registered successfully!");
    }

    public User loginUser(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            logger.info("Login successful");
            return user;
        }
        logger.warn("Invalid details, try again");
        return null;
    }

    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        logger.info("Password updated successfully!");
    }

    public void addTodo(User user, String name, String details) {
        Todo todo = new Todo(name, details);
        todos.put(user.getEmail(), todo);
        logger.info("Todo added successfully!");
    }

    public Collection<Todo> getAllTodos(User user) {
        return todos.get(user.getEmail());
    }

    public Collection<Todo> getActiveTodos(User user) {
        return CollectionUtils.select(todos.get(user.getEmail()), new Predicate<Todo>() {
            @Override
            public boolean evaluate(Todo todo) {
                return !todo.isCompleted();
            }
        });
    }

    public Collection<Todo> getCompletedTodos(User user) {
        return CollectionUtils.select(todos.get(user.getEmail()), new Predicate<Todo>() {
            @Override
            public boolean evaluate(Todo todo) {
                return todo.isCompleted();
            }
        });
    }

    public void deleteTodo(User user, String name) {
        Collection<Todo> userTodos = todos.get(user.getEmail());
        CollectionUtils.filter(userTodos, new Predicate<Todo>() {
            @Override
            public boolean evaluate(Todo todo) {
                return !todo.getTitle().equals(name);
            }
        });
        logger.info("Todo deleted successfully!");
    }

    public void updateTodoStatus(User user, String name, boolean completed) {
        Collection<Todo> userTodos = todos.get(user.getEmail());
        for (Todo todo : userTodos) {
            if (todo.getTitle().equals(name)) {
                todo.setCompleted(completed);
                logger.info("Todo updated successfully!");
                return;
            }
        }
        logger.info("Todo not found!");
    }

    public Collection<Todo> searchTodos(User user, String searchTerm) {
        return CollectionUtils.select(todos.get(user.getEmail()), new Predicate<Todo>() {
            @Override
            public boolean evaluate(Todo todo) {
                return todo.getTitle().contains(searchTerm)
                        || todo.getDetails().contains(searchTerm);
            }
        });
    }
}
