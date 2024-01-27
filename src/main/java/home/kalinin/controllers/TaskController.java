package home.kalinin.controllers;

import home.kalinin.models.Task;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import home.kalinin.repository.TaskRepository;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
@Slf4j
public class TaskController {
    private final TaskRepository taskRepository;

    @GetMapping()
    public String getTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        model.addAttribute("task", new Task());
        return "tasks";
    }

    @PostMapping()
    public String addTask(@Valid Task task, Errors errors, Model model) {
        log.info("POST task = " + task.hashCode() + " " + task);

        if (errors.hasErrors()) {
            log.error("errors.hasErrors() " + errors);
            model.addAttribute("db_save_error", errors);
        } else {
            try {
                taskRepository.save(task);
            } catch (DataAccessException ex) {
                log.error("DataAccessException ");
                log.error(ex.getLocalizedMessage());
                model.addAttribute("db_save_error", ex.getMessage());
            }
        }
        model.addAttribute("tasks", taskRepository.findAll());
        model.addAttribute("task", new Task());
        return "tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, Model model) {
        log.info("DELETE task = " + id);
        if (id == null || id < 1) {
            log.error("DELETE task id == null || id < 1 : " + id);
            model.addAttribute("db_save_error", "DELETE task id == null || id < 1 : " + id);
        } else {
            try {
                taskRepository.deleteById(id);
            } catch (DataAccessException ex) {
                log.error("DataAccessException ");
                log.error(ex.getLocalizedMessage());
                model.addAttribute("db_save_error", ex.getMessage());
            }
        }
        model.addAttribute("tasks", taskRepository.findAll());
        model.addAttribute("task", new Task());
        return "redirect:/tasks";
    }

    @GetMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, Model model) {
        log.info("UPDATE task = " + id);

        if (id == null || id < 1) {
            log.error("UPDATE task id == null || id < 1 : " + id);
            model.addAttribute("db_save_error", "UPDATE task id == null || id < 1 : " + id);
            model.addAttribute("tasks", taskRepository.findAll());
            model.addAttribute("task", new Task());
            return "redirect:/tasks";
        } else {
            Task task;
            try {
                task = taskRepository.findById(id).get();
            } catch (Exception ex) {
                log.error(ex.getLocalizedMessage());
                model.addAttribute("db_save_error", ex.getMessage());
                model.addAttribute("tasks", taskRepository.findAll());
                model.addAttribute("task", new Task());
                return "redirect:/tasks";
            }
            model.addAttribute("task", task);
            return "task_update";
        }
    }

    @PostMapping("/task_update")
    public String putTask(@Valid Task task, Errors errors, Model model) {
        log.info("PUT task = " + task.hashCode() + " " + task);

        if (errors.hasErrors()) {
            log.error("errors.hasErrors() " + errors);
            model.addAttribute("db_save_error", errors);
            model.addAttribute("task", task);
            return "task_update";
        } else {
            try {
                taskRepository.save(task);
            } catch (DataAccessException ex) {
                log.error("DataAccessException ");
                log.error(ex.getLocalizedMessage());
                model.addAttribute("db_save_error", ex.getMessage());
            }
        }
        return "redirect:/tasks";
    }
}