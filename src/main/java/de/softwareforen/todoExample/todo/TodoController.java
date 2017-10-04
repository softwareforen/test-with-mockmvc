package de.softwareforen.todoExample.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoEntity> createTodoItem(@RequestBody TodoDTO todoDTO, HttpServletRequest request)
    {
        TodoEntity result = todoService.createTodoEntity(todoDTO);
        UriComponents uri = ServletUriComponentsBuilder.fromRequest(request).pathSegment(String.valueOf(result.getId())).build();
        return ResponseEntity.created(uri.toUri()).build();
    }

    @GetMapping
    public ResponseEntity<List<TodoEntity>> findToDoEntities()
    {
        List<TodoEntity> result = todoService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoEntity> findToDoEntities(@PathVariable long id)
    {
        TodoEntity result = todoService.findById(id);
        return ResponseEntity.ok(result);
    }
}
