package com.example.manki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import com.example.manki.model.Task;
import com.example.manki.service.TaskService;
import com.example.manki.exception.TaskNotFoundException;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks= taskService.getAllTasks();
        if(tasks.isEmpty()){
            throw new TaskNotFoundException("No tasks found");
        }
        return ResponseEntity.ok(tasks);
    } 
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(()->new TaskNotFoundException("Task with ID "+id+"not found"));
    }
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(task));
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestBody Task updatedTask){
        return taskService.updateTaskStatus(id, updatedTask.isCompleted())
        .map(ResponseEntity::ok)
        .orElseThrow(()->new TaskNotFoundException("Task with ID "+ id+"not found"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        if(!taskService.getTaskById(id).isPresent()){
            throw new TaskNotFoundException("task with ID"+id+"not found");
        }
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
