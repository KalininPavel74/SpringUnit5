package home.kalinin.repository;

import home.kalinin.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TaskRepository extends JpaRepository<Task, Long> { }
