package de.softwareforen.todoExample.todo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoEntity {
    private long id;
    private String subject;
    private String description;
}
