package home.kalinin.controllers;

import home.kalinin.models.Task;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import home.kalinin.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api", produces="application/json")
@AllArgsConstructor
@Slf4j
public class TaskRestController {
    private final TaskRepository taskRepository;
    @GetMapping()
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Optional<Task> oTask = taskRepository.findById(id);
        if(oTask.isPresent())
            return new ResponseEntity<>(oTask.get(), HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id){
        taskRepository.deleteById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }
    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id, @RequestBody Task task) {
        task.setId(id);
        return taskRepository.save(task);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateSelectiveFieldsTask(
            @PathVariable Long id, @RequestBody Task task) {
        Optional<Task> oTask = taskRepository.findById(id);
        if(oTask.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        Task existTask = oTask.get();
        if(task.getName() != null && task.getName().isBlank())
            existTask.setName(task.getName());
        if(task.getTaskStatus() != null)
            existTask.setTaskStatus(task.getTaskStatus());
        taskRepository.save(existTask);
        return new ResponseEntity<>(existTask, HttpStatus.OK);
    }
}