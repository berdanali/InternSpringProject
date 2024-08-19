package com.demo1.demo.service;

import com.demo1.demo.dto.TaskDto;
import com.demo1.demo.entity.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);

    void addTask(Long userId, TaskDto taskDto);
    List<TaskDto> getTasksByUserId(Long userId);
    List<TaskDto> getTasksByUser(Long userId);
}
