package home.kalinin.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import home.kalinin.models.Task;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Data
@Slf4j
@Service
public class TaskClientRest {
    private RestTemplate rest;
    private String api_url = "http://localhost:8080/api";
    //private String api_url = "http://localhost:8080/default-api/tasks";


    public TaskClientRest(RestTemplate restTemplate) {
        this.rest = restTemplate;
    }

    public Task getTaskById(Long id) {
        return rest.getForObject(api_url+"/{id}", Task.class, id);
    }

    public void updateTask(Task task) {
        rest.put(api_url+"/{id}",task, task.getId());
    }

    public Task createTask(Task task) {
        return rest.postForObject(api_url,task, Task.class);
    }

    public void deleteTask(Task task) {
        rest.delete(api_url+"/{id}",task.getId());
    }

    public List<Task> getAllTasks() {
        return rest.exchange(api_url,
                        HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<List<Task>>() {
                        })
                .getBody();
    }

}
