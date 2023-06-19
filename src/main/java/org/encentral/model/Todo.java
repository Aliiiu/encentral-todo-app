package org.encentral.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Todo {
    private String title;
    private String details;
    private boolean completed;

    private LocalDateTime dateCreated;

    public Todo(String title, String details) {
        this.title = title;
        this.details = details;
        this.completed = false;
        this.dateCreated = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo)) return false;
        Todo todo = (Todo) o;
        return completed == todo.completed && Objects.equals(title, todo.title) && Objects.equals(details, todo.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, details, completed);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", completed=" + completed +
                '}';
    }
}
