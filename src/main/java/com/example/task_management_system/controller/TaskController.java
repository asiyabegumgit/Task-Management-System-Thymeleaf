package com.example.task_management_system.controller;

import com.example.task_management_system.model.Task;
import com.example.task_management_system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  private TaskService taskService;
  @GetMapping
    public String getAllTask(Model model) {
    List<Task> tasks=taskService.getAllTasks();
    model.addAttribute("tasks", tasks);
    return "task-list";
  }

  @GetMapping("/new")
  public String showCreateTaskForm(Model model) {
    Task task = new Task();
    model.addAttribute("task", task);
    return "task-form";
  }


  @GetMapping("/edit/{id}")
  public String showEditTaskForm(@PathVariable Long id, Model model) {
    Optional<Task> optionalTask = taskService.getTaskById(id);
    if (optionalTask.isPresent()) {
      model.addAttribute("task", optionalTask.get());
    } else {
      return "redirect:/tasks";
    }
    return "task-form";
  }

  @PostMapping("/save")
  public String saveTask(@ModelAttribute("task") Task task) {
    if (task.getId() != null) {
      taskService.updateTask(task.getId(), task);
    } else {
      taskService.createTask(task);
    }
    return "redirect:/tasks";
  }

  @GetMapping("/delete/{id}")
  public String deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return "redirect:/tasks";
  }

}

