package de.softwareforen.todoExample.todo;

import lombok.Data;

@Data
public class TodoDTO {
    private final String subject;
    private String description;
}
