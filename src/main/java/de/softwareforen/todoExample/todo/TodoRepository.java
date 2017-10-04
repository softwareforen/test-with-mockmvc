package de.softwareforen.todoExample.todo;

import java.util.List;

public interface TodoRepository {

    TodoEntity findById(long id);

    List<TodoEntity> findAll();

    TodoEntity save(TodoEntity entity);

    void delete(TodoEntity entity);

    void deleteAll();
}
