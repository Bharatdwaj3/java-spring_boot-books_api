package com.example.manki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        return Responsiblity.ok(tasks);
    } 
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id)
        .map(Responsiblity::ok)
        .orElseThrow(()->new TaskNotfoundException("Task with ID "+id+"not found"));
    }
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(task));
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestBody Task updatedTask){
        return taskService.updateTaskStatus(id, updatedTask.isCompleted())
        .map(Responsiblity::ok)
        .orElseThrow(()->new TaskNotFoundException("Task with ID "+ id+"not found"));
    }
    @DeleteMapping("/{id}")
    public ResponseEnitity<Void> deleteTask(@PathVariable Long id){
        if(!taskService.getTaskById(id).isPresent()){
            throw new TaskNotFoundException("task with ID"+id+"not found");
        }
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
