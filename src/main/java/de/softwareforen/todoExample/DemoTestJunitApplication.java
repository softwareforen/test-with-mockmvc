package de.softwareforen.todoExample;

import de.softwareforen.todoExample.todo.TodoEntity;
import de.softwareforen.todoExample.todo.TodoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DemoTestJunitApplication {

    @Bean
    public TodoRepository todoRepository()
    {
        return new TodoRepository() {
            @Override
            public TodoEntity findById(long id) {
                return null;
            }

            @Override
            public List<TodoEntity> findAll() {
                return null;
            }

            @Override
            public TodoEntity save(TodoEntity entity) {
                return null;
            }

            @Override
            public void delete(TodoEntity entity) {

            }

            @Override
            public void deleteAll() {

            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoTestJunitApplication.class, args);
    }
}
