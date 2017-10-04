package de.softwareforen.todoExample.todo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    TodoEntity findById(long id)
    {
        log.debug("Loading todo item with id[{}]", id);
        return todoRepository.findById(id);
    }

    TodoEntity createTodoEntity(TodoDTO todoDTO)
    {
        TodoEntity entity = createFrom(todoDTO);
        log.debug("Saving todo item {[]}", entity);
        return todoRepository.save(entity);
    }

    private TodoEntity createFrom(@NonNull TodoDTO todoDTO) {
        return TodoEntity.builder().subject(todoDTO.getSubject()).description(todoDTO.getDescription()).build();
    }

    public List<TodoEntity> findAll() {
        return todoRepository.findAll();
    }
}
