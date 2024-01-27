package home.kalinin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull( message = "Error. Task name is required.")
    @NotBlank(message = "Error. Task name is required.")
    private String name;
    @Column(nullable = false)
    @NotNull(message = "Error. Task status is required.")
    private TaskStatus taskStatus;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum TaskStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETE
    }

    public Task(String name, TaskStatus taskStatus) {
        this.name = name;
        this.taskStatus = taskStatus;
    }
}
