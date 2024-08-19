package com.demo1.demo.controller;

import com.demo1.demo.dto.TaskDto;
import com.demo1.demo.entity.Task;
import com.demo1.demo.service.Impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDto>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskDto> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskDto>> getUserTasks(@PathVariable Long userId) {
        List<TaskDto> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }
}
