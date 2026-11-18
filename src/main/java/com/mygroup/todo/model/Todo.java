package com.mygroup.todo.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@EntityListeners(AuditingEntityListener.class)
@Table(name = "todo")
public class Todo {
    @Id
    @GeneratedValue 
    private long id;

    @Column(name = "todo", nullable = false)
    private String todo;

    @Column(name = "done", nullable = false)
    private Boolean done = false;

    @CreatedDate 
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public Todo() {
    }

    public Todo(String todo, Boolean done) {
        this.todo = todo;
        if (done != null) {
            this.done = done;
        }
    }

    public long getId() {
        return this.id;
    }

    public String getTodo() {
        return this.todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Boolean getDone() {
        return this.done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return this.id + " : " + this.done + " : " + this.createdAt + " : " + this.todo;
    }
}
