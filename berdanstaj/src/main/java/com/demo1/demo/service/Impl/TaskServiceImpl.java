package com.demo1.demo.service.Impl;

import com.demo1.demo.dto.TaskDto;
import com.demo1.demo.entity.Task;
import com.demo1.demo.entity.User;
import com.demo1.demo.repository.TaskRepository;
import com.demo1.demo.repository.UserRepository;
import com.demo1.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void addTask(Long userId, TaskDto taskDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Task task = modelMapper.map(taskDto, Task.class);
        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    public List<TaskDto> getTasksByUserId(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByUser(Long userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
    }
}
