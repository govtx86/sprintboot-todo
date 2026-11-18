package com.mygroup.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mygroup.todo.model.Todo;
import com.mygroup.todo.repository.TodoRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getTodos() {
        try {
            List<Todo> todos = todoRepository.findAll();
            if (todos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(todos, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable("id") long id) {
        try {
            Optional<Todo> todo = todoRepository.findById(id);
            if (!todo.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(todo.get(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<Long> createTodo(@RequestBody Todo todo) {
        try {
            if (todo.getTodo() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Todo _todo = todoRepository.save(new Todo(todo.getTodo(), todo.getDone()));
            return new ResponseEntity<>(_todo.getId(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<HttpStatus> updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
        try {
            Optional<Todo> todoData = todoRepository.findById(id);
            if (!todoData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Todo _todo =  todoData.get();
            if (todo.getTodo() != null) {
                _todo.setTodo(todo.getTodo());
            }
            if (todo.getDone() != null) {
                _todo.setDone(todo.getDone());
            }
            todoRepository.save(_todo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable("id") long id) {
        try {
            if (!todoRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            todoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
