package org.encentral;

import org.encentral.model.Todo;
import org.encentral.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TodoAppTest {
    TodoApp todoApp;
    @BeforeEach
    void setUp() {
        todoApp = new TodoApp();
        todoApp.registerUser("ar@gmail.com", "12345", "aliu");
    }

    @Test
    void registerUser() {
        User user = todoApp.loginUser("ar@gmail.com", "12345");
        assertNotNull(user);
    }

    @Test
    void updateUserPassword() {
        User user = todoApp.loginUser("ar@gmail.com", "12345");
        todoApp.updateUserPassword(user, "123456");
        assertEquals(user.getPassword(), "123456");
    }

    @Test
    void addTodo() {
        User user = todoApp.loginUser("ar@gmail.com", "12345");
        todoApp.addTodo(user, "breakfast", "eat rice");
        todoApp.addTodo(user, "rea", "study java");
    }

    @Test
    void getAllTodos() {
        User user = todoApp.loginUser("ar@gmail.com", "12345");
        todoApp.addTodo(user, "breakfast", "eat rice");
        todoApp.addTodo(user, "rea", "study java");
        Collection<Todo> todos = todoApp.getAllTodos(user);
        assertEquals(2, todos.size());
    }

    @Test
    void getActiveTodos() {
        User user = todoApp.loginUser("ar@gmail.com", "12345");
        todoApp.addTodo(user, "breakfast", "eat rice");
        todoApp.addTodo(user, "rea", "study java");
        todoApp.updateTodoStatus(user, "breakfast", true);
        Collection<Todo> todos = todoApp.getActiveTodos(user);
        assertEquals(1, todos.size());
    }

    @Test
    void getCompletedTodos() {
        User user = todoApp.loginUser("ar@gmail.com", "12345");
        todoApp.addTodo(user, "breakfast", "eat rice");
        todoApp.addTodo(user, "rea", "study java");
        todoApp.updateTodoStatus(user, "breakfast", true);
        Collection<Todo> todos = todoApp.getCompletedTodos(user);
        assertEquals(1, todos.size());
    }

    @Test
    void deleteTodo() {
        User user = todoApp.loginUser("ar@gmail.com", "12345");
        todoApp.addTodo(user, "breakfast", "eat rice");
        todoApp.addTodo(user, "read", "study java");
        todoApp.deleteTodo(user, "read");
        Collection<Todo> todos = todoApp.getAllTodos(user);
        assertEquals(1, todos.size());
    }

    @Test
    void searchTodos() {
        User user = todoApp.loginUser("ar@gmail.com", "12345");
        todoApp.addTodo(user, "breakfast", "eat rice");
        todoApp.addTodo(user, "read", "study java");
        Collection<Todo> todos = todoApp.searchTodos(user, "breakfast");
        assertEquals(1, todos.size());
    }
}