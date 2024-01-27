package home.kalinin;

import home.kalinin.client.TaskClientRest;
import home.kalinin.models.Task;
import home.kalinin.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner dataLoader(TaskRepository taskRepository, TaskClientRest taskClientRest, RestTemplate rest) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                taskRepository.save(new Task("Изучить Spring",Task.TaskStatus.IN_PROGRESS));
                taskRepository.save(new Task("Сдать ДЗ №2 по Spring",Task.TaskStatus.COMPLETE));
                taskRepository.save(new Task("Сдать ДЗ №3 по Spring",Task.TaskStatus.COMPLETE));
                taskRepository.save(new Task("Сдать ДЗ №4 по Spring",Task.TaskStatus.COMPLETE));
                taskRepository.save(new Task("Сдать ДЗ №5 по Spring",Task.TaskStatus.IN_PROGRESS));
                taskRepository.save(new Task("Сдать ДЗ №6 по Spring",Task.TaskStatus.NOT_STARTED));
                taskRepository.save(new Task("Изучить Spring Security",Task.TaskStatus.COMPLETE));
                taskRepository.save(new Task("Изучить Spring OAuth2",Task.TaskStatus.IN_PROGRESS));


                // Клиент через REST API сделанный в ручнею - работает.
                taskClientRest.setApi_url("http://localhost:8080/api");
                taskClientRest.createTask(new Task("Доп №9",Task.TaskStatus.IN_PROGRESS));
                taskClientRest.createTask(new Task("Доп №10",Task.TaskStatus.IN_PROGRESS));
                taskClientRest.createTask(new Task("Доп №11",Task.TaskStatus.IN_PROGRESS));
                taskClientRest.createTask(new Task("Доп №12",Task.TaskStatus.IN_PROGRESS));
                log.info(""+taskClientRest.getAllTasks());
                log.info(""+taskClientRest.getTaskById(Long.valueOf(9)));
                taskClientRest.deleteTask(taskClientRest.getTaskById(Long.valueOf(9)));
                log.info(""+taskClientRest.getAllTasks());
                Task task10 = taskClientRest.getTaskById(Long.valueOf(10));
                task10.setTaskStatus(Task.TaskStatus.COMPLETE);
                taskClientRest.updateTask(task10);

                // Клиент через REST API DEFAULT не заработал.
/*
                taskClientRest.setApi_url("http://localhost:8080/default-api/tasks");

                taskClientRest.createTask(new Task("Доп №13",Task.TaskStatus.NOT_STARTED));
                taskClientRest.createTask(new Task("Доп №14",Task.TaskStatus.NOT_STARTED));
                log.info(""+taskClientRest.getAllTasks());
                log.info(""+taskClientRest.getTaskById(Long.valueOf(13)));
                taskClientRest.deleteTask(taskClientRest.getTaskById(Long.valueOf(13)));
                log.info(""+taskClientRest.getAllTasks());
                Task task14 = taskClientRest.getTaskById(Long.valueOf(14));
                task14.setTaskStatus(Task.TaskStatus.COMPLETE);
                taskClientRest.updateTask(task14);
*/
            }
        };
    }

}